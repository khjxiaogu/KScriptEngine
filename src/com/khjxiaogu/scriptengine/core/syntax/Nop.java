package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
//no operation
public class Nop implements CodeNode {

	public Nop() {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return null;
	}

}
