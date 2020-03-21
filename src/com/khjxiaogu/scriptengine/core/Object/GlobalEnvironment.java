package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.AccessDeniedException;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class GlobalEnvironment extends Closure {
	private static GlobalEnvironment global = new GlobalEnvironment();

	public static GlobalEnvironment getGlobal() {
		return GlobalEnvironment.global;
	}

	public GlobalEnvironment() {
		super(new MapEnvironment());
	}

	@Override
	public KVariant getMemberByName(String name,int flag) throws KSException {
		return Closure.getMemberByName(name,flag);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var,int flag) throws KSException {
		return Closure.getMemberByVariant(var,flag);
	}


	@Override
	public KVariant setMemberByName(String name, KVariant val,int flag) throws KSException {
		return Closure.setMemberByName(name, val,flag);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val,int flag) throws KSException {
		return Closure.setMemberByVariant(var, val,flag);
	}

	@Override
	public boolean hasMemberByName(String name,int flag) throws KSException {
		return Closure.hasMemberByName(name,flag);
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		return Closure.deleteMemberByName(name);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		return Closure.deleteMemberByVariant(var);
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		return Closure.doOperationByName(op, name, opr);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return Closure.doOperationByVariant(op, var, opr);
	}

	@Override
	public boolean isValid() throws KSException {
		return Closure != null;
	}

	@Override
	public boolean invalidate() throws KSException {
		if (isValid()) {
			Closure = null;
			return true;
		}
		return false;
	}

	@Override
	public void EnumMembers(Enumerator cosumer,int flag) throws KSException {
		Closure.EnumMembers(cosumer,flag);
	}
}
