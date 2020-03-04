package com.khjxiaogu.scriptengine.core.syntax.operator.p02;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:Exchange.java
 */
public class Exchange extends DoubleOperator {

	/**
	 * 
	 */
	public Exchange() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		KVariant left = super.left.eval(env);
		KVariant right = super.right.eval(env);
		KVariant temp = new KVariant(left);
		left.setValue(right);
		right.setValue(temp);
		return new KVariant();
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "<->";
	}
}
