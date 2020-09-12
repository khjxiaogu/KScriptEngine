package com.khjxiaogu.scriptengine.core.syntax.operator.p07;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:ByteXor.java
 */
public class ByteXor extends DoubleOperator {

	/**
	 *
	 */
	public ByteXor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KVariant(super.left.eval(env)).BXOR(super.right.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "^";
	}
}
