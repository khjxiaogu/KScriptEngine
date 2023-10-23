package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.KVariantReference;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KEnvironmentReference;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.VisitContext;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;
import com.khjxiaogu.scriptengine.core.syntax.statement.FuncCall;

/**
 * @author khjxiaogu
 * @time 2020年2月16日
 */
public class Member extends DoubleOperator implements Assignable {

	/**
	 *
	 */
	public Member() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).asType(KObject.class).getMemberByName(((LiteralNode) super.right).getToken(),
				KEnvironment.MUSTEXIST, null);
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
	public void setChildren(CodeNode... codeNodes) throws KSException {
		// TODO Auto-generated method stub
		super.setChildren(codeNodes);
		if (!((super.left instanceof Assignable||super.left==null)&&(super.right instanceof LiteralNode||super.right==null)))
			throw new SyntaxError("错误的表达式");
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		return super.left.eval(env).asType(KObject.class).doOperationByName(op,
				((LiteralNode) super.right).getToken(), val);
	}


	@Override
	public void Visit(VisitContext context) throws KSException {
		Visitable.Visit(super.left, context);
	}

	@Override
	public KVariantReference evalAsRef(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KEnvironmentReference(super.left.eval(env).asType(KObject.class),((LiteralNode) super.right).getToken());
	}

}
