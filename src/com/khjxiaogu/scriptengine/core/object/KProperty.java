package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public interface KProperty {
	public void setProp(KVariant x, KEnvironment env) throws KSException;

	public KVariant getProp(KEnvironment env) throws KSException;
}
