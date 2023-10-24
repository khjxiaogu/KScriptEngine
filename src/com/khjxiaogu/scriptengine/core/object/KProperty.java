package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public interface KProperty {
	public void setProp(KVariant x, KObject env) throws KSException;

	public KVariant getProp(KObject env) throws KSException;
}
