package com.khjxiaogu.scriptengine.core.syntax.operator.p08;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:ByteAnd.java
 */
public class ByteAnd extends DoubleOperator {

	/**
	 *
	 */
	public ByteAnd() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).BAND(super.right.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "&";
	}
}
