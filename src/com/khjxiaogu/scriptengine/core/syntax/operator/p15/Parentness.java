package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 * file:Parentness.java
 */
public class Parentness implements CodeNode,ASTParser{
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
		StatementParser sp=new StatementParser();
		inner=sp.parseUntil(reader,')');
		return this;
	}
	@Override
	public String toString() {
		return inner.toString();
	}

}
