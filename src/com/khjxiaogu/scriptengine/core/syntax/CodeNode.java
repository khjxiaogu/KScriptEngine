package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public interface CodeNode extends Cloneable {
	public KVariant eval(KEnvironment env) throws KSException;
}
