package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import java.util.List;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日
 * file:OperatorMember.java
 * x.x
 */
public class Member extends DoubleOperator implements MemberOperator {

	/**
	 * 
	 */
	public Member() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).asType(KObject.class).getMemberByName(((LiteralNode)super.right).getToken());
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public void getClassPath(KEnvironment env, List<String> dest) throws KSException {
		// TODO Auto-generated method stub
		if(super.left instanceof MemberOperator)
			((MemberOperator)super.left).getClassPath(env,dest);
		dest.add(((LiteralNode)super.right).getToken());
	}
	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return ".";
	}
}
