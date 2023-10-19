package com.khjxiaogu.scriptengine.core.syntax.operator.p09;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:Equal.java
 */
public class Equals extends DoubleOperator {

	/**
	 *
	 */
	public Equals() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return KVariant.valueOf(super.left.eval(env).equals(super.right.eval(env)));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "==";
	}

}
