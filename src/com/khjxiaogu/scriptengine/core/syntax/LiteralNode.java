package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;

/**
 * @author khjxiaogu
 * @time 2020年2月16日
 * file:LiteralNode.java
 */
public class LiteralNode implements CodeNode,ASTParser {

	/**
	 * 
	 */
	String token;
	public LiteralNode() {
		// TODO Auto-generated constructor stub
	}

	public LiteralNode(String string) {
		// TODO Auto-generated constructor stub
		token=string;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return env.getMemberByName(token);
	}
	public String getToken() {
		return token;
	}
	@Override
	public String toString() {
		return token;
	}
	@Override
	public CodeNode parse(ParseReader reader) {
		// TODO Auto-generated method stub
		return this;
	}

}
