package com.khjxiaogu.scriptengine.core.Object;

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
	public void setProp(KVariant x) {
		// TODO Auto-generated method stub
		value = x.getValue();
	}

	public void setProp(Object x) {
		// TODO Auto-generated method stub
		value = x;
	}

	@Override
	public KVariant getProp() {
		// TODO Auto-generated method stub
		return new KVariant(value);
	}
}
