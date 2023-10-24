  package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

/**
 * @author khjxiaogu
 * @time 2019年8月23日 file:KObject.java
 */
public interface KObject extends KEnvironment {

	public boolean isInstanceOf(String str) throws KSException;

	public boolean isValid() throws KSException;

	public boolean invalidate() throws KSException;

	public KObject newInstance() throws KSException;
	public void callConstructor(KVariant[] args,KObject objthis) throws KSException;
}
