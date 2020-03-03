package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Exception.ScriptException;
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
	CodeBlockAttribute attr;
	/**
	 * @param parent
	 */
	public CodeBlockEnvironment(KEnvironment parent, CodeBlock block,CodeBlockAttribute attr) {
		super(parent);
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
