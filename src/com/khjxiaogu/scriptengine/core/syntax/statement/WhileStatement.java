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

public class WhileStatement implements Block {

	private CodeNode Condition;
	private CodeNode Body;

	public WhileStatement() {
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser parser = new StatementParser();
		char c = reader.eatAllSpace();
		//

		if (c == '(') {
			parser.clear();
			c = reader.eat();
			Condition = parser.parseUntil(reader, ')');
			reader.eat();
		} else
			throw new SyntaxError("错误的while表达式");
		c = reader.eatAllSpace();
		if (c == '{') {
			c = reader.eat();
			Body = new CodeBlock(CodeBlockAttribute.BREAKABLE).parse(reader);
		} else {
			Body = parser.parseUntilOrBlock(reader, ';');
		}
		if (Condition == null || Body == null)
			throw new SyntaxError("错误的while表达式");
		return this;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		while (Condition.eval(env).asBoolean()) {
			Body.eval(env);
		}
		return null;
	}

	@Override
	public String toString() {
		return "while(" + Condition.toString() + ")\n" + Body.toString();
	}

	@Override
	public void Visit(VisitContext context) throws KSException {
		Visitable.Visit(Condition, context);
		Visitable.Visit(Body, context);
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}

}
