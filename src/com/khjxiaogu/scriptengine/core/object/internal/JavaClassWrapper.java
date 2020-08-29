package com.khjxiaogu.scriptengine.core.object.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.JVMError;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.KOctet;
import com.khjxiaogu.scriptengine.core.object.NativeClassClosure;
import com.khjxiaogu.scriptengine.core.object.NativeProperty;
import com.khjxiaogu.scriptengine.core.object.Closure;
import com.khjxiaogu.scriptengine.core.object.ExtendableClosure;
import com.khjxiaogu.scriptengine.core.typeconvert.ConversionManager;
import com.khjxiaogu.scriptengine.core.typeconvert.TypeInfo;

public class JavaClassWrapper<T> extends NativeClassClosure<T> {
	private final static HashMap<Class<?>,Class<?>> typemap=new HashMap<Class<?>,Class<?>>();
	private final static HashMap<Class<?>,Class<?>> resultmap=new HashMap<Class<?>,Class<?>>();
	private final static int HIERARCHY_MAX=10;
	static {
		typemap.put(char.class,Character.class);
		typemap.put(byte.class,Byte.class);
		typemap.put(short.class,Short.class);
		typemap.put(int.class,Integer.class);
		typemap.put(long.class,Long.class);
		typemap.put(float.class,Float.class);
		typemap.put(double.class,Double.class);
		typemap.put(boolean.class,Boolean.class);
	}
	static {
		resultmap.put(char.class,String.class);
		resultmap.put(byte[].class, KOctet.class);
		resultmap.put(byte.class, Long.class);
		resultmap.put(short.class,Long.class);
		resultmap.put(Integer.class,Long.class);
		resultmap.put(Long.class,Long.class);
		resultmap.put(Float.class,Double.class);
		resultmap.put(Double.class,Double.class);
		resultmap.put(Boolean.class,Long.class);
		resultmap.put(Void.class,Void.class);
	}
	private static boolean isAssignable(Class<?> jvmType,KVariant var) throws KSException {
		Class<?> varType=var.getType().getType();
		jvmType=typemap.getOrDefault(jvmType, jvmType);
		
		jvmType=resultmap.getOrDefault(jvmType,jvmType);
		if(jvmType.equals(varType))
			return true;
		KObject ko;
		if(varType==KObject.class&&(ko=var.toType(KObject.class)) instanceof Closure&&ko.getNativeInstance(jvmType)!=null)
			return true;
		return false;
	}
	private static Object convert(Class<?> jvmType,KVariant var) throws KSException {
		jvmType=typemap.getOrDefault(jvmType,jvmType);
		Class<?> varType=var.getType().getType();
		if(ConversionManager.canConvert(jvmType,varType))
			return var.toType(jvmType);
		return ((Closure)var.toType(KObject.class)).getNativeInstance(jvmType);
	}
	private static KVariant revert(Object ret) throws KSException {
		Class<?> jvmType=ret.getClass();
		jvmType=typemap.getOrDefault(jvmType,jvmType);
		Class<?> varType=resultmap.getOrDefault(jvmType,jvmType);
		TypeInfo ti=TypeInfo.forTypeConstant(varType);
		if(ti==null) {
			KObject ko=getWrapper(ret.getClass()).newInstance();
			ko.putNativeInstance(ret);
			return new KVariant(ko);
		}
		return new KVariant(ConversionManager.getConversion(jvmType,varType).from(ret),ti);
	}
	private static final Map<Class<?>,JavaClassWrapper<?>> cache=new HashMap<>();
	public static KVariant wrapObject(Object ret) throws KSException {
		KObject ko=getWrapper(ret.getClass()).newInstance();
		ko.putNativeInstance(ret);
		return new KVariant(ko);
	}
	public static <T> KVariant wrapObject(Class<T> cls,T ret) throws KSException {
		ExtendableClosure ko=(ExtendableClosure) getWrapper(cls).newInstance();
		ko.putNativeInstance(cls,ret);
		return new KVariant(ko);
	}
	public synchronized static JavaClassWrapper<?> getWrapper(Class<?> cls) {
		JavaClassWrapper<?> jcw=cache.get(cls);
		if(jcw==null) {
			jcw=new JavaClassWrapper<>(cls,false);
		}
		return jcw;
	}
	public synchronized static JavaClassWrapper<?> getUnsafeWrapper(Class<?> cls) {
		JavaClassWrapper<?> jcw=cache.get(cls);
		if(jcw==null) {
			jcw=new JavaClassWrapper<>(cls,true);
			
		}
		return jcw;
	}
	@SuppressWarnings("unchecked")
	protected JavaClassWrapper(Class<T> cls,boolean isSecure) {
		super(cls,cls.getSimpleName());
		synchronized(cache) {
			cache.put(cls,this);
		}
		Set<Constructor<?>> ctors=new HashSet<>();
		ctors.addAll(Arrays.asList(cls.getDeclaredConstructors()));
		ctors.removeIf(ct->!Modifier.isPublic(ct.getModifiers()));
		if(ctors.size()==0) {
			super.registerConstructor((a,b)->{throw new ContextException();});
		}else {
			super.registerConstructor((objthis,args)->{
				Object[] arg=new Object[args.length];
				outer:
				for(Constructor<?> ctor:ctors) {
					if(ctor.getParameterCount()==args.length) {
						Class<?>[] param=ctor.getParameterTypes();
						int i=0;
						for(Class<?> par:param) {
							if(!isAssignable(par,args[i++]))
								continue outer;
						}
						i=0;
						for(Class<?> par:param) {
							arg[i]=convert(par,args[i++]);
						}
						try {
							return (T)ctor.newInstance(arg);
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
							// TODO Auto-generated catch block
							throw new JVMError(e);
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							throw new JVMError(e.getCause());
						}
					}
				}
				throw new ContextException();
			});
		}
		HashSet<Method> mets=new HashSet<>();
		mets.addAll(Arrays.asList(cls.getDeclaredMethods()));
		mets.addAll(Arrays.asList(cls.getMethods()));
		Class<?> spcls;
		HashSet<Field> fs=new HashSet<>();
		fs.addAll(Arrays.asList(cls.getDeclaredFields()));
		fs.addAll(Arrays.asList(cls.getFields()));
		/*int ch=0;
		while((spcls=cls.getSuperclass())!=null) {
			mets.addAll(Arrays.asList(spcls.getDeclaredMethods()));
			fs.addAll(Arrays.asList(spcls.getDeclaredFields()));
			if(ch++>=HIERARCHY_MAX)
				break;
		}*/
		if(isSecure)
			mets.removeIf(m->Object.class.isAssignableFrom(m.getReturnType()));
		Map<String,ArrayList<Method>> mms=new HashMap<>();
		for(Method m:mets) {
			m.setAccessible(true);
			ArrayList<Method> am=mms.get(m.getName());
			if(am==null) {
				am=new ArrayList<>();
				mms.put(m.getName(),am);
			}
			am.add(m);
		}
		
		for(Map.Entry<String,ArrayList<Method>> m:mms.entrySet()) {
			super.registerFunction(m.getKey(),(obj,args)->{
					Object[] arg=new Object[args.length];
					outer:
					for(Method mt:m.getValue()) {
						if(mt.getParameterCount()==args.length) {
							Class<?>[] param=mt.getParameterTypes();
							int i=0;
							for(Class<?> par:param) {
								if(!isAssignable(par,args[i++]))
									continue outer;
							}
							i=0;
							for(Class<?> par:param) {
								arg[i]=convert(par,args[i++]);
							}
							try {
								return revert(mt.invoke(obj, arg));
							} catch (IllegalAccessException | IllegalArgumentException e) {
								// TODO Auto-generated catch block
								throw new JVMError(e);
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								throw new JVMError(e.getCause());
							}
						}
					}
					
					throw new ContextException();
				});
		}

		for(Field f:fs) {
			if(Modifier.isPublic(f.getModifiers())) {
				if(Modifier.isFinal(f.getModifiers())) {
					super.registerProperty(f.getName(),new NativeProperty<T>(
							cls,
							(e)->{
								try {
									return revert(f.get(e));
								} catch (IllegalArgumentException | IllegalAccessException e1) {
									// TODO Auto-generated catch block
									throw new JVMError(e1);
								}
							},null
						));
				}else
					super.registerProperty(f.getName(),new NativeProperty<T>(
							cls,
							(e)->{
								try {
									return revert(f.get(e));
								} catch (IllegalArgumentException | IllegalAccessException e1) {
									// TODO Auto-generated catch block
									throw new JVMError(e1);
								}
							},(e,x)->{
								try {
									f.set(e,convert(f.getType(),x));
								} catch (IllegalArgumentException | IllegalAccessException e1) {
									// TODO Auto-generated catch block
									throw new JVMError(e1);
								}
							}
						));
			}
		}
	}
}
