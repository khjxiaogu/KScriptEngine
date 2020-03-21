package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public interface Assignable {
	public KVariant assign(KEnvironment env, KVariant val) throws KSException;

	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException;

}
