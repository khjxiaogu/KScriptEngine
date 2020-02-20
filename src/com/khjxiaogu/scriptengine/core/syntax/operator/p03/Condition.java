package com.khjxiaogu.scriptengine.core.syntax.operator.p03;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.Operator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 * file:Conditon.java
 * x?x:x
 */
public class Condition implements Operator,ASTParser {
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
		if(cond.eval(env).asBoolean())
			return first.eval(env);
		else
			return other.eval(env);
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 2;
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
		if(codeNodes[0]!=null)
		cond=codeNodes[0];
		if(codeNodes.length>1&&codeNodes[1]!=null)
		other=codeNodes[1];
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		StatementParser sp=new StatementParser();
		first=sp.parseUntil(reader,':');
		return this;
	}
	@Override
	public String toString() {
		return "("+cond.toString()+"?("+first.toString()+"):("+other.toString()+"))";
	}
}
