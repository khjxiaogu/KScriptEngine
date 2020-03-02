package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;

public class IfStatement implements CodeNode, ASTParser {
	private CodeNode Condition;
	private CodeNode If;
	private CodeNode Else;

	public IfStatement() {
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		return null;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return null;
	}

}
