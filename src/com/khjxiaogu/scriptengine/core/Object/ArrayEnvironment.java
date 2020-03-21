package com.khjxiaogu.scriptengine.core.Object;

import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
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
	public KVariant getMemberByName(String name,int flag) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant getMemberByNum(int num,int flag) throws KSException {
		if (num < offset)
			return parent.getMemberByNum(num,flag);
		if (num - offset >= list.length)
			throw new MemberNotFoundException("%" + num);
		KVariant v;
		if ((v = list[num - offset]) == null)
			throw new MemberNotFoundException("%" + num);
		if((flag&KEnvironment.IGNOREPROP)!=1) {
			if(v.getType().getType()==KObject.class&&v.getValue() instanceof KProperty) {
				return ((KProperty)v.getValue()).getProp(null);
			}
		}
		return v;
	}

	@Override
	public KVariant getMemberByVariant(KVariant var,int flag) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val,int flag) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val,int flag) throws KSException {
		if (num < offset)
			return parent.setMemberByNum(num, val,flag);
		KVariant va=list[num - offset];
		if((flag&KEnvironment.IGNOREPROP)!=1) {
			if(va!=null&&va.getType().getType()==KObject.class&&va.getValue() instanceof KProperty) {
				((KProperty)va.getValue()).setProp(val,null);
			}
		}
		return list[num - offset] = val;
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val,int flag) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}
	@Override
	public boolean hasMemberByName(String name,int flag) throws KSException {
		throw new MemberNotFoundException(name);
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
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		throw new MemberNotFoundException(name);
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
		throw new MemberNotFoundException(name);
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
		switch (op) {
		case ADD:
			v.addby(opr);
			break;
		case ARSH:
			v.ARSHby(opr.getInt());
			break;
		case BAND:
			v.BANDby(opr);
			break;
		case BOR:
			v.BOR(opr);
			break;
		case BXOR:
			v.BXORby(opr);
			break;
		case DIV:
			v.divideby(opr);
			break;
		case EQ:
			v.setValue(opr);
			break;
		case FDIV:
			v.floorDivideby(opr);
			break;
		case LAND:
			v.set(v.asBoolean() && opr.asBoolean());
			break;
		case LOR:
			v.set(v.asBoolean() || opr.asBoolean());
			break;
		case LSH:
			v.LSHby(opr.getInt());
			break;
		case MIN:
			v.minusby(opr);
			break;
		case MOD:
			v.modby(opr);
			break;
		case MUL:
			v.multiplyby(opr);
			break;
		case RSH:
			v.RSHby(opr.getInt());
			break;
		}
		return v;
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KEnvironment objthis,int flag) throws KSException {
		KVariant res = list[num];
		if (res == null) {
			if (parent != null)
				return parent.funcCallByNum(num, args, objthis,flag);
		}
		if (res == null)
			throw new MemberNotFoundException("%" + num);
		KObject obj = (KObject) res.toType("Object");
		if (obj instanceof CallableFunction)
			return ((CallableFunction) obj).FuncCall(args, objthis == null ? this : objthis);
		else
			throw new ScriptException("呼叫的对象不是函数");
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KEnvironment objthis,int flag) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public void EnumMembers(Enumerator cosumer,int flag) throws KSException {
		for (int i = 0; i < list.length; i++) {
			KVariant va=list[i];
			if((flag&KEnvironment.IGNOREPROP)!=1) {
				if(va!=null&&va.getType().getType()==KObject.class&&va.getValue() instanceof KProperty) {
					va=((KProperty)va.getValue()).getProp(null);
				}
			}
			if (!cosumer.execute(new KVariant(i),va)) {
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
}
