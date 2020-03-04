package com.khjxiaogu.scriptengine.core.syntax.operator.p14;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.Object.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
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
		if (super.Child instanceof LiteralNode) {
			if(((LiteralNode) super.Child).isLocal()) {
				return new KVariant(Integer.valueOf(env.deleteMemberByNum(((LiteralNode) super.Child).getLocalToken()) ? 1 : 0),
						"Integer");
			}else
			return new KVariant(Integer.valueOf(env.deleteMemberByName(((LiteralNode) super.Child).getToken()) ? 1 : 0),
					"Integer");
		} else if (super.Child instanceof MemberOperator) {
			List<String> path = new ArrayList<>();
			KVariant ret = new KVariant();
			path.clear();
			return ret;
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
