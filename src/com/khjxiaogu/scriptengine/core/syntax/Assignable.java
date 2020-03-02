package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.KVariant;

public interface Assignable {
	public KVariant assign(KEnvironment env, KVariant val) throws KSException;

	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException;

	public KObject getObject(KEnvironment env) throws KSException;

	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException;

	public KVariant getPointing(KEnvironment env) throws KSException;
}
