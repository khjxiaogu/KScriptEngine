package com.khjxiaogu.scriptengine.core.syntax.operator.p14;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorNegative.java -x
 */
public class Negative extends SingleOperator {

	/**
	 *
	 */
	public Negative() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KVariant(-super.Child.eval(env).getNumber());
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 14;
	}

	@Override
	public Associative getAssociative() {
		// TODO Auto-generated method stub
		return Associative.LEFT;
	}

	@Override
	public String toString() {
		return "(-" + super.Child.toString() + ")";
	}
}
