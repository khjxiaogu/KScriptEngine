package com.khjxiaogu.scriptengine.core.syntax.operator.p10;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月18日 file:LessThan.java
 */
public class LessThan extends DoubleOperator {

	/**
	 * 
	 */
	public LessThan() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).LT(super.right.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "<";
	}
}
