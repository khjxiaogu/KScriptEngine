package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public interface CallableFunction extends KObject {
	public KVariant FuncCall(KVariant[] args, KEnvironment env) throws KSException;
}
