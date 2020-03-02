package com.khjxiaogu.scriptengine.core.syntax.operator;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;

public interface MemberOperator extends Assignable {
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException;

	public KVariant getPointing(KEnvironment env) throws KSException;
}
