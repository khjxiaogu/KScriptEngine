package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.block.Block;

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
