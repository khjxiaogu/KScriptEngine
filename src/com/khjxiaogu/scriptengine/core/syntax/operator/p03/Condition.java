package com.khjxiaogu.scriptengine.core.syntax.operator.p03;

import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.Operator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 *  file:Conditon.java
 *   x?x:x
 */
public class Condition implements Operator, ASTParser, MemberOperator {
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
		if (cond.eval(env).asBoolean()) {
			return first.eval(env);
		} else {
			return other.eval(env);
		}
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
		first = sp.parseUntilOrBlock(reader, ':');
		return this;
	}

	@Override
	public String toString() {
		return "(" + cond.toString() + "?(" + first.toString() + "):(" + other.toString() + "))";
	}

	@Override
	public KVariant assign(KEnvironment env, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		CodeNode cn;
		if (cond.eval(env).asBoolean()) {
			cn = first;
		} else {
			cn = other;
		}
		if (cn instanceof Assignable) {
			return ((MemberOperator) cn).assign(env, val);
		}
		throw new ScriptException("错误的赋值表达式");
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		CodeNode cn;
		if (cond.eval(env).asBoolean()) {
			cn = first;
		} else {
			cn = other;
		}
		if (cn instanceof Assignable) {
			return ((MemberOperator) cn).getObject(env);
		}
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
		if (cn instanceof Assignable) {
			return ((MemberOperator) cn).assignOperation(env, val, op);
		}
		throw new ScriptException("错误的赋值表达式");
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		throw new ScriptException("条件运算符不能使用在delete以后");
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		throw new ScriptException("条件运算符不能使用在delete以后");
	}

	@Override
	public void Visit(List<String> parentMap) {
		Visitable.Visit(cond,parentMap);
		Visitable.Visit(first,parentMap);
		Visitable.Visit(other,parentMap);
	}

	@Override
	public void VisitAsChild(List<String> parentMap) {
		Visit(parentMap);
	}
}
