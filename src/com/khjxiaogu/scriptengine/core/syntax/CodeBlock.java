package com.khjxiaogu.scriptengine.core.syntax;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Exception.ScriptException;
import com.khjxiaogu.scriptengine.core.Object.CodeBlockEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;

public class CodeBlock implements CodeNode,ASTParser {
	List<CodeNode> nodes=new ArrayList<>();
	StatementParser parser=new StatementParser();
	CodeBlockAttribute attr;
	String name;
	boolean broked;
	public CodeBlock(CodeBlockAttribute attr) {
		// TODO Auto-generated constructor stub
		this.attr=attr;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		CodeBlockEnvironment cbenv=new CodeBlockEnvironment(env,this);
		int i=0;
		try {
		KVariant result = new KVariant();
		for(;i<nodes.size();i++) {
			result=nodes.get(i).eval(cbenv);
			if(broked)
				break;
		}
		if(attr==CodeBlockAttribute.STATEMENT)
			return result;
		}catch(ScriptException e) {
			e.filename=name;
			e.colume=0;
			e.line=i;
			throw e;
		}
		return null;
	}
	public void put(CodeNode node) {
		nodes.add(node);
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<nodes.size();i++) {
			sb.append(nodes.get(i).toString());
		}
		return sb.toString();
	}
	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		name=reader.getName();
		// TODO Auto-generated method stub
		while(true) {
			if(!reader.has())
				break;
			char c=reader.read();
			while(Character.isWhitespace(c))
				c=reader.eat();
			if(!reader.has())
				break;
			
			if(c!='}') {
				if(attr==CodeBlockAttribute.STATEMENT)
					put(parser.parseUntilOrEnd(reader,';'));
				else
					put(parser.parseUntil(reader,';'));
			}else
				break;
		}
		return this;
	}

	public void Break() {
		// TODO Auto-generated method stub
		
	}

	public void Return(KVariant val) {
		// TODO Auto-generated method stub
		
	}
}
