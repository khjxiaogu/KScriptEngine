package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class ArrayEnvironment implements KEnvironment {
	// private List<KVariant> list=Collections.synchronizedList(new ArrayList<>());
	private KVariant[] list;
	protected KEnvironment parent = null;
	int offset;

	public ArrayEnvironment(KEnvironment par, int off, int size) {
		list = new KVariant[size];
		offset = off;
		parent = par;
	}

	@Override
	public KVariant getMemberByName(String name) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant getMemberByNum(int num) throws KSException {
		if (num < offset)
			return parent.getMemberByNum(num);
		if (num - offset >= list.length)
			throw new MemberNotFoundException("%" + num);
		KVariant v;
		if ((v = list[num - offset]) == null)
			throw new MemberNotFoundException("%" + num);
		return v;
	}

	@Override
	public KVariant getMemberByVariant(KVariant var) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public KVariant getMemberByNameEnsure(String name) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val) throws KSException {
		if (num < offset)
			return parent.setMemberByNum(num, val);
		return list[num - offset] = val;
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public KVariant setMemberByNameEnsure(String name, KVariant val) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public boolean hasMemberByName(String name) throws KSException {
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
	public KVariant DoOperatonByName(AssignOperation op, String name, KVariant opr) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant DoOperatonByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		if (num < offset)
			return parent.DoOperatonByNum(op, num, opr);
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
	public KVariant DoOperatonByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}
}
