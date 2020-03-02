package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.Object.MapEnvironment;

/**
 * @author khjxiaogu
 * @time 2020年2月20日 file:CodeBlockEnvironment.java
 */
public class CodeBlockEnvironment extends MapEnvironment {
	CodeBlock block;
	boolean stopped = false;
	boolean skipped = false;
	KVariant ret;

	/**
	 * @param parent
	 */
	public CodeBlockEnvironment(KEnvironment parent, CodeBlock block) {
		super(parent);
		this.block = block;
		// TODO Auto-generated constructor stub
	}

	public void Break() throws KSException {
		// TODO Auto-generated method stub
		stopped = true;
		skipped = true;
	}

	public void Return(KVariant val) throws KSException {
		// TODO Auto-generated method stub
		stopped = true;
		skipped = true;
		ret = val;

	}

	public void Continue() throws KSException {
		skipped = true;
	}
}
