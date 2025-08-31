package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.AccessDeniedException;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.NotImplementedException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

/**
 * @author khjxiaogu
 * @time 2020年3月5日
 *       project:khjScriptEngine
 */
public abstract class KAbstractObject implements KObject {


	public KAbstractObject() {
	}

	@Override
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException {
		if (name != null)
			throw new MemberNotFoundException(name);
		return KVariant.valueOf(this);
	}

	@Override
	public KVariant getMemberByNum(int num, int flag) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag, KObject objthis) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		throw name==null?new NotImplementedException():new MemberNotFoundException(name);
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public boolean hasMemberByNum(int num) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public boolean deleteMemberByNum(int num) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant doOperationByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		return str.equals(getInstanceName());
	}
	@Override
	public String toString() {
		return "("+getInstanceName()+")"+("0x"+Integer.toHexString(this.hashCode()));
	}
	public abstract String getInstanceName();
	@Override
	public boolean isValid() throws KSException {
		return true;
	}

	@Override
	public boolean invalidate() throws KSException {
		return false;
	}

	@Override
	public KObject newInstance() throws KSException {
		throw new ContextException();
	}
	@Override
	public void callConstructor(KVariant[] args, KObject objthis) throws KSException {
		throw new ContextException();
	}
	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KObject objthis, int flag) throws KSException {
		throw new MemberNotFoundException(num);
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KObject objthis, int flag) throws KSException {
		if (name == null && this instanceof CallableFunction)
			return ((CallableFunction) this).FuncCall(args, objthis);
		throw new MemberNotFoundException(name);
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
	}

	@Override
	public <T> T getNativeInstance(Class<T> cls) throws KSException {
		return null;
	}
	public boolean hasNativeInstance(Class<?> cls) throws KSException {
		return false;
	}

	@Override
	public void putNativeInstance(Object nis) throws KSException {
		throw new ContextException();
	}

	@Override
	public KEnvironment getThis() throws KSException {
		throw new ScriptException("无法定位this的类。");
	}

	@Override
	public KEnvironment getSuper() throws KSException {
		throw new InvalidSuperClassException();
	}
}
