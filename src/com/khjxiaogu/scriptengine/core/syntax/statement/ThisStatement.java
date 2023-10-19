package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;

/**
 * @author khjxiaogu
 * @time 2020年3月21日
 *       project:khjScriptEngine
 */
public class ThisStatement implements CodeNode, ObjectOperator {

	/**
	 *
	 */
	public ThisStatement() {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return KVariant.valueOf(env.getThis());
	}

	@Override
	public KEnvironment getObject(KEnvironment env) throws KSException {
		return env.getThis();
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
	}


	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
	}

}
