package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import java.util.List;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorGetMember.java x[x]
 */
public class GetMember extends DoubleOperator implements MemberOperator {

	/**
	 * 
	 */
	public GetMember() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return ((KObject) super.left.eval(env).asType("Object")).getMemberByVariant(super.right.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public void getClassPath(KEnvironment env, List<String> dest) throws KSException {
		// TODO Auto-generated method stub
		if (super.left instanceof MemberOperator) {
			((MemberOperator) super.left).getClassPath(env, dest);
		}
		dest.add((String) super.right.eval(env).asType("String"));
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "[]";
	}

	@Override
	public String toString() {
		return "(" + super.left.toString() + "[" + super.right.toString() + "])";
	}

	@Override
	public KVariant assign(KEnvironment env, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		return ((Assignable) super.left).getObject(env).setMemberByVariant(super.right.eval(env), val);
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return (KObject) ((Assignable) super.left).getObject(env).getMemberByVariant(super.right.eval(env))
				.toType("Object");
	}

	@Override
	public void setChildren(CodeNode... codeNodes) throws KSException {
		// TODO Auto-generated method stub
		super.setChildren(codeNodes);
		if (!(super.left instanceof Assignable)) {
			throw new SyntaxError("错误的表达式");
		}
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		return ((Assignable) super.left).getObject(env).DoOperatonByVariant(op, super.right.eval(env), val);
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		return ((Assignable) super.left).getObject(env);
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return super.right.eval(env);
	}
}
