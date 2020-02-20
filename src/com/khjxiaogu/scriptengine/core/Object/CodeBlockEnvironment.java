package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlock;

/**
 * @author khjxiaogu
 * @time 2020年2月20日
 * file:CodeBlockEnvironment.java
 */
public class CodeBlockEnvironment extends MapEnvironment {
	CodeBlock block;
	/**
	 * @param parent
	 */
	public CodeBlockEnvironment(KEnvironment parent,CodeBlock block) {
		super(parent);
		this.block=block;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void Break() throws KSException {
		// TODO Auto-generated method stub
		block.Break();
	}

	@Override
	public void Return(KVariant val) throws KSException {
		// TODO Auto-generated method stub
		block.Return(val);
		
	}
}
