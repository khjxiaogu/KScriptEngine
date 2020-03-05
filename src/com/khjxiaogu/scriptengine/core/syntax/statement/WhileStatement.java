package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.Block;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public class WhileStatement implements Block {

	private CodeNode Condition;
	private CodeNode Body;

	public WhileStatement() {
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

			if (c == '(' && Condition == null) {
				parser.clear();
				c = reader.eat();
				Condition = parser.parseUntilOrBlock(reader, ')');
			} else if (c == '{') {
				if (Condition == null)
					throw new SyntaxError("错误的while表达式");
				c = reader.eat();
				Body = new CodeBlock(CodeBlockAttribute.BREAKABLE).parse(reader);
				break;
			} else if (Condition != null) {
				parser.clear();
				Body = parser.parseUntilOrBlock(reader, ';');
				break;
			}
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
	public void Visit(List<String> parentMap) {
		Visitable.Visit(Condition, parentMap);
		Visitable.Visit(Body, parentMap);
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}

}
