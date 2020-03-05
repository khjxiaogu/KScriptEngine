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

public class IfStatement implements Block {
	private CodeNode Condition;
	private CodeNode If;
	private CodeNode Else;

	public IfStatement() {
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser parser = new StatementParser();
		boolean iselse = false;
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
					throw new SyntaxError("错误的if表达式");
				c = reader.eat();
				if (iselse) {
					Else = new CodeBlock(CodeBlockAttribute.NORMAL).parse(reader);
					break;
				} else {
					if (If == null) {
						If = new CodeBlock(CodeBlockAttribute.NORMAL).parse(reader);
					} else {
						break;
					}
				}
				// c=reader.eat();
			} else if (c == 'e' && reader.reads(0, 4).equals("else")) {
				reader.eat();
				reader.eat();
				reader.eat();
				reader.eat();
				iselse = true;
			} else if (Condition != null) {
				parser.clear();
				if (iselse) {
					Else = parser.parseUntilOrBlock(reader, ';');
					break;
				} else if (If == null) {
					If = parser.parseUntilOrBlock(reader, ';');
				} else {
					break;
				}
			}
		}
		if (Condition == null || If == null)
			throw new SyntaxError("错误的if表达式");
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
	public void Visit(List<String> parentMap) {
		Visitable.Visit(Condition, parentMap);
		Visitable.Visit(If, parentMap);
		Visitable.Visit(Else, parentMap);
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}

}
