package com.khjxiaogu.scriptengine.core.syntax;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;

public class ConstantNode implements CodeNode,ObjectOperator{
	protected KVariant value;
	public ConstantNode(KVariant ref) {
		value=ref;
	}
	public ConstantNode() {
		value = KVariant.valueOf();
	}
	public KVariant getValue() {
		return value;
	}
	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return KVariant.valueOf(value);
	}
	@Override
	public String toString() {
		return value.toString();
	}
	@Override
	public void Visit(List<String> parentMap) throws KSException {
	}
	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return null;
	}
	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
	}
	@Override
	public KEnvironment getObject(KEnvironment env) throws KSException {
		return value.asType(KObject.class);
	}
}
