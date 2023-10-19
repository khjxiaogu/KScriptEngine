package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.CustomScriptException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.ArrayEnvironment;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.internal.ObjectException;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.block.Block;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockAttribute;

public class TryStatement implements Block {
	private CodeNode Try;
	private CodeNode Catch;
	String catchPar=null;
	int off;
	public TryStatement() {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		KVariant e=null;
		try{
			Try.eval(env);
		}catch(CustomScriptException cse) {
			e=cse.getException();
		}catch(Exception ex) {
			e=KVariant.valueOf(ObjectException.getException(new ScriptException(ex.getMessage())));
		}
		if(e!=null)
			if(catchPar!=null) {
				KEnvironment cenv=new ArrayEnvironment(env,off,new KVariant[] {e});
				Catch.eval(cenv);
			}else
				Catch.eval(env);
		return null;
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		char c = reader.eatAllSpace();
		//
		c = reader.eatAllSpace();
		if (c == '{') {
			c = reader.eat();
			Try = new CodeBlock(CodeBlockAttribute.NORMAL).parse(reader);
		}
		c=reader.eatAllSpace();
		if(c=='c'&&reader.eat()=='a'&&reader.eat()=='t'&&reader.eat()=='c'&&reader.eat()=='h') {
			reader.eat();
			c = reader.eatAllSpace();
			StringBuilder pb=new StringBuilder();
			if(c=='(') {
				c=reader.eatAllSpace();
				while((c=reader.eat())!=')') {
					pb.append(c);
				}
			}
			catchPar=pb.toString();
			reader.eat();
			c = reader.eatAllSpace();
			if (c == '{') {
				c = reader.eat();
				Catch = new CodeBlock(CodeBlockAttribute.NORMAL).parse(reader);
			}	
		}
		if (Try == null || Catch == null)
			throw new SyntaxError("错误的try表达式");
		return this;
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		Visitable.Visit(Try, parentMap);
		off = parentMap.size();
		List<String> catMap=new ArrayList<>(parentMap);
		if(catchPar!=null)
			catMap.add(catchPar);
		Visitable.Visit(Catch,catMap);
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}

	@Override
	public String toString() {
		return "try"+Try.toString()+"catch("+catchPar+"[%"+off+"])"+Catch.toString();
	}

}
