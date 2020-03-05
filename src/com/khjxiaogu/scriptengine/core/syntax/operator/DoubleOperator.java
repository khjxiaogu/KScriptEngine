package com.khjxiaogu.scriptengine.core.syntax.operator;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public abstract class DoubleOperator implements Operator {
	protected CodeNode left;
	protected CodeNode right;

	public DoubleOperator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract KVariant eval(KEnvironment env) throws KSException;

	@Override
	public abstract int getPriority();

	@Override
	public Associative getAssociative() {
		return Associative.RIGHT;
	}

	@Override
	public int getOperandCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	public abstract String getToken();

	@Override
	public String toString() {
		return "(" + left.toString() + getToken() + right.toString() + ")";
	}

	@Override
	public void setChildren(CodeNode... codeNodes) throws KSException {
		// TODO Auto-generated method stub
		if (codeNodes[0] != null) {
			left = codeNodes[0];
		}
		if (codeNodes.length > 1 && codeNodes[1] != null) {
			right = codeNodes[1];
		}
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		Visitable.Visit(left, parentMap);
		Visitable.Visit(right, parentMap);
	}

}
