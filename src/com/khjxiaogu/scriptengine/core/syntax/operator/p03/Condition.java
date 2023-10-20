package com.khjxiaogu.scriptengine.core.syntax.operator.p03;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.KVariantReference;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.VisitContext;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.Operator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 *       file:Conditon.java
 *       x?x:x
 */
public class Condition implements Operator, ASTParser, ObjectOperator, Assignable {
	CodeNode cond;
	CodeNode first;
	CodeNode other;

	/**
	 *
	 */
	public Condition() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (cond.eval(env).asBoolean())
			return first.eval(env);
		else
			return other.eval(env);
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Associative getAssociative() {
		// TODO Auto-generated method stub
		return Associative.RIGHT;
	}

	@Override
	public int getOperandCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void setChildren(CodeNode... codeNodes) {
		// TODO Auto-generated method stub
		if (codeNodes[0] != null) {
			cond = codeNodes[0];
		}
		if (codeNodes.length > 1 && codeNodes[1] != null) {
			other = codeNodes[1];
		}
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		StatementParser sp = new StatementParser();
		first = sp.parseUntil(reader, ':');
		reader.eat();
		return this;
	}

	@Override
	public String toString() {
		return "(" + cond.toString() + "?(" + first.toString() + "):(" + other.toString() + "))";
	}

	@Override
	public KEnvironment getObject(KEnvironment env) throws KSException {
		CodeNode cn;
		if (cond.eval(env).asBoolean()) {
			cn = first;
		} else {
			cn = other;
		}
		if (cn instanceof ObjectOperator)
			return ((ObjectOperator) cn).getObject(env);
		throw new ScriptException("错误的赋值表达式");
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		CodeNode cn;
		if (cond.eval(env).asBoolean()) {
			cn = first;
		} else {
			cn = other;
		}
		if (cn instanceof Assignable)
			return ((Assignable) cn).assignOperation(env, val, op);
		throw new ScriptException("错误的赋值表达式");
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		throw new ScriptException("条件运算符不能使用在delete以后");
	}

	@Override
	public void Visit(VisitContext context) throws KSException {
		Visitable.Visit(cond, context);
		Visitable.Visit(first, context);
		Visitable.Visit(other, context);
	}

	@Override
	public KVariantReference evalAsRef(KEnvironment env) throws KSException {

		CodeNode cn;
		if (cond.eval(env).asBoolean()) {
			cn = first;
		} else {
			cn = other;
		}
		if (cn instanceof Assignable)
			return ((Assignable) cn).evalAsRef(env);
		throw new ScriptException("错误的赋值表达式");

	}

}
