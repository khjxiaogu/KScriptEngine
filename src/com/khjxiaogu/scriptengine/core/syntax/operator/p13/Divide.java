package com.khjxiaogu.scriptengine.core.syntax.operator.p13;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

public class Divide extends DoubleOperator {

	public Divide() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KVariant(super.left.eval(env)).divide(super.left.eval(env));
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 13;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "/";
	}
}
