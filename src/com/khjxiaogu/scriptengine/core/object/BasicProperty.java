package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:BasicProperty.java
 */
public class BasicProperty implements KProperty {
	Object value;

	/**
	 *
	 */
	public BasicProperty() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setProp(KVariant x, KEnvironment env) {
		// TODO Auto-generated method stub
		value = x.getValue();
	}

	public void setProp(Object x) {
		// TODO Auto-generated method stub
		value = x;
	}

	@Override
	public KVariant getProp(KEnvironment env) {
		// TODO Auto-generated method stub
		return new KVariant(value);
	}
}
