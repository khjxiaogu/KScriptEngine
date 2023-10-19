package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;

public class SuperStatement implements ObjectOperator, CodeNode {

	public SuperStatement() {
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return KVariant.valueOf(env.getSuper());
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
	}

	@Override
	public KEnvironment getObject(KEnvironment env) throws KSException {
		return env.getSuper();
	}

}
