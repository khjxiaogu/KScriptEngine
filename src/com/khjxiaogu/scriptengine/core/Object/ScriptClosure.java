package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.AccessDeniedException;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class ScriptClosure extends Closure {
	private String clsname;
	private KEnvironment objthis;

	public ScriptClosure(KEnvironment env, String inheritance) {
		super(new MapEnvironment(env));
		clsname = inheritance;
		objthis = env;
	}

	@Override
	public KVariant getMemberByName(String name) throws KSException {
		if (name != null && name.equals("this"))
			return new KVariant(this);
		return Closure.getMemberByName(name);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var) throws KSException {
		return Closure.getMemberByVariant(var);
	}

	@Override
	public KVariant getMemberByNameEnsure(String name) throws KSException {
		if (name != null && name.equals("this"))
			return new KVariant(this);
		return Closure.getMemberByNameEnsure(name);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val) throws KSException {
		if (name != null && name.equals("this"))
			throw new AccessDeniedException();
		return Closure.setMemberByName(name, val);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val) throws KSException {
		return Closure.setMemberByVariant(var, val);
	}

	@Override
	public KVariant setMemberByNameEnsure(String name, KVariant val) throws KSException {
		if (name != null && name.equals("this"))
			throw new AccessDeniedException();
		return Closure.setMemberByNameEnsure(name, val);
	}

	@Override
	public boolean hasMemberByName(String name) throws KSException {
		if (name != null && name.equals("this"))
			return true;
		return Closure.hasMemberByName(name);
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		return Closure.hasMemberByVariant(var);
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		if (name != null && name.equals("this"))
			throw new MemberNotFoundException("this");
		return Closure.deleteMemberByName(name);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		return Closure.deleteMemberByVariant(var);
	}

	@Override
	public KVariant DoOperatonByName(AssignOperation op, String name, KVariant opr) throws KSException {
		if (name != null && name.equals("this"))
			throw new AccessDeniedException();
		return Closure.DoOperatonByName(op, name, opr);
	}

	@Override
	public KVariant DoOperatonByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return Closure.DoOperatonByVariant(op, var, opr);
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		if (str.equals(clsname))
			return true;
		else if (objthis != null && objthis instanceof KObject && ((KObject) objthis).isInstanceOf(str))
			return true;
		return false;
	}

	@Override
	public boolean isValid() throws KSException {
		return Closure == null;
	}

	@Override
	public boolean invalidate() throws KSException {
		if (objthis != null) {
			objthis = null;
			Closure = null;
			return true;
		}
		return false;
	}

	@Override
	public KObject newInstance() throws KSException {
		throw new ContextException();
	}

}
