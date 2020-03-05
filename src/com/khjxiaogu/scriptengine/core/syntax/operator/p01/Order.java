package com.khjxiaogu.scriptengine.core.syntax.operator.p01;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:Order.java x,x
 */
public class Order extends DoubleOperator {

	/**
	 *
	 */
	public Order() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		super.left.eval(env);
		return super.right.eval(env);
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return ",";
	}

}
