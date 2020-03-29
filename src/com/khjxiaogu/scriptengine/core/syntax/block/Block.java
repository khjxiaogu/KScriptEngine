package com.khjxiaogu.scriptengine.core.syntax.block;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public interface Block extends CodeNode, ASTParser, Visitable {
	public void init(KEnvironment env) throws KSException;
}
