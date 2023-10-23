package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class PropertyClosure extends Closure implements KProperty {
	protected KProperty backed;
	public PropertyClosure(KProperty prop, KEnvironment env) {
		super(env);
		backed = prop;
		while(backed instanceof PropertyClosure)
			backed=((PropertyClosure)backed).backed;
	}

	public PropertyClosure(KProperty prop) {
		super(null);
		backed=prop;
	}

	@Override
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException {
		if (name == null)
			return getProp(null);
		return super.getMemberByName(name, flag, objthis);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		if (name == null) {
			setProp(val, null);
			return val;
		}
		return getProp(null).asType(KObject.class).setMemberByName(name, val, flag);
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		if (name == null)
			return true;
		return getProp(null).asType(KObject.class).hasMemberByName(name, flag);
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KObject objthis, int flag) throws KSException {

		return getProp(null).asType(KObject.class).funcCallByName(name, args, objthis, flag);
	}

	@Override
	public KVariant getMemberByNum(int num, int flag) throws KSException {
		return getProp(null).asType(KObject.class).getMemberByNum(num, flag);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag) throws KSException {
		return getProp(null).asType(KObject.class).getMemberByVariant(var, flag);
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException {
		return getProp(null).asType(KObject.class).setMemberByNum(num, val, flag);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		return getProp(null).asType(KObject.class).setMemberByVariant(var, val, flag);
	}

	@Override
	public boolean hasMemberByNum(int num) throws KSException {
		return getProp(null).asType(KObject.class).hasMemberByNum(num);
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		return getProp(null).asType(KObject.class).hasMemberByVariant(var);
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		return getProp(null).asType(KObject.class).deleteMemberByName(name);
	}

	@Override
	public boolean deleteMemberByNum(int num) throws KSException {
		return getProp(null).asType(KObject.class).deleteMemberByNum(num);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		return getProp(null).asType(KObject.class).deleteMemberByVariant(var);
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		return getProp(null).asType(KObject.class).doOperationByName(op, name, opr);
	}

	@Override
	public KVariant doOperationByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		return getProp(null).asType(KObject.class).doOperationByNum(op, num, opr);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return getProp(null).asType(KObject.class).doOperationByVariant(op, var, opr);
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		return getProp(null).asType(KObject.class).equals(str);
	}

	@Override
	public boolean invalidate() throws KSException {
		return getProp(null).asType(KObject.class).invalidate();
	}

	@Override
	public KObject newInstance() throws KSException {
		return getProp(null).asType(KObject.class).newInstance();
	}

	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KObject objthis, int flag) throws KSException {
		return getProp(null).asType(KObject.class).funcCallByNum(num, args, objthis, flag);
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		getProp(null).asType(KObject.class).EnumMembers(cosumer, flag);
	}

	@Override
	public void setProp(KVariant x, KEnvironment env) throws KSException {
		if (env == null) {
			backed.setProp(x, closure);
		} else {
			backed.setProp(x, env);
		}
	}

	@Override
	public KVariant getProp(KEnvironment env) throws KSException {
		if (env == null)
			return backed.getProp(closure);
		return backed.getProp(env);
	}

	@Override
	public String getInstanceName() {
		return "Property";
	}

	@Override
	public String toString() {
		return "(Property)"+super.getInstancePointer();
	}

}
