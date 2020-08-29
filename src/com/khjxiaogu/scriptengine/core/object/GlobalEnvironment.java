package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class GlobalEnvironment extends Closure {
	private static GlobalEnvironment global = new GlobalEnvironment();

	public static GlobalEnvironment getGlobal() {
		return GlobalEnvironment.global;
	}
	public static KEnvironment createGlobal() throws KSException {
		MapEnvironment me=new MapEnvironment();
		GlobalEnvironment.getGlobal().EnumMembers((k,v)->{me.setMemberByName(k.toString(), v, IGNOREPROP);return true;}, IGNOREPROP);
		return me;
	}
	public GlobalEnvironment() {
		super(new MapEnvironment());
		try {
			closure.setMemberByName("global",new KVariant(this), KEnvironment.DEFAULT);
		} catch (KSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public KVariant getMemberByName(String name, int flag) throws KSException {
		return closure.getMemberByName(name, 0);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag) throws KSException {
		return closure.getMemberByVariant(var,0);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		return closure.setMemberByName(name, val, 0);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		return closure.setMemberByVariant(var, val, 0);
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		return closure.hasMemberByName(name, 0);
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		return closure.deleteMemberByName(name);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		return closure.deleteMemberByVariant(var);
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		return closure.doOperationByName(op, name, opr);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return closure.doOperationByVariant(op, var, opr);
	}

	@Override
	public boolean isValid() throws KSException {
		return closure != null;
	}

	@Override
	public boolean invalidate() throws KSException {
		if (isValid()) {
			closure = null;
			return true;
		}
		return false;
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		closure.EnumMembers(cosumer,flag);
	}

	@Override
	public String getInstanceName() {
		return "Global";
	}
}
