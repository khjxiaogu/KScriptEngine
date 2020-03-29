package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.block.Block;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockAttribute;

public class IfStatement implements Block {
	private CodeNode Condition;
	private CodeNode If;
	private CodeNode Else;

	public IfStatement() {
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
			c = reader.eat();
			// System.out.println(c);
		}
		c = reader.eatAllSpace();
		if (c == '{') {
			c = reader.eat();
			If = new CodeBlock(CodeBlockAttribute.NORMAL).parse(reader);
			// c=reader.eat();
		} else {
			If = parser.parseUntilOrBlock(reader, ';');
		}
		c = reader.eatAllSpace();
		if (c == 'e' && reader.reads(0, 4).equals("else")) {
			reader.eat();
			reader.eat();
			reader.eat();
			reader.eat();
			c = reader.eatAllSpace();
			if (c == '{') {
				c = reader.eat();
				Else = new CodeBlock(CodeBlockAttribute.NORMAL).parse(reader);
				// c=reader.eat();
			} else {
				Else = parser.parseUntilOrBlock(reader, ';');
			}
			reader.eat();
		}
		return this;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		if (Condition.eval(env).asBoolean()) {
			If.eval(env);
		} else if (Else != null) {
			Else.eval(env);
		}
		return null;
	}

	@Override
	public String toString() {
		return "if(" + Condition.toString() + ")\n" + If.toString()
				+ (Else != null ? "\nelse\n" + Else.toString() : "");
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		Visitable.Visit(Condition, parentMap);
		Visitable.Visit(If, parentMap);
		Visitable.Visit(Else, parentMap);
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}

}
