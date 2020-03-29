package com.khjxiaogu.scriptengine.core.syntax.operator.p02;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:OrEqual.java
 */
public class ByteOrEqual extends Equal {

	/**
	 *
	 */
	public ByteOrEqual() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return ((Assignable) super.left).assignOperation(env, super.right.eval(env), AssignOperation.BOR);
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "|=";
	}
}
