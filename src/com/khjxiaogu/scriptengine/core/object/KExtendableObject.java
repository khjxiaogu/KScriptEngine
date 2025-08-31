package com.khjxiaogu.scriptengine.core.object;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

/**
 * ScriptClosure for inheritance and Script Object
 * backed by map,Closure is the 'this' environment.
 * SuperClass is the super environment/
 *
 * @author khjxiaogu
 * @time 2020年3月6日
 *       project:khjScriptEngine
 */
public class KExtendableObject extends KAbstractObject {

	protected String clsname;
	private KObject SuperClass = null;
	Map<Class<?>,Object> natives= new ConcurrentHashMap<>();
	private MapEnvironment map=new MapEnvironment();
	public KExtendableObject(String name, KObject sbclass) {
		super();
		clsname = name;
		SuperClass = sbclass;
	}

	public KExtendableObject(String name) {
		super();
		clsname = name;
		SuperClass = null;
	}

	@Override
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException {
		if (!map.hasMemberByName(name, KEnvironment.THISONLY))
			if (SuperClass != null) {
				if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
					return SuperClass.getMemberByName(name, flag, objthis).withStance(this);
			} else if ((flag & KEnvironment.THISONLY) ==0)
				return GlobalEnvironment.getGlobal().getMemberByName(name, flag, objthis);
		return map.getMemberByName(name, flag, objthis);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag, KObject objthis) throws KSException {
		if (SuperClass != null && SuperClass.hasMemberByVariant(var)&&!map.hasMemberByVariant(var))
			return SuperClass.getMemberByVariant(var, flag, objthis).withStance(this);
		return map.getMemberByVariant(var, flag, objthis);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		//if (!closure.hasMemberByName(name, KEnvironment.THISONLY))
		if (SuperClass != null) {
			return SuperClass.setMemberByName(name, val, flag);
		}
		return map.setMemberByName(name, val, flag);
			
		//return closure.setMemberByName(name, val, flag);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		if (SuperClass != null)
			return SuperClass.setMemberByVariant(var, val, flag);
		return map.setMemberByVariant(var, val, flag);
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KObject objthis, int flag) throws KSException {
		if (name == null) {
			if(this instanceof CallableFunction) {
				return ((CallableFunction) this).FuncCall(args, objthis);
			}else throw new MemberNotFoundException(name);
		}
		
		if (map.hasMemberByName(name, KEnvironment.THISONLY)) {
			return map.funcCallByName(name, args, objthis, flag);
		}
		if(SuperClass != null&&SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
			return SuperClass.funcCallByName(name, args, objthis, flag);
		if(Objects.equals(name, clsname))
			return KVariant.valueOf();
		if ((flag & KEnvironment.THISONLY) == 0)
			return GlobalEnvironment.getGlobal().funcCallByName(name, args, objthis, flag);
		throw new MemberNotFoundException(name);
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		if (name == null)
			return true;
		if (map.hasMemberByName(name, KEnvironment.THISONLY)
				|| SuperClass == null && SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
			return true;
		if ((flag & KEnvironment.THISONLY) ==0)
			return false;
		else
			return GlobalEnvironment.getGlobal().hasMemberByName(name, flag);
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		if (var.isNull())
			return true;
		if (map.hasMemberByVariant(var) || SuperClass == null && SuperClass.hasMemberByVariant(var))
			return true;
		return false;
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		if (!map.deleteMemberByName(name))
			if (SuperClass != null)
				return SuperClass.deleteMemberByName(name);
			else
				return false;
		else
			return true;
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		if (!map.deleteMemberByVariant(var))
			if (SuperClass != null)
				return SuperClass.deleteMemberByVariant(var);
			else
				return false;
		else
			return true;
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		if (SuperClass != null && !map.hasMemberByName(name, KEnvironment.DEFAULT))
			if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
				return SuperClass.doOperationByName(op, name, opr);
			else
				return GlobalEnvironment.getGlobal().doOperationByName(op, name, opr);
		return map.doOperationByName(op, name, opr);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		if (SuperClass != null && !map.hasMemberByVariant(var) && SuperClass.hasMemberByVariant(var))
			return SuperClass.doOperationByVariant(op, var, opr);
		return map.doOperationByVariant(op, var, opr);
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		if (str.equals(clsname))
			return true;
		return SuperClass.isInstanceOf(str);
	}

	@Override
	public boolean isValid() throws KSException {
		return map == null;
	}

	@Override
	public boolean invalidate() throws KSException {
		if (map != null) {
			map = null;
			return true;
		}
		return false;
	}
	protected KExtendableObject getNewInstance() throws KSException {
		KObject Super=null;
		if(SuperClass!=null)
			Super = SuperClass.newInstance();
		return new KExtendableObject(clsname, Super);
	}
	@Override
	public KObject newInstance() throws KSException {
		KExtendableObject newInst = getNewInstance();
		newInst.SuperClass=this;
		map.EnumMembers((k,f, v) -> {
			if((f&KEnvironment.STATIC_MEMBER)!=0)return true;
			
			
			try {
				if (v.isObject()) {
					KObject obj = v.asObject();
					v = KVariant.valueOf(new KObjectClosure(obj, newInst));
					newInst.setMemberByVariant(k, v, KEnvironment.THISONLY|KEnvironment.IGNOREPROP);
				}
			} catch (KSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
			return true;
		}, KEnvironment.IGNOREPROP);
		return newInst;
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		map.EnumMembers(cosumer, flag);
	}

	@Override
	public void putNativeInstance(Object nis) throws KSException {
		natives.put(nis.getClass(),nis);
	}
	public <T> void putNativeInstance(Class<T> cls,T nis) throws KSException {
		natives.put(cls,nis);
	}
	@Override
	public boolean hasNativeInstance(Class<?> cls) {
		for(Object obj:natives.values())
			if(cls.isInstance(obj))
				return true;
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getNativeInstance(Class<T> cls) throws KSException {
		Object o=natives.get(cls);
		if(o==null) {
			for(Object obj:natives.values())
				if(cls.isInstance(obj))
					return (T)obj;
		}
		return (T)o;
	}

	@Override
	public KEnvironment getThis() throws ScriptException {
		return this;
	}

	@Override
	public KEnvironment getSuper() throws InvalidSuperClassException {
		return SuperClass;
	}

	@Override
	public void callConstructor(KVariant[] args, KObject objthis) throws KSException {
		//this.funcCallByName(this.clsname, args, env, DEFAULT);
		KVariant ctor=this.getMemberByName(this.clsname,KEnvironment.DEFAULT, objthis);
		if(KObject.class.isAssignableFrom(ctor.getType().getType())) {
			KObject ctorfunc=ctor.asObject();
			if(ctorfunc instanceof CallableFunction) {
				((CallableFunction) ctorfunc).FuncCall(args, objthis);
			}
		}
	}

	@Override
	public String toString() {
		return "(Object)("+this.getInstanceName()+")(0x"+Integer.toHexString(SuperClass==null?map.hashCode():SuperClass.hashCode())+")@(0x"+Integer.toHexString(map.hashCode())+")";
	}

	@Override
	public String getInstanceName() {
		return clsname;
	}
}
