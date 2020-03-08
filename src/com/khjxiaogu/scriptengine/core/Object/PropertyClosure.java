package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;

public class PropertyClosure extends Closure implements KProperty {

	public PropertyClosure(KProperty prop,KEnvironment env) {
		super(env);
	}

	@Override
	public void setProp(KVariant x, KEnvironment env) {
	}

	@Override
	public KVariant getProp(KEnvironment env) {
		return null;
	}

}
