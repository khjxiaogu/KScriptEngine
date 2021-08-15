package com.khjxiaogu.scriptengine.core.syntax;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;

public class StringNode implements CodeNode, ASTParser,ObjectOperator {
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
		// System.out.println(c);
		// System.out.println(start);
		while (c != start) {
			if (c == '\\') {
				c = reader.eat();
				switch (c) {
				case '\\':
					sb.append('\\');
					break;
				case '\'':
					sb.append('\'');
					break;
				case '"':
					sb.append('"');
					break;
				case 'a':
					sb.append('\u0007');
					break;
				case 'b':
					sb.append('\u0008');
					break;
				case 'f':
					sb.append('\u000C');
					break;
				case 'n':
					sb.append('\n');
					break;
				case 'r':
					sb.append('\r');
					break;
				case 't':
					sb.append('\t');
					break;
				case 'v':
					sb.append('\u000B');
					break;
				case 'X':// \x0008
				case 'x':
					StringBuilder x = new StringBuilder(8);
					char c1 = reader.eat();
					for (int i = 0; i < 4; i++) {
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
				c = reader.read();
				continue;
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

	@Override
	public void Visit(List<String> parentMap) throws KSException {
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
	}

	@Override
	public KEnvironment getObject(KEnvironment env) throws KSException {
		return new KVariant(variant).toType(KObject.class);
	}
}
