package com.khjxiaogu.scriptengine.core.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

/**
 * ScriptClosure for inheritance and Script Object
 * backed by map,Closure is the 'this' environment.
 * sbclasses is the super environment/
 *
 * @author khjxiaogu
 * @time 2020年3月6日
 *       project:khjScriptEngine
 */
public class ExtendableClosure extends Closure {

	protected String clsname;
	private KObject SuperClass = null;
	Map<Class<?>,Object> natives= new ConcurrentHashMap<>();
	public ExtendableClosure(String name, KObject sbclass) {
		super(new MapEnvironment());
		clsname = name;
		SuperClass = sbclass;
	}

	public ExtendableClosure(String name) {
		super(new MapEnvironment());
		clsname = name;
		SuperClass = null;
	}

	@Override
	public KVariant getMemberByName(String name, int flag) throws KSException {
		if (!closure.hasMemberByName(name, KEnvironment.THISONLY))
			if (SuperClass != null) {
				if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
					return SuperClass.getMemberByName(name, flag);
			} else if ((flag & KEnvironment.THISONLY) ==0)
				return GlobalEnvironment.getGlobal().getMemberByName(name, flag);
		return closure.getMemberByName(name, flag);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag) throws KSException {
		if (SuperClass != null && !closure.hasMemberByVariant(var) && SuperClass.hasMemberByVariant(var))
			return SuperClass.getMemberByVariant(var, flag);

		return closure.getMemberByVariant(var, flag);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		if (!closure.hasMemberByName(name, KEnvironment.THISONLY))
			if (SuperClass != null) {
				if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
					return SuperClass.setMemberByName(name, val, flag);
			} else if ((flag & KEnvironment.THISONLY) == 0)
				return GlobalEnvironment.getGlobal().setMemberByName(name, val, flag);
		return closure.setMemberByName(name, val, flag);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		if (SuperClass != null && !closure.hasMemberByVariant(var) && SuperClass.hasMemberByVariant(var))
			return SuperClass.setMemberByVariant(var, val, flag);
		return closure.setMemberByVariant(var, val, flag);
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KEnvironment objthis, int flag) throws KSException {
		if (name == null)
			return ((CallableFunction) this).FuncCall(args, objthis);
		if (!closure.hasMemberByName(name, KEnvironment.THISONLY))
			if (SuperClass != null) {
				if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
					return SuperClass.funcCallByName(name, args, objthis, flag);
			} else if ((flag & KEnvironment.THISONLY) == 0)
				return GlobalEnvironment.getGlobal().funcCallByName(name, args, objthis, flag);
		return closure.funcCallByName(name, args, objthis, flag);
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		if (name == null)
			return true;
		if (closure.hasMemberByName(name, KEnvironment.THISONLY)
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
		if (closure.hasMemberByVariant(var) || SuperClass == null && SuperClass.hasMemberByVariant(var))
			return true;
		return false;
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		if (!closure.deleteMemberByName(name))
			if (SuperClass != null)
				return SuperClass.deleteMemberByName(name);
			else
				return false;
		else
			return true;
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		if (!closure.deleteMemberByVariant(var))
			if (SuperClass != null)
				return SuperClass.deleteMemberByVariant(var);
			else
				return false;
		else
			return true;
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		if (SuperClass != null && !closure.hasMemberByName(name, KEnvironment.DEFAULT))
			if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
				return SuperClass.doOperationByName(op, name, opr);
			else
				return GlobalEnvironment.getGlobal().doOperationByName(op, name, opr);
		return closure.doOperationByName(op, name, opr);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		if (SuperClass != null && !closure.hasMemberByVariant(var) && SuperClass.hasMemberByVariant(var))
			return SuperClass.doOperationByVariant(op, var, opr);
		return closure.doOperationByVariant(op, var, opr);
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		if (str.equals(clsname))
			return true;
		return SuperClass.isInstanceOf(str);
	}

	@Override
	public boolean isValid() throws KSException {
		return closure == null;
	}

	@Override
	public boolean invalidate() throws KSException {
		if (closure != null) {
			closure = null;
			return true;
		}
		return false;
	}
	protected ExtendableClosure getNewInstance() throws KSException {
		KObject Super=null;
		if(SuperClass!=null)
			Super = SuperClass.newInstance();
		return new ExtendableClosure(clsname, Super);
	}
	@Override
	public KObject newInstance() throws KSException {
		ExtendableClosure newInst = getNewInstance();
		closure.EnumMembers((k, v) -> {
			try {
				if (v.getType().getType() == KObject.class) {
					KObject obj = v.toType(KObject.class);
					if (obj instanceof KProperty) {
						v = new KVariant(new PropertyClosure((KProperty) obj, newInst));
					} else if (obj instanceof CallableFunction) {
						v = new KVariant(new FunctionClosure(obj, newInst));
					} else
						return true;
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
		closure.EnumMembers(cosumer, flag);
	}

	@Override
	public void putNativeInstance(Object nis) throws KSException {
		natives.put(nis.getClass(),nis);
	}
	public <T> void putNativeInstance(Class<T> cls,T nis) throws KSException {
		natives.put(cls,nis);
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getNativeInstance(Class<T> cls) throws KSException {
		return (T)natives.get(cls);
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
	public void callConstructor(KVariant[] args, KEnvironment env) throws KSException {
		KVariant ctor=this.getMemberByName(this.clsname,KEnvironment.DEFAULT);
		if(KObject.class.isAssignableFrom(ctor.getType().getType())) {
			KObject ctorfunc=ctor.asType(KObject.class);
			if(ctorfunc instanceof CallableFunction)
				((CallableFunction) ctorfunc).FuncCall(args, env);
		}
	}

	@Override
	public String toString() {
		return "(Object)("+this.getInstanceName()+")(0x"+Integer.toHexString(super.hashCode())+")@(0x"+Integer.toHexString(closure.hashCode())+")";
	}

	@Override
	public String getInstanceName() {
		return clsname;
	}
}
