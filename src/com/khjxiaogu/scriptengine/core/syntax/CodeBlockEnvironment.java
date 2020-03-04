package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.Object.ArrayEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.Object.MapEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;

/**
 * @author khjxiaogu
 * @time 2020年2月20日 file:CodeBlockEnvironment.java
 */
public class CodeBlockEnvironment extends ArrayEnvironment {
	CodeBlock block;
	boolean stopped = false;
	boolean skipped = false;
	KVariant ret;
	CodeBlockAttribute attr;
	/**
	 * @param parent
	 */
	public CodeBlockEnvironment(KEnvironment parent,int offset,int size ,CodeBlock block,CodeBlockAttribute attr) {
		super(parent, offset, size);
		this.block = block;
		this.attr=attr;
		// TODO Auto-generated constructor stub
	}

	public void Break() throws KSException {
		// TODO Auto-generated method stub
		if(attr!=CodeBlockAttribute.BREAKABLE) {
			if(attr!=CodeBlockAttribute.RETURNABLE&&super.parent!=null&&super.parent instanceof CodeBlockEnvironment)
				((CodeBlockEnvironment)super.parent).Break();
			else
				throw new ScriptException("错误出现的break");
		}
		stopped = true;
		skipped = true;
	}

	public void Return(KVariant val) throws KSException {
		// TODO Auto-generated method stub
		if(attr!=CodeBlockAttribute.RETURNABLE) {
			if(super.parent!=null&&super.parent instanceof CodeBlockEnvironment)
				((CodeBlockEnvironment)super.parent).Return(val);
			else
				throw new ScriptException("错误出现的break");
		}
		stopped = true;
		skipped = true;
		ret = val;

	}

	public void Continue() throws KSException {
		if(attr!=CodeBlockAttribute.BREAKABLE) {
			if(attr!=CodeBlockAttribute.RETURNABLE&&super.parent!=null&&super.parent instanceof CodeBlockEnvironment)
				((CodeBlockEnvironment)super.parent).Continue();
			else
				throw new ScriptException("错误出现的break");
		}
		skipped = true;
	}
}
