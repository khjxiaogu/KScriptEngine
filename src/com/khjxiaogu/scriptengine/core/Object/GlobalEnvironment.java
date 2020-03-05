package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.AccessDeniedException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class GlobalEnvironment extends ScriptClosure {
	private static GlobalEnvironment global=new GlobalEnvironment();
	public static GlobalEnvironment getGlobal() {
		return global;
	}
	public GlobalEnvironment() {
		super(new MapEnvironment(null));
		try {
			Closure.setMemberByName("global",new KVariant(this));
		} catch (KSException e) {
			// TODO Auto-generated catch block
			System.out.println("engine start failure");
			e.printStackTrace();
			System.exit(0);
		}
	}
	@Override
	public KVariant getMemberByName(String name) throws KSException {
		return Closure.getMemberByName(name);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var) throws KSException {
		return Closure.getMemberByVariant(var);
	}

	@Override
	public KVariant getMemberByNameEnsure(String name) throws KSException {
		return Closure.getMemberByNameEnsure(name);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val) throws KSException {
		if(name.equals("global"))
			throw new AccessDeniedException();
		return Closure.setMemberByName(name, val);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val) throws KSException {
		if(var.toString().equals("global"))
			throw new AccessDeniedException();
		return Closure.setMemberByVariant(var, val);
	}

	@Override
	public KVariant setMemberByNameEnsure(String name, KVariant val) throws KSException {
		if(name.equals("global"))
			throw new AccessDeniedException();
		return Closure.setMemberByNameEnsure(name, val);
	}

	@Override
	public boolean hasMemberByName(String name) throws KSException {
		return Closure.hasMemberByName(name);
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		if(name.equals("global"))
			throw new AccessDeniedException();
		return Closure.deleteMemberByName(name);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		if(var.toString().equals("global"))
			throw new AccessDeniedException();
		return Closure.deleteMemberByVariant(var);
	}

	@Override
	public KVariant DoOperatonByName(AssignOperation op, String name, KVariant opr) throws KSException {
		if(name.equals("global"))
			throw new AccessDeniedException();
		return Closure.DoOperatonByName(op, name, opr);
	}

	@Override
	public KVariant DoOperatonByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		if(var.toString().equals("global"))
			throw new AccessDeniedException();
		return Closure.DoOperatonByVariant(op, var, opr);
	}

	@Override
	public boolean isValid() throws KSException {
		return Closure!=null;
	}

	@Override
	public boolean invalidate() throws KSException {
		if(isValid()) {
			Closure=null;
			return true;
		}
		return false;
	}



}
