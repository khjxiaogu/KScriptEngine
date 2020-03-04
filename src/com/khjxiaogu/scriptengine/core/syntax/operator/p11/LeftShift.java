package com.khjxiaogu.scriptengine.core.syntax.operator.p11;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月18日 file:LeftShift.java x<<x
 */
public class LeftShift extends DoubleOperator {

	/**
	 * 
	 */
	public LeftShift() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).LSH(super.right.eval(env).getInt());
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 11;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "<<";
	}
}
