package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;

@FunctionalInterface
public interface NativeFunction<T> {
	public KVariant call(T objthis, KVariant... arg);
}
