package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class GlobalEnvironment extends KAbstractObject {
	private static GlobalEnvironment global = new GlobalEnvironment();
	private static KVariant globalv; 
	public static GlobalEnvironment getGlobal() {
		return GlobalEnvironment.global;
	}
	public static KVariant getGlobalVariant() {
		return GlobalEnvironment.globalv;
	}
	public static KEnvironment createGlobal() throws KSException {
		MapEnvironment me=new MapEnvironment();
		GlobalEnvironment.getGlobal().EnumMembers((k,v)->{me.setMemberByName(k.toString(), v, IGNOREPROP);return true;}, IGNOREPROP);
		return me;
	}
	private MapEnvironment map=new MapEnvironment();
	public GlobalEnvironment() {
		super();
		try {
			globalv=KVariant.valueOf(this);
			map.setMemberByName("global",globalv, KEnvironment.DEFAULT);
		} catch (KSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException {
		return map.getMemberByName(name, 0, null);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag, KObject objthis) throws KSException {
		return map.getMemberByVariant(var,0, null);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		return map.setMemberByName(name, val, 0);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		return map.setMemberByVariant(var, val, 0);
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		return map.hasMemberByName(name, 0);
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		return map.deleteMemberByName(name);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		return map.deleteMemberByVariant(var);
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		return map.doOperationByName(op, name, opr);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return map.doOperationByVariant(op, var, opr);
	}

	@Override
	public boolean isValid() throws KSException {
		return map != null;
	}

	@Override
	public boolean invalidate() throws KSException {
		if (isValid()) {
			map = null;
			return true;
		}
		return false;
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		map.EnumMembers(cosumer,flag);
	}

	@Override
	public String getInstanceName() {
		return "Global";
	}
}
