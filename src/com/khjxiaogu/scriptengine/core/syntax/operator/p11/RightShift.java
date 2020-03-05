package com.khjxiaogu.scriptengine.core.syntax.operator.p11;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月17日 file:RightShift.java x>>x
 */
public class RightShift extends DoubleOperator {

	/**
	 *
	 */
	public RightShift() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).RSH(super.right.eval(env).getInt());
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 11;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return ">>";
	}
}
