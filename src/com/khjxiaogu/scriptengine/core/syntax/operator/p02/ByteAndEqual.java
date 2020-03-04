package com.khjxiaogu.scriptengine.core.syntax.operator.p02;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:AndEqual.java
 */
public class ByteAndEqual extends Equal {

	/**
	 * 
	 */
	public ByteAndEqual() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return ((Assignable) super.left).assignOperation(env, super.right.eval(env), AssignOperation.BAND);
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "&=";
	}
}
