package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;

public class ConstantNode implements CodeNode {
	protected final KVariant value = new KVariant();
	public ConstantNode(KVariant ref) {
		value.setValue(ref);
	}
	public ConstantNode() {
	}
	public KVariant getValue() {
		return value;
	}
	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return value;
	}
	@Override
	public String toString() {
		return value.toString();
	}
}
