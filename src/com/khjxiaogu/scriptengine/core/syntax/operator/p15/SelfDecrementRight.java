package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.KVariantReference;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorSelfDecrementRight.java x--
 */
public class SelfDecrementRight extends SingleOperator {

	public SelfDecrementRight() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		KVariantReference v=((Assignable)super.Child).evalAsRef(env);
		return v.getValue().selfDecrement(Associative.RIGHT,v);
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
		return "(" + super.Child.toString() + "--)";
	}
}
