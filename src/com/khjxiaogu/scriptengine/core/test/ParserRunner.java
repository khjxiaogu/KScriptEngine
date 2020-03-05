package com.khjxiaogu.scriptengine.core.test;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.Parser;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlock;

public class ParserRunner {
	public static void main(String[] args) throws KSException {
		Parser p = Parser.getParser();
		CodeDialog cd = new CodeDialog();
		String s;
		while ((s = cd.showDialog()) != null) {
			// System.out.println(s);
			CodeBlock cn = null;
			try {
				cn = (CodeBlock) p.parse(s);
				List<String> li;
				cn.Visit(li = new ArrayList<String>());
				System.out.println("语法解析结果：");
				System.out.println(cn.toString());
				System.out.println("在null上下文执行结果：");
				System.out.println(cn.eval(null));
			} catch (KSException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
		// Lexer l = new Lexer(new CodeDialog());
	}
}
