package com.khjxiaogu.scriptengine.core.syntax.operator;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;

public interface Operator extends CodeNode {

	public int getPriority();

	public Associative getAssociative();

	public int getOperandCount();

	public void setChildren(CodeNode... codeNodes) throws KSException;
}
