package com.khjxiaogu.scriptengine.core.syntax.operator;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public interface Operator extends CodeNode,Visitable {

	public int getPriority();

	public Associative getAssociative();

	public int getOperandCount();

	public void setChildren(CodeNode... codeNodes) throws KSException;
}
