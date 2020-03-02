package com.khjxiaogu.scriptengine.core.syntax.operator.p03;

import java.util.List;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年3月2日
 *       project:khjScriptEngine
 */
public class Var extends SingleOperator implements Assignable, MemberOperator {

	/**
	 * 
	 */
	public Var() {
	}


	@Override
	public KVariant assign(KEnvironment env, KVariant val) throws KSException {
		return env.setMemberByName(((LiteralNode) super.Child).getToken(), val);
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		throw new SyntaxError();
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		throw new SyntaxError();
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		throw new SyntaxError();
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		throw new SyntaxError();
	}

	@Override
	public void setChildren(CodeNode... codeNodes) throws KSException {
		super.setChildren(codeNodes);
		if (super.Child != null && !(super.Child instanceof LiteralNode)) {
			throw new SyntaxError();
		}
	}

	@Override
	public String toString() {
		return "var " + super.Child.toString();
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return env.setMemberByName(((LiteralNode) super.Child).getToken(), new KVariant());
	}

	@Override
	public int getPriority() {
		return 3;
	}

	@Override
	public Associative getAssociative() {
		return Associative.LEFT;
	}

}
