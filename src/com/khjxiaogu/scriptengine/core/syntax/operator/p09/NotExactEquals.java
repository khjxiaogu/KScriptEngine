package com.khjxiaogu.scriptengine.core.syntax.operator.p09;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 * file:NotExactEquals.java
 */
public class NotExactEquals extends DoubleOperator {

	/**
	 * 
	 */
	public NotExactEquals() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KVariant(!super.left.eval(env).ExactEquals(super.right.eval(env)));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 9;
	}
	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "!==";
	}
}
