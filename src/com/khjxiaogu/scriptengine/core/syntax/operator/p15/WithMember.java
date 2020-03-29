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
import com.khjxiaogu.scriptengine.core.syntax.WithEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

public class WithMember extends SingleOperator implements MemberOperator,Assignable {

	public WithMember() {
	}

	@Override
	public KVariant assign(KEnvironment env, KVariant val) throws KSException {
		if (!(env instanceof WithEnvironment))
			throw new SyntaxError("错误的.");
		return ((WithEnvironment) env).getWith().setMemberByName(((LiteralNode) super.Child).getToken(), val,KEnvironment.MUSTEXIST);
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		if (!(env instanceof WithEnvironment))
			throw new SyntaxError("错误的.");
		return ((WithEnvironment) env).getWith().doOperationByName(op, ((LiteralNode) super.Child).getToken(), val);
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		if (!(env instanceof WithEnvironment))
			throw new SyntaxError("错误的.");
		return ((WithEnvironment) env).getWith();
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return ((LiteralNode) super.Child).getPointing(env);
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		return;
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
		return;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		if (!(env instanceof WithEnvironment))
			throw new SyntaxError("错误的.");
		return ((WithEnvironment) env).getWith().getMemberByName(((LiteralNode) super.Child).getToken(),KEnvironment.MUSTEXIST);
	}

	@Override
	public int getPriority() {
		return 15;
	}

	@Override
	public void setChildren(CodeNode... codeNodes) throws KSException {
		super.setChildren(codeNodes);
		if (!(super.Child instanceof LiteralNode))
			throw new SyntaxError("错误的表达式");
	}

	@Override
	public Associative getAssociative() {
		return Associative.LEFT;
	}

}
