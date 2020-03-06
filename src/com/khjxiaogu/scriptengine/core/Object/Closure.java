package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.AccessDeniedException;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

/**
 * @author khjxiaogu
 * @time 2020年3月5日
 *       project:khjScriptEngine
 */
public abstract class Closure implements KObject {

	/**
	 *
	 */
	protected KEnvironment Closure;

	public Closure(KEnvironment env) {
		Closure = env;
	}

	@Override
	public KVariant getMemberByName(String name) throws KSException {
		if (name != null && !name.equals("this"))
			throw new MemberNotFoundException(name);
		else
			return new KVariant(this);
	}

	@Override
	public KVariant getMemberByNum(int num) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public KVariant getMemberByNameEnsure(String name) throws KSException {
		if (name != null && !name.equals("this"))
			throw new MemberNotFoundException(name);
		else
			return new KVariant(this);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val) throws KSException {
		throw new AccessDeniedException();
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val) throws KSException {
		throw new MemberNotFoundException("%" + num);
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
	public KVariant DoOperatonByName(AssignOperation op, String name, KVariant opr) throws KSException {
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant DoOperatonByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant DoOperatonByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		throw new MemberNotFoundException(var.toString());
	}

	@Override
	public KVariant FuncCall(KVariant[] args, KEnvironment env) throws KSException {
		throw new MemberNotFoundException("");
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		return false;
	}

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

}
