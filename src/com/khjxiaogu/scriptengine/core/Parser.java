package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;

public class Parser {
	private static Parser parser = new Parser();

	public Parser() {
		// TODO Auto-generated constructor stub
	}

	public static Parser getParser() {
		return parser;
	}

	public CodeNode parse(String s) throws KSException {
		// System.out.println(s);
		ParseReader reader = new StringParseReader(s);
		return new CodeBlock(CodeBlockAttribute.STATEMENT).parse(reader);
	}
}
