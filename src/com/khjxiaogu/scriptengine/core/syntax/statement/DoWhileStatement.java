package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.Block;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;

public class DoWhileStatement implements Block {

	public DoWhileStatement() {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		return null;
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}

}
