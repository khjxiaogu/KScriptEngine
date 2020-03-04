package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorSelfIncrementRight.java x++
 */
public class SelfIncrementRight extends SingleOperator {

	public SelfIncrementRight() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.Child.eval(env).selfIncrement(Associative.RIGHT);
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public Associative getAssociative() {
		// TODO Auto-generated method stub
		return Associative.RIGHT;
	}

	@Override
	public String toString() {
		return "(" + super.Child.toString() + "++)";
	}
}
