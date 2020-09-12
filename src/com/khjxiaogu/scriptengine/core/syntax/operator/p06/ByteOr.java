package com.khjxiaogu.scriptengine.core.syntax.operator.p06;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:ByteOr.java
 */
public class ByteOr extends DoubleOperator {

	/**
	 *
	 */
	public ByteOr() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KVariant(super.left.eval(env)).BOR(super.right.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "|";
	}
}
