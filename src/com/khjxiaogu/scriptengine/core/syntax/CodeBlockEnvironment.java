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
	private boolean stopped = false;
	private boolean skipped = false;
	private KVariant ret;
	CodeBlockAttribute attr;
	protected String[] symbol;
	/**
	 * @param parent
	 */
	public CodeBlockEnvironment(KEnvironment parent,int offset,int size ,CodeBlock block,CodeBlockAttribute attr) {
		super(parent, offset, size);
		symbol=new String[size];
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
		setStopped(true);
		setSkipped(true);
	}

	public void Return(KVariant val) throws KSException {
		// TODO Auto-generated method stub
		if(attr!=CodeBlockAttribute.RETURNABLE) {
			if(super.parent!=null&&super.parent instanceof CodeBlockEnvironment)
				((CodeBlockEnvironment)super.parent).Return(val);
			else
				throw new ScriptException("错误出现的break");
		}
		setStopped(true);
		setSkipped(true);
		setRet(val);

	}

	public void Continue() throws KSException {
		if(attr!=CodeBlockAttribute.BREAKABLE) {
			if(attr!=CodeBlockAttribute.RETURNABLE&&super.parent!=null&&super.parent instanceof CodeBlockEnvironment)
				((CodeBlockEnvironment)super.parent).Continue();
			else
				throw new ScriptException("错误出现的break");
		}
		setSkipped(true);
	}

	public boolean isSkipped() {
		return skipped;
	}

	public void setSkipped(boolean skipped) {
		this.skipped = skipped;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public KVariant getRet() {
		return ret;
	}

	public void setRet(KVariant ret) {
		this.ret = ret;
	}
}
