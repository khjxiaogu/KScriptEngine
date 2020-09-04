package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorGetMember.java x[x]
 */
public class GetMember extends DoubleOperator implements ObjectOperator, Assignable {

	/**
	 *
	 */
	public GetMember() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).toType(KObject.class).getMemberByVariant(super.right.eval(env),
				KEnvironment.DEFAULT);
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 15;
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
		return super.left
				.eval(env)
				.toType(KObject.class)
				.setMemberByVariant(
						super.right.eval(env)
						, val
						,KEnvironment.DEFAULT);
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return (KObject) ((ObjectOperator) super.left).getObject(env)
				.getMemberByVariant(super.right.eval(env), KEnvironment.DEFAULT).toType("Object");
	}

	@Override
	public void setChildren(CodeNode... codeNodes) throws KSException {
		// TODO Auto-generated method stub
		super.setChildren(codeNodes);
		if (!(super.left instanceof Assignable))
			throw new SyntaxError("错误的表达式");
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		return ((ObjectOperator) super.left).getObject(env).doOperationByVariant(op, super.right.eval(env), val);
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return super.right.eval(env);
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		Visitable.Visit(super.left, parentMap);
		if (!(super.right instanceof LiteralNode)) {
			Visitable.Visit(super.right, parentMap);
		}
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
		if (!(super.right instanceof LiteralNode)) {
			if (super.right instanceof ObjectOperator) {
				((ObjectOperator) super.right).VisitAsChild(parentMap);
			} else {
				Visitable.Visit(super.right, parentMap);
			}
		}
	}
}
