package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

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
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 */
public class Parentness implements CodeNode, ASTParser, MemberOperator {
	CodeNode inner;

	/**
	 * 
	 */
	public Parentness() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return inner.eval(env);
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		StatementParser sp = new StatementParser();
		inner = sp.parseUntil(reader, ')');
		return this;
	}

	@Override
	public String toString() {
		return inner.toString();
	}
	@Override
	public KVariant assign(KEnvironment env, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		if (inner instanceof Assignable) {
			return ((MemberOperator) inner).assign(env, val);
		}
		throw new ScriptException("错误的赋值表达式");
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (inner instanceof Assignable) {
			return ((MemberOperator) inner).getObject(env);
		}
		throw new ScriptException("错误的赋值表达式");
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		if (inner instanceof Assignable) {
			return ((MemberOperator) inner).assignOperation(env, val, op);
		}
		throw new ScriptException("错误的赋值表达式");
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		return ((MemberOperator) inner).getSuperEnvironment(env);
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return ((MemberOperator) inner).getPointing(env);
	}
	@Override
	public void Visit(List<String> parentMap) {
		Visitable.Visit(inner,parentMap);
	}
	@Override
	public void VisitAsChild(List<String> parentMap) {
		if(!(inner instanceof LiteralNode)) {
			if(inner instanceof MemberOperator)
				((MemberOperator)inner).VisitAsChild(parentMap);
			else
				Visitable.Visit(inner,parentMap);
		}
	}

}
