package com.khjxiaogu.scriptengine.core.syntax.operator.p04;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:LogicalOr.java
 */
public class LogicalOr extends DoubleOperator {

	/**
	 *
	 */
	public LogicalOr() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KVariant(super.left.eval(env).asBoolean() || super.right.eval(env).asBoolean());
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "||";
	}
}
