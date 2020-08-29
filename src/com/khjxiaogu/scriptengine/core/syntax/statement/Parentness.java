package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.AssignException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 */
public class Parentness implements CodeNode, ASTParser, ObjectOperator, Assignable {
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
		reader.eat();
		return this;
	}

	@Override
	public String toString() {
		return inner.toString();
	}

	@Override
	public KVariant assign(KEnvironment env, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		if (inner instanceof Assignable)
			return ((Assignable) inner).assign(env, val);
		throw new AssignException(inner.toString());
	}

	@Override
	public KEnvironment getObject(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (inner instanceof ObjectOperator)
			return ((ObjectOperator) inner).getObject(env);
		throw new AssignException(inner.toString());
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		if (inner instanceof Assignable)
			return ((Assignable) inner).assignOperation(env, val, op);
		throw new AssignException(inner.toString());
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		return ((ObjectOperator) inner).getSuperEnvironment(env);
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return ((ObjectOperator) inner).getPointing(env);
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		Visitable.Visit(inner, parentMap);
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
		if (!(inner instanceof LiteralNode)) {
			if (inner instanceof ObjectOperator) {
				((ObjectOperator) inner).VisitAsChild(parentMap);
			} else {
				Visitable.Visit(inner, parentMap);
			}
		}
	}

}
