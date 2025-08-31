package com.khjxiaogu.scriptengine.core.syntax.operator.p12;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月17日 file:Minus.java
 */
public class Minus extends DoubleOperator {

	/**
	 *
	 */
	public Minus() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).minus(super.right.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "-";
	}
}
