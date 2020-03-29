package com.khjxiaogu.scriptengine.core.syntax.operator.p00;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.block.Block;

public class Default implements CodeNode, Block {

	public Default() {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
	}

	@Override
	public void init(KEnvironment env) throws KSException {

	}

	@Override
	public String toString() {
		return "default:";
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		return null;
	}

}
