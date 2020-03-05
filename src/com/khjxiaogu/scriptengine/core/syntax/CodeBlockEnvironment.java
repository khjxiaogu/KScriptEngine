package com.khjxiaogu.scriptengine.core.syntax;

import java.util.Arrays;
import java.util.Collection;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.Object.ArrayEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
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
		Arrays.fill(getSymbol(), "");
		this.block = block;
		this.attr=attr;
		// TODO Auto-generated constructor stub
	}
	public CodeBlockEnvironment(KEnvironment parent,int offset,int size ,CodeBlock block,CodeBlockAttribute attr,Collection<? extends String> symbols) {
		super(parent, offset, size);
		symbol=new String[size];
		symbol=symbols.toArray(getSymbol());
		this.block = block;
		this.attr=attr;
		// TODO Auto-generated constructor stub
	}
	public CodeBlockEnvironment(KEnvironment parent,int offset,int size ,CodeBlock block,CodeBlockAttribute attr,String[] symbols) {
		super(parent, offset, size);
		symbol=Arrays.copyOf(symbols,symbols.length);
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
	@Override
	public KVariant getMemberByName(String name) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(name);
		return parent.getMemberByName(name);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(var.toString());
		return parent.getMemberByVariant(var);
	}

	@Override
	public KVariant getMemberByNameEnsure(String name) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(name);
		return parent.getMemberByNameEnsure(name);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(name);
		return parent.setMemberByName(name, val);
	}
	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(var.toString());
		return parent.setMemberByVariant(var, val);
	}

	@Override
	public KVariant setMemberByNameEnsure(String name, KVariant val) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(name);
		return parent.setMemberByNameEnsure(name, val);
	}

	@Override
	public boolean hasMemberByName(String name) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(name);
		return parent.hasMemberByName(name);
	}	
	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(var.toString());
		return parent.hasMemberByVariant(var);
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(name);
		return parent.deleteMemberByName(name);
	}
	@Override
	public KVariant DoOperatonByName(AssignOperation op, String name, KVariant opr) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(name);
		return parent.DoOperatonByName(op, name, opr);
	}
	@Override
	public KVariant DoOperatonByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		if(parent == null)
			throw new MemberNotFoundException(var.toString());
		return parent.DoOperatonByVariant(op, var, opr);
	}
	public String[] getSymbol() {
		return symbol;
	}
}
