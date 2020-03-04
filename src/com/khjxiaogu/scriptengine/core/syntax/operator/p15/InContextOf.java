package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorInContextOf.java x incontextof x
 */
public class InContextOf extends DoubleOperator {

	public InContextOf() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval((KObject) super.right.eval(env).asType("Object"));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "incontextof";
	}
}
