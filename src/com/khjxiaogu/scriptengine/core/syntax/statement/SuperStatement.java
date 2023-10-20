package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;
import com.khjxiaogu.scriptengine.core.syntax.VisitContext;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public class SuperStatement implements Visitable, CodeNode {

	public SuperStatement() {
	}

	@Override
	public void Visit(VisitContext parentMap) throws KSException {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return KVariant.valueOf(env.getSuper());
	}

}
