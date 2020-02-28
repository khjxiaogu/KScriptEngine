package com.khjxiaogu.scriptengine.core.syntax.operator.p00;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月21日
 * file:Break.java
 */
public class Break extends SingleOperator {

	/**
	 * 
	 */
	public Break() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		env.Break();
		return null;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Associative getAssociative() {
		// TODO Auto-generated method stub
		return Associative.LEFT;
	}

}
