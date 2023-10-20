package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;

public interface ObjectOperator {
	public KVariant getPointing(KEnvironment env) throws KSException;
	public KEnvironment getObject(KEnvironment env) throws KSException;

}
