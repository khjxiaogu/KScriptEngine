package com.khjxiaogu.scriptengine.core.syntax.operator.p13;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:FloorDivide.java x\x
 */
public class FloorDivide extends DoubleOperator {

	/**
	 * 
	 */
	public FloorDivide() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return super.left.eval(env).floorDivide(super.right.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 13;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "\\";
	}
}
