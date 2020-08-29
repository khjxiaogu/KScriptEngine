package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockAttribute;

public class WithStatement extends CodeBlock {
	private CodeNode cond;

	public WithStatement() {
		super(CodeBlockAttribute.NORMAL);
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		WithEnvironment cbenv = new WithEnvironment(env, off, siz, this, attr, symbol);
		cbenv.setWith((KObject) cond.eval(env).toType("Object"));
		int i = 0;
		if (nodes.size() == 0)
			return null;
		try {
			for (; i < nodes.size(); i++) {
				nodes.get(i).eval(cbenv);
				if (cbenv.isSkipped()) {
					break;
				}
			}
		} catch (ScriptException e) {
			e.filename = name;
			e.colume = 0;
			e.line = i + 1;
			throw e;
		}
		return null;
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		char c = reader.eatAllSpace();
		if (c == '(') {
			parser.clear();
			c = reader.eat();
			cond = parser.parseUntil(reader, ')');
			reader.eat();
			c = reader.eatAllSpace();
			if (c == '{') {
				c = reader.eat();
				super.parse(reader);
			} else {
				super.parseExp(reader);
			}
		} else
			throw new SyntaxError("错误的with语句");
		return this;
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}

}
