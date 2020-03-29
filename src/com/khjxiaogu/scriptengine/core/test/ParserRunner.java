package com.khjxiaogu.scriptengine.core.test;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.Parser;
import com.khjxiaogu.scriptengine.core.StringParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.GlobalEnvironment;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.NativeFunctionClosure;
import com.khjxiaogu.scriptengine.core.syntax.block.GlobalCodeBlock;

public class ParserRunner {
	public static void main(String[] args) {
		Parser p = Parser.getParser();
		CodeDialog cd = new CodeDialog();
		String s;
		while ((s = cd.showDialog()) != null) {
			// System.out.println(s);
			GlobalCodeBlock cn = new GlobalCodeBlock();
			try {
				GlobalEnvironment.getGlobal().setMemberByName("print",
						new KVariant(new NativeFunctionClosure<Object>((objthis, params) -> {
							if (params.length > 0) {
								System.out.println(params[0].toString());
							}
							return null;
						})),KEnvironment.DEFAULT);
				cn.parse(new StringParseReader(s));
				System.out.println("语法解析结果：");
				System.out.println(cn.toString());
				System.out.println("在global上下文执行结果：");
				System.out.println(cn.eval());
			} catch (KSException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
		// Lexer l = new Lexer(new CodeDialog());
	}
}
