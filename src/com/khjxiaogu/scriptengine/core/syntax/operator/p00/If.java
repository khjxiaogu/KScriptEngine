package com.khjxiaogu.scriptengine.core.syntax.operator.p00;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:If.java
 */
public class If extends DoubleOperator {

	/**
	 * 
	 */
	public If() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (super.right.eval(env).asBoolean()) {
			super.left.eval(env);
		}
		return new KVariant();
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Associative getAssociative() {
		return Associative.LEFT;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "if";
	}
}
