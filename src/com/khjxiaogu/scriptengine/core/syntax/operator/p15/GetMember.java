package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.KVariantReference;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KEnvironmentReference;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.VisitContext;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorGetMember.java x[x]
 */
public class GetMember extends SingleOperator implements  Assignable,ASTParser {

	/**
	 *
	 */
	protected CodeNode under;
	public GetMember() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.Child.eval(env).asType(KObject.class).getMemberByVariant(under.eval(env),
				KEnvironment.DEFAULT);
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 15;
	}


	@Override
	public String toString() {
		return "(" + super.Child.toString() + "[" + under.toString() + "])";
	}


	@Override
	public void setChildren(CodeNode... codeNodes) throws KSException {
		// TODO Auto-generated method stub
		super.setChildren(codeNodes);
		if (!(super.Child instanceof Assignable))
			throw new SyntaxError("错误的表达式");
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		return super.Child.eval(env).asType(KObject.class).doOperationByVariant(op, under.eval(env), val);
	}


	@Override
	public void Visit(VisitContext context) throws KSException {
		Visitable.Visit(under, context);
		if (!(super.Child instanceof LiteralNode)) {
			Visitable.Visit(super.Child, context);
		}
	}


	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser stp=new StatementParser();
		under=stp.parseUntil(reader,']');
		return this;
	}

	@Override
	public Associative getAssociative() {
		return Associative.RIGHT;
	}

	@Override
	public KVariantReference evalAsRef(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KEnvironmentReference(super.Child.eval(env).asType(KObject.class),under.eval(env)).withFlag(KEnvironment.THISONLY);
	}
}
