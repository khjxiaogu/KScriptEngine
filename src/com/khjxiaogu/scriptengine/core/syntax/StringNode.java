package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public class StringNode implements CodeNode, ASTParser {
	String variant;

	public StringNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		char start = reader.read();
		char c = reader.eat();
		//System.out.println(c);
		//System.out.println(start);
		while (c != start) {
			if (c == '\\') {
				switch (c) {
				case '\\':
				case '\'':
				case '"':
					sb.append('\\');
					break;
				case 'a':
					sb.append('\u0007');
					c = reader.eat();
					break;
				case 'b':
					sb.append('\u0008');
					c = reader.eat();
					break;
				case 'f':
					sb.append('\u000C');
					c = reader.eat();
					break;
				case 'n':
					sb.append('\n');
					c = reader.eat();
					break;
				case 'r':
					sb.append('\r');
					c = reader.eat();
					break;
				case 't':
					sb.append('\t');
					c = reader.eat();
					break;
				case 'v':
					sb.append('\u000B');
					c = reader.eat();
					break;
				case 'X':
				case 'x':
					StringBuilder x = new StringBuilder(8);
					char c1 = reader.eat();
					for (int i = 0; i < 8; i++) {
						if (c1 >= '0' && c1 <= '9' || c1 >= 'a' && c1 <= 'f' || c1 >= 'A' && c1 <= 'F') {
							x.append(c1);
							c1 = reader.eat();
						} else {
							c = c1;
							break;
						}
					}
					sb.append((char) Integer.parseInt(x.toString(), 16));
					break;
				}
				break;
			}
			sb.append(c);
			c = reader.eat();
		}
		reader.eat();
		variant = sb.toString();
		return this;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KVariant(variant);
	}

	@Override
	public String toString() {
		return "\"" + variant + "\"";
	}
}
