package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.Collection;

import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockEnvironment;

public class WithEnvironment extends CodeBlockEnvironment {
	KObject with;

	public KObject getWith() {
		return with;
	}

	public void setWith(KObject with) {
		this.with = with;
	}

	public WithEnvironment(KEnvironment parent, int offset, int size, CodeBlock block, CodeBlockAttribute attr) {
		super(parent, offset, size, block, attr);
	}

	public WithEnvironment(KEnvironment parent, int offset, int size, CodeBlock block, CodeBlockAttribute attr,
			Collection<? extends String> symbols) {
		super(parent, offset, size, block, attr, symbols);
	}

	public WithEnvironment(KEnvironment parent, int offset, int size, CodeBlock block, CodeBlockAttribute attr,
			String[] symbols) {
		super(parent, offset, size, block, attr, symbols);
	}

}
