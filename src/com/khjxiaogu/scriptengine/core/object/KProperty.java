package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;

public interface KProperty {
	public void setProp(KVariant x, KEnvironment env);

	public KVariant getProp(KEnvironment env);
}
