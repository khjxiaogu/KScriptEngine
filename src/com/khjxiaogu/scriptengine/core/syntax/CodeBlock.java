package com.khjxiaogu.scriptengine.core.syntax;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;

public class CodeBlock implements Block,Visitable {
	List<CodeNode> nodes = new ArrayList<>();
	StatementParser parser = new StatementParser();
	CodeBlockAttribute attr;
	String name;
	int off;
	int siz;
	public CodeBlock(CodeBlockAttribute attr) {
		// TODO Auto-generated constructor stub
		this.attr = attr;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		CodeBlockEnvironment cbenv = new CodeBlockEnvironment(env,off,siz, this,attr);
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
			} else if (attr == CodeBlockAttribute.BREAKABLE||attr==CodeBlockAttribute.SWITCH) {
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
	/**
	 * for class object,wont run Function
	 * @param env
	 * @return
	 * @throws KSException
	 */
	public void init(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		int i = 0;
		if(nodes.size()==0)return;
		try {
			for (; i < nodes.size(); i++) {
				CodeNode cn;
				cn = nodes.get(i);
				if(cn instanceof CodeBlock) {
					((CodeBlock) cn).attr=CodeBlockAttribute.OBJECT;
					((CodeBlock) cn).init(env);
				}
			}
		} catch (ScriptException e) {
			e.filename = name;
			e.colume = 0;
			e.line = i+1;
			throw e;
		}
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
					reader.eat();
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

	@Override
	public void Visit(List<String> parentMap) {
		off=parentMap.size();
		List<String> curmap=new ArrayList<>(parentMap);
		for(int i=0;i<nodes.size();i++) {
			CodeNode node=nodes.get(i);
				Visitable.Visit(node,curmap);
		}
		siz=curmap.size()-off;
	}

}
