package com.khjxiaogu.scriptengine.core.syntax.statement;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.VisitContext;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.block.Block;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockEnvironment;

public class ForStatement implements Block {

	private CodeNode Init;
	private CodeNode Cond;
	private CodeNode Incr;
	private CodeNode Body;
	int numvars;
	int off;
	String[] symbol;
	public ForStatement() {
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser parser = new StatementParser();
		while (true) {
			if (!reader.has()) {
				break;
			}
			char c = reader.eatAllSpace();
			if (!reader.has()) {
				break;
			}
			if (c == '(' && Init == null) {
				parser.clear();
				c = reader.eat();
				// System.out.println(c);
				Init = parser.parseUntil(reader, ';');
				// System.out.println(reader.read());
				reader.eat();
				Cond = parser.parseUntil(reader, ';');
				reader.eat();
				Incr = parser.parseUntil(reader, ')');
				reader.eat();
			} else if (c == '{') {
				if (Incr == null)
					throw new SyntaxError("错误的for表达式");
				c = reader.eat();
				Body = new CodeBlock(CodeBlockAttribute.BREAKABLE).parse(reader);
				break;
			} else if (Incr != null) {
				parser.clear();
				Body = parser.parseUntilOrBlock(reader, ';');
				// reader.eat();
				break;
			}
		}
		if (Init == null || Cond == null || Incr == null || Body == null)
			throw new SyntaxError("错误的for表达式");
		return this;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		KEnvironment forenv=new CodeBlockEnvironment(env, off, numvars);
		for (Init.eval(forenv); Cond.eval(forenv).asBoolean(); Incr.eval(forenv)) {
			Body.eval(forenv);
		}
		return null;
	}

	@Override
	public String toString() {
		return "for(" + Init.toString() + ";" + Cond.toString() + ";" + Incr.toString() + ")\n" + Body.toString();
	}

	@Override
	public void Visit(VisitContext context) throws KSException {
		VisitContext allnodes = context.child();
		off=allnodes.getOffset();
		Visitable.Visit(Init, allnodes);
		Visitable.Visit(Cond, allnodes);
		Visitable.Visit(Incr, allnodes);
		numvars=allnodes.getCurrentSize();
		Visitable.Visit(Body, allnodes);
		
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}
}
