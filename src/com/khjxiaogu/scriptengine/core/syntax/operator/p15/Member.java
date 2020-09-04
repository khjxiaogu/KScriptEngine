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
import com.khjxiaogu.scriptengine.core.syntax.statement.FuncCall;

/**
 * @author khjxiaogu
 * @time 2020年2月16日
 */
public class Member extends DoubleOperator implements ObjectOperator, Assignable {

	/**
	 *
	 */
	public Member() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).toType(KObject.class).getMemberByName(((LiteralNode) super.right).getToken(),
				KEnvironment.MUSTEXIST);
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return ".";
	}

	@Override
	public KVariant assign(KEnvironment env, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		return  super.left.eval(env).toType(KObject.class).setMemberByName(((LiteralNode) super.right).getToken(), val,
				KEnvironment.MUSTEXIST);
	}

	@Override
	public KEnvironment getObject(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return ((ObjectOperator) super.left).getObject(env);
	}

	@Override
	public void setChildren(CodeNode... codeNodes) throws KSException {
		// TODO Auto-generated method stub
		super.setChildren(codeNodes);
		if (!((super.left instanceof ObjectOperator||super.left==null)&&(super.right instanceof LiteralNode||super.right==null)))
			throw new SyntaxError("错误的表达式");
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		return ((ObjectOperator) super.left).getObject(env).doOperationByName(op,
				((LiteralNode) super.right).getToken(), val);
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return ((LiteralNode) super.right).getPointing(env);
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		Visitable.Visit(super.left, parentMap);
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
		if (super.left instanceof ObjectOperator) {
			((ObjectOperator) super.right).VisitAsChild(parentMap);
		} else {
			Visitable.Visit(super.right, parentMap);
		}
	}
}
