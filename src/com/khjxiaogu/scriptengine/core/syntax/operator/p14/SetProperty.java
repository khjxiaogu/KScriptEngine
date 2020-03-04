package com.khjxiaogu.scriptengine.core.syntax.operator.p14;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KProperty;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorSetProperty.java *x
 */
public class SetProperty extends SingleOperator {

	/**
	 * 
	 */
	public SetProperty() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		KVariant prop = super.Child.eval(env);
		prop.toType(KProperty.class);
		return prop;
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
		return "(*" + super.Child.toString() + ")!";
	}
}
