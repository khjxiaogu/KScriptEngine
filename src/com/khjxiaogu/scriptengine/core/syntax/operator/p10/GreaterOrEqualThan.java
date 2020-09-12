package com.khjxiaogu.scriptengine.core.syntax.operator.p10;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:GreaterOrEqualThan.java
 */
public class GreaterOrEqualThan extends DoubleOperator {

	/**
	 *
	 */
	public GreaterOrEqualThan() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KVariant(super.left.eval(env)).GOET(super.right.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return ">=";
	}
}
