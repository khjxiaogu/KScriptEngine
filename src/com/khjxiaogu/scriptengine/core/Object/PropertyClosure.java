package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;

public class PropertyClosure extends Closure implements KProperty {

	public PropertyClosure(KProperty prop, KEnvironment env) {
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
