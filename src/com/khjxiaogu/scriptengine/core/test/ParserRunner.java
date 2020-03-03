package com.khjxiaogu.scriptengine.core.test;

import com.khjxiaogu.scriptengine.core.Parser;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;

public class ParserRunner {
	public static void main(String[] args) throws KSException {
		Parser p = Parser.getParser();
		CodeDialog cd = new CodeDialog();
		String s;
		while ((s = cd.showDialog()) != null) {
			//System.out.println(s);
			CodeNode cn = null;
			try {
				cn = p.parse(s);
				System.out.println("AST Parse result");
				System.out.println(cn.toString());
				System.out.println("result of expression in null context:");
				System.out.println(cn.eval(null));
			} catch (KSException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
		// Lexer l = new Lexer(new CodeDialog());
	}
}
