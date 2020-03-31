package com.khjxiaogu.scriptengine.core.object;

import java.util.ArrayList;
import java.util.List;

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

	private String clsname;
	private KObject SuperClass = null;
	private List<Object> natives = new ArrayList<>();

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
		if (!Closure.hasMemberByName(name, KEnvironment.THISONLY))
			if (SuperClass != null) {
				if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
					return SuperClass.getMemberByName(name, flag);
			} else if ((flag & KEnvironment.THISONLY) != 1)
				return GlobalEnvironment.getGlobal().getMemberByName(name, flag);
		return Closure.getMemberByName(name, flag);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag) throws KSException {
		if (SuperClass != null && !Closure.hasMemberByVariant(var) && SuperClass.hasMemberByVariant(var))
			return SuperClass.getMemberByVariant(var, flag);

		return Closure.getMemberByVariant(var, flag);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		if (!Closure.hasMemberByName(name, KEnvironment.THISONLY))
			if (SuperClass != null) {
				if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
					return SuperClass.setMemberByName(name, val, flag);
			} else if ((flag & KEnvironment.THISONLY) != 1)
				return GlobalEnvironment.getGlobal().setMemberByName(name, val, flag);
		return Closure.setMemberByName(name, val, flag);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		if (SuperClass != null && !Closure.hasMemberByVariant(var) && SuperClass.hasMemberByVariant(var))
			return SuperClass.setMemberByVariant(var, val, flag);
		return Closure.setMemberByVariant(var, val, flag);
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KEnvironment objthis, int flag) throws KSException {
		if (name == null)
			return ((CallableFunction) this).FuncCall(args, objthis);
		if (!Closure.hasMemberByName(name, KEnvironment.THISONLY))
			if (SuperClass != null) {
				if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
					return SuperClass.funcCallByName(name, args, objthis, flag);
			} else if ((flag & KEnvironment.THISONLY) != 1)
				return GlobalEnvironment.getGlobal().funcCallByName(name, args, objthis, flag);
		return Closure.funcCallByName(name, args, objthis, flag);
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		if (name == null)
			return true;
		if (Closure.hasMemberByName(name, KEnvironment.THISONLY)
				|| SuperClass == null && SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
			return true;
		if ((flag & KEnvironment.THISONLY) != 1)
			return false;
		else
			return GlobalEnvironment.getGlobal().hasMemberByName(name, flag);
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		if (var.isNull())
			return true;
		if (Closure.hasMemberByVariant(var) || SuperClass == null && SuperClass.hasMemberByVariant(var))
			return true;
		return false;
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		if (!Closure.deleteMemberByName(name))
			if (SuperClass != null)
				return SuperClass.deleteMemberByName(name);
			else
				return false;
		else
			return true;
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		if (!Closure.deleteMemberByVariant(var))
			if (SuperClass != null)
				return SuperClass.deleteMemberByVariant(var);
			else
				return false;
		else
			return true;
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		if (SuperClass != null && !Closure.hasMemberByName(name, KEnvironment.DEFAULT))
			if (SuperClass.hasMemberByName(name, KEnvironment.THISONLY))
				return SuperClass.doOperationByName(op, name, opr);
			else
				return GlobalEnvironment.getGlobal().doOperationByName(op, name, opr);
		return Closure.doOperationByName(op, name, opr);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		if (SuperClass != null && !Closure.hasMemberByVariant(var) && SuperClass.hasMemberByVariant(var))
			return SuperClass.doOperationByVariant(op, var, opr);
		return Closure.doOperationByVariant(op, var, opr);
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		if (str.equals(clsname))
			return true;
		return SuperClass.isInstanceOf(str);
	}

	@Override
	public boolean isValid() throws KSException {
		return Closure == null;
	}

	@Override
	public boolean invalidate() throws KSException {
		if (Closure != null) {
			Closure = null;
			return true;
		}
		return false;
	}

	@Override
	public KObject newInstance() throws KSException {
		KObject Super = null;
		if (SuperClass != null) {
			Super = SuperClass.newInstance();
		}
		ExtendableClosure newInst = new ExtendableClosure(clsname, Super);
		Closure.EnumMembers((k, v) -> {
			try {
				if (v.getType().getType() == KObject.class) {
					KObject obj = (KObject) v.toType("Object");
					if (obj instanceof KProperty) {
						v = new KVariant(new PropertyClosure((KProperty) obj, newInst));
					} else if (obj instanceof CallableFunction) {
						v = new KVariant(new FunctionClosure(obj, newInst));
					} else
						return true;
					newInst.setMemberByVariant(k, v, KEnvironment.DEFAULT);
				}
			} catch (KSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
			return true;
		}, KEnvironment.IGNOREPROP | KEnvironment.THISONLY);
		return newInst;
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		Closure.EnumMembers(cosumer, flag);
	}

	@Override
	public void putNativeInstance(Object nis) throws KSException {
		natives.add(nis);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getNativeInstance(Class<T> cls) throws KSException {
		for (int i = 0; i < natives.size(); i++) {
			if (cls.isInstance(natives.get(i)))
				return (T) natives.get(i);
		}
		return null;
	}

	@Override
	public KEnvironment getThis() throws ScriptException {
		return this;
	}

	@Override
	public KEnvironment getSuper() throws InvalidSuperClassException {
		return SuperClass;
	}
}
