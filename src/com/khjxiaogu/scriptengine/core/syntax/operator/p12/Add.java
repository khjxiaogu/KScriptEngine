package com.khjxiaogu.scriptengine.core.syntax.operator.p12;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日
 * file:Add.java
 */
public class Add extends DoubleOperator {
	
	/**
	 * 
	 */
	public Add() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).add(super.right.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 12;
	}
	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "+";
	}
}
