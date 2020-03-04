package com.khjxiaogu.scriptengine.core.Object;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class MapEnvironment implements KEnvironment {
	private Map<String, KVariant> map = new ConcurrentHashMap<>();
	protected KEnvironment parent = null;

	public MapEnvironment(KEnvironment parent) {
		// TODO Auto-generated constructor stub
		this.parent = parent;
	}

	@Override
	public KVariant getMemberByName(String name) throws KSException {
		// TODO Auto-generated method stub
		KVariant res = map.get(name);
		if (res == null) {
			return new KVariant();
		}
		return res;
	}

	@Override
	public KVariant getMemberByNameEnsure(String name) throws KSException {
		// TODO Auto-generated method stub
		KVariant res = map.get(name);
		if (res == null) {
			if (parent != null) {
				res = parent.getMemberByName(name);
			}
		}
		if (res == null) {
			throw new MemberNotFoundException(name);
		}
		return res;
	}

	@Override
	public KVariant getMemberByNum(int num) throws KSException {
		// TODO Auto-generated method stub
		KVariant res = map.get(Integer.toString(num));
		if (res == null) {
			return new KVariant();
		}
		return res;
	}

	@Override
	public KVariant getMemberByVariant(KVariant var) throws KSException {
		// TODO Auto-generated method stub
		KVariant res = null;
		String name = (String) var.toType("String");
		res = map.get(name);
		if (res == null) {
			res = new KVariant();
		}
		return res;
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		map.put(name, val);
		return val;
	}

	@Override
	public KVariant setMemberByNameEnsure(String name, KVariant val) throws KSException {
		if (map.containsKey(name)) {
			map.put(name, val);
			return val;
		} else if (parent != null) {
			return parent.setMemberByNameEnsure(name, val);
		}
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		String name = Integer.toString(num);
		map.put(name, val);
		return val;
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		String name = null;
		name = (String) var.toType("String");
		map.put(name, val);
		return val;
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMemberByNum(int num) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public KVariant DoOperatonByName(AssignOperation op, String name, KVariant opr) throws KSException {
		KVariant v=map.get(name);
		if(v==null)
			throw new MemberNotFoundException(name);
		switch(op) {
		case ADD:v.addby(opr);break;
		case ARSH:v.ARSHby(opr.getInt());break;
		case BAND:v.BANDby(opr);break;
		case BOR:v.BOR(opr);break;
		case BXOR:v.BXORby(opr);break;
		case DIV:v.divideby(opr);break;
		case EQ:v.setValue(opr);break;
		case FDIV:v.floorDivideby(opr);break;
		case LAND:v.set(v.asBoolean() && opr.asBoolean());break;
		case LOR:v.set(v.asBoolean() || opr.asBoolean());break;
		case LSH:v.LSHby(opr.getInt());break;
		case MIN:v.minusby(opr);break;
		case MOD:v.modby(opr);break;
		case MUL:v.multiplyby(opr);break;
		case RSH:v.RSHby(opr.getInt());break;
		}
		return v;
	}

	@Override
	public KVariant DoOperatonByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		return null;
	}

	@Override
	public KVariant DoOperatonByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return null;
	}

	@Override
	public boolean hasMemberByName(String name) throws KSException {
		return map.containsKey(name) || parent != null && parent.hasMemberByName(name);
	}

	@Override
	public boolean hasMemberByNum(int num) throws KSException {
		return map.containsKey(Integer.toString(num)) || parent != null && parent.hasMemberByNum(num);
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		return map.containsKey(var.toType("String")) || parent != null && parent.hasMemberByVariant(var);
	}



}
