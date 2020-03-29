package com.khjxiaogu.scriptengine.core.syntax.operator.p00;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.block.Block;

public class Case implements CodeNode, ASTParser, Block {
	private CodeNode cond;

	public CodeNode getCond() {
		return cond;
	}

	public Case() {
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser parser = new StatementParser();
		cond = parser.parseUntil(reader, ':');
		reader.eat();
		return this;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		Visitable.Visit(cond, parentMap);
	}

	@Override
	public void init(KEnvironment env) throws KSException {

	}

	@Override
	public String toString() {
		return "case" + cond.toString() + ":";
	}

}
