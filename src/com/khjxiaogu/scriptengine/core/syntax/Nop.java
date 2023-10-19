package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;

//no operation
public class Nop implements CodeNode {
	private static Nop instance=new Nop();
	public static Nop createNop() {
		return instance;
	}

	private Nop() {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return KVariant.valueOf();
	}

	@Override
	public String toString() {
		return "";
	}

}
