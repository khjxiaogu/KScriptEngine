package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;

public class ForStatement implements Block {

	private CodeNode Init;
	private CodeNode Cond;
	private CodeNode Incr;
	private CodeNode Body;
	public ForStatement() {
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser parser = new StatementParser();
		while (true) {
			if (!reader.has()) {
				break;
			}
			char c = reader.read();
			//
			while (Character.isWhitespace(c)) {
				c = reader.eat();
			}
			if (!reader.has()) {
				break;
			}
			if (c == '('&&Init==null) {
				parser.clear();
				c=reader.eat();
				Init=parser.parseUntil(reader,';');
				Cond=parser.parseUntil(reader, ';');
				Incr=parser.parseUntil(reader,')');
			}else if (c == '{') {
				if(Incr==null)
					throw new SyntaxError("错误的for表达式");
				c=reader.eat();
				Body=new CodeBlock(CodeBlockAttribute.BREAKABLE).parse(reader);
				break;
			}else if(Incr!=null) {
				parser.clear();
				Body=parser.parseUntil(reader,';');
				break;
			}
		}
		if(Init==null||Cond==null||Incr==null||Body==null)
			throw new SyntaxError("错误的for表达式");
		return this;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		for(Init.eval(env);Cond.eval(env).asBoolean();Incr.eval(env))
			Body.eval(env);
		return null;
	}
	@Override
	public String toString() {
		return "for("+Init.toString()+";"+Cond.toString()+";"+Incr.toString()+")\n"+Body.toString();
	}
}
