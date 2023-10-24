package com.khjxiaogu.scriptengine.core.object;

import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.NotImplementedException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class ArrayEnvironment implements KEnvironment {
	private KVariant[] list;
	protected KEnvironment parent = null;
	int offset;

	public ArrayEnvironment(KEnvironment par, int off, int size) {
		list = new KVariant[size];
		offset = off;
		parent = par;
	}

	public ArrayEnvironment(KEnvironment par, int off, int size, KVariant[] initial) {
		list = Arrays.copyOf(initial, size);
		offset = off;
		parent = par;
	}

	public ArrayEnvironment(ArrayEnvironment par, int size, KVariant[] initial) {
		list = Arrays.copyOf(initial, size);
		offset = par.list.length + par.offset;
		parent = par;
	}

	public ArrayEnvironment(KEnvironment par, int off, KVariant[] initial) {
		list = initial;
		offset = off;
		parent = par;
	}

	public ArrayEnvironment(ArrayEnvironment par, KVariant[] initial) {
		list = initial;
		offset = par.list.length + par.offset;
		parent = par;
	}

	@Override
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException {
		return parent.getMemberByName(name,KEnvironment.MUSTEXIST, null);
		//throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant getMemberByNum(int num, int flag) throws KSException {
		if (num < offset)
			return parent.getMemberByNum(num, flag);
		if (num - offset >= list.length)
			throw new MemberNotFoundException("%" + num);
		KVariant v;
		if ((v = list[num - offset]) == null)
			throw new MemberNotFoundException("%" + num);
		if ((flag & KEnvironment.IGNOREPROP) ==0) {
			if (v.getType().getType() == KObject.class)
				return v.asObject().getMemberByName(null, flag, null);
		}
		return v;
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag, KObject objthis) throws KSException {
		return parent.getMemberByVariant(var,KEnvironment.MUSTEXIST, null);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		return parent.setMemberByName(name,val,KEnvironment.MUSTEXIST);
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException {
		if (num < offset)
			return parent.setMemberByNum(num, val, flag);
		KVariant va = list[num - offset];
		if ((flag & KEnvironment.IGNOREPROP) ==0) {
			if (va != null && va.getType().getType() == KObject.class) {
				try {
					return va.asObject().setMemberByName(null, val, flag);
				}catch(NotImplementedException ignored) {}
			}
		}
		return list[num - offset] = val;
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		return parent.setMemberByVariant(var, val,KEnvironment.MUSTEXIST);
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		return parent.hasMemberByName(name,KEnvironment.MUSTEXIST);
	}

	@Override
	public boolean hasMemberByNum(int num) throws KSException {
		if (num < offset)
			return parent.hasMemberByNum(num);
		if (num - offset >= list.length)
			throw new MemberNotFoundException("%" + num);
		return list[num - offset] != null;
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		return parent.hasMemberByVariant(var);
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		return parent.deleteMemberByName(name);
	}

	@Override
	public boolean deleteMemberByNum(int num) throws KSException {
		if (num < offset)
			return parent.deleteMemberByNum(num);
		if (num - offset >= list.length)
			throw new MemberNotFoundException("%" + num);
		if (list[num - offset] == null)
			return false;
		list[num - offset] = null;
		return true;
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		return parent.doOperationByName(op, name, opr);
	}

	@Override
	public KVariant doOperationByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		if (num < offset)
			return parent.doOperationByNum(op, num, opr);
		if (num - offset >= list.length)
			throw new MemberNotFoundException("%" + num);
		KVariant v = list[num - offset];
		if (v == null)
			throw new MemberNotFoundException("%" + num);
		return v.doOperation(op, opr,new KEnvironmentReference(this,num));
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return parent.doOperationByVariant(op, var, opr);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		return parent.deleteMemberByVariant(var);
	}

	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KObject objthis, int flag) throws KSException {
		KVariant res = list[num];
		if (res == null) {
			if (parent != null)
				return parent.funcCallByNum(num, args, objthis, flag);
		}
		if (res == null)
			throw new MemberNotFoundException("%" + num);
		KObject obj = (KObject) res.asType("Object");
		return obj.funcCallByName(null, args, objthis, KEnvironment.THISONLY);
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KObject objthis, int flag) throws KSException {
		return parent.funcCallByName(name, args, objthis, KEnvironment.MUSTEXIST);
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		for (int i = 0; i < list.length; i++) {
			KVariant va = list[i];
			if ((flag & KEnvironment.IGNOREPROP) ==0) {
				if (va != null && va.isObject()) {
					va = va.asObject().getMemberByName(null, flag, null);
				}
			}
			if (!cosumer.execute(KVariant.valueOf(i), va)) {
				break;
			}
		}
	}

	@Override
	public <T> T getNativeInstance(Class<T> cls) throws KSException {
		return parent.getNativeInstance(cls);
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

	@Override
	public boolean hasNativeInstance(Class<?> cls) throws KSException {
		return false;
	}
}
