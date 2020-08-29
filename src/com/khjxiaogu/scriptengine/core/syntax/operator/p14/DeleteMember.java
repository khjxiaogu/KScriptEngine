package com.khjxiaogu.scriptengine.core.syntax.operator.p14;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperationDeleteMember.java delete x
 */
public class DeleteMember extends SingleOperator {

	/**
	 *
	 */
	public DeleteMember() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (super.Child instanceof MemberOperator) {
			KVariant point = ((MemberOperator) super.Child).getPointing(env);
			KEnvironment obj = ((MemberOperator) super.Child).getObject(env);
			if (point.getType().getType() == Long.class) {
				return new KVariant(obj.deleteMemberByNum(point.getInt()));
				
			} else {
				return new KVariant(obj.deleteMemberByName(point.toString()));
			}
		}
		throw new MemberNotFoundException(super.Child.toString());
		// CodeNode parent=
		// while()
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
		return "(delete " + super.Child.toString() + ")";
	}
}
