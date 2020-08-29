package com.khjxiaogu.scriptengine.core.syntax.operator.p14;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.object.BasicProperty;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorGetPropertyInstance.java
 */
public class GetPropertyInstance extends SingleOperator {

	/**
	 *
	 */
	public GetPropertyInstance() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (super.Child instanceof ObjectOperator) {
			KVariant point = ((ObjectOperator) super.Child).getPointing(env);
			KEnvironment obj = ((ObjectOperator) super.Child).getObject(env);
			if (point.getType().getType() == Long.class) {
				return new KVariant(obj.getMemberByNum(point.getInt(),KEnvironment.IGNOREPROP));
			} else {
				return new KVariant(obj.getMemberByName(point.toString(),KEnvironment.IGNOREPROP));
			}
		}
		throw new MemberNotFoundException(super.Child.toString());
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
		return "(&" + super.Child.toString() + ")";
	}
}
