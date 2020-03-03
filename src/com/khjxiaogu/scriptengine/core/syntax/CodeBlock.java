package com.khjxiaogu.scriptengine.core.syntax;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Exception.ScriptException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;

public class CodeBlock implements Block {
	List<CodeNode> nodes = new ArrayList<>();
	StatementParser parser = new StatementParser();
	CodeBlockAttribute attr;
	String name;
	public CodeBlock(CodeBlockAttribute attr) {
		// TODO Auto-generated constructor stub
		this.attr = attr;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		CodeBlockEnvironment cbenv = new CodeBlockEnvironment(env, this,attr);
		int i = 0;
		if(nodes.size()==0)return null;
		try {
			KVariant result = new KVariant();
			for (; i < nodes.size(); i++) {
				result = nodes.get(i).eval(cbenv);
				if (cbenv.skipped) {
					break;
				}
			}
			if (attr == CodeBlockAttribute.STATEMENT) {
				return result;
			} else if (attr == CodeBlockAttribute.RETURNABLE) {
				return cbenv.ret;
			} else if (attr == CodeBlockAttribute.BREAKABLE) {
				if (cbenv.stopped) {
					return null;
				}
				return KVariant.TRUE;
			}
		} catch (ScriptException e) {
			e.filename = name;
			e.colume = 0;
			e.line = i+1;
			throw e;
		}
		return null;
	}

	public void put(CodeNode node) {
		nodes.add(node);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (CodeNode node : nodes) {
			sb.append(node.toString());
			sb.append(";\n");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		name = reader.getName();
		// TODO Auto-generated method stub
		try {
			while (true) {
				if (!reader.has()) {
					break;
				}
				char c = reader.read();
				while (Character.isWhitespace(c)) {
					c = reader.eat();
				}
				if (!reader.has()) {
					break;
				}
	
				if (c != '}') {
					if (attr == CodeBlockAttribute.STATEMENT) {
						put(parser.parseUntilOrEnd(reader, ';'));
					} else {
						put(parser.parseUntil(reader, ';'));
					}
				} else {
					break;
				}
			}
		} catch (SyntaxError e) {
			e.filename = name;
			e.colume = 0;
			e.line = nodes.size()+1;
			throw e;
		}
		return this;
	}

}
