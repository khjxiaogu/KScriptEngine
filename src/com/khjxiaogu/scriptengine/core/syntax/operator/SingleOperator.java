package com.khjxiaogu.scriptengine.core.syntax.operator;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public abstract class SingleOperator implements Operator {
	protected CodeNode Child;

	public SingleOperator() {
	}

	@Override
	public abstract KVariant eval(KEnvironment env) throws KSException;

	@Override
	public abstract int getPriority();

	@Override
	public abstract Associative getAssociative();

	@Override
	public int getOperandCount() {
		return 1;
	}

	@Override
	public void setChildren(CodeNode... codeNodes) throws KSException {
		if (codeNodes[0] != null) {
			if (getAssociative() == Associative.RIGHT) {
				Child = codeNodes[0];
			} else
				throw new SyntaxError("Unexpected 'operator' position,expected ';'");
		}
		if (codeNodes.length > 1 && codeNodes[1] != null) {
			if (getAssociative() == Associative.LEFT) {
				Child = codeNodes[1];
			} else
				throw new SyntaxError("Unexpected 'operator' position,expected ';'");
		}
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		Visitable.Visit(Child, parentMap);
	}

	public CodeNode getChild() {
		return Child;
	}

}
