package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

/**
 * @author khjxiaogu
 * @time 2019年8月23日 file:KObject.java
 */
public interface KObject extends KEnvironment {
	public KVariant FuncCall(KVariant[] args, KEnvironment env) throws KSException;

	public boolean isInstanceOf(String str) throws KSException;

	public boolean isValid() throws KSException;

	public boolean invalidate() throws KSException;

	public KObject newInstance() throws KSException;
}
