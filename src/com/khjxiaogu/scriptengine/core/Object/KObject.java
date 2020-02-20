package com.khjxiaogu.scriptengine.core.Object;

/**
 * @author khjxiaogu
 * @time 2019年8月23日
 * file:KObject.java
 */
public interface KObject extends KEnvironment{

	public boolean isInstanceOf(String str);
	public boolean isValid();
	public boolean invalidate();
}
