package com.khjxiaogu.scriptengine.core.syntax.operator.p11;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月18日 file:AlgebraicRightShift.java x>>>zx
 */
public class AlgebraicRightShift extends DoubleOperator {

	/**
	 * 
	 */
	public AlgebraicRightShift() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).ARSH(super.right.eval(env).getInt());
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 11;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return ">>>";
	}
}
