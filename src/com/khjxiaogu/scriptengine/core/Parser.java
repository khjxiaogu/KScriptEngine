package com.khjxiaogu.scriptengine.core;

import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;

public class Parser {
	private static Parser parser = new Parser();

	public Parser() {
		// TODO Auto-generated constructor stub
	}

	public static Parser getParser() {
		return Parser.parser;
	}

	public CodeNode parse(String s) throws KSException {
		// System.out.println(s);
		ParseReader reader = new StringParseReader(s);
		CodeBlock cb=(CodeBlock) new CodeBlock(CodeBlockAttribute.STATEMENT).parse(reader);
		return cb;
	}
	public CodeNode parse(String s,String[] symbols) throws KSException {
		// System.out.println(s);
		ParseReader reader = new StringParseReader(s);
		CodeBlock cb=(CodeBlock) new CodeBlock(CodeBlockAttribute.STATEMENT).parse(reader);
		try {
		cb.Visit(Arrays.asList(symbols));
		}catch(UnsupportedOperationException e) {
			throw new SyntaxError("var");
		}
		return cb;
	}
}
