package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

@FunctionalInterface
public interface NativeMethod<T> {
	public KVariant call(T objthis, KVariant... arg) throws KSException;
}
