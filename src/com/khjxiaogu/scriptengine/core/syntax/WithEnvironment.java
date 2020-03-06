package com.khjxiaogu.scriptengine.core.syntax;

import java.util.Collection;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;

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
