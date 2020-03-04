package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public interface Block extends CodeNode, ASTParser,Visitable {
	public void init(KEnvironment env) throws KSException;
}
