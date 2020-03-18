package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;

public class FunctionClosure extends Closure implements CallableFunction {
	KObject objthis;
	CallableFunction functhis;

	public FunctionClosure(CallableFunction functhis,KObject objthis) {
		super(objthis);
		this.objthis = objthis;
		this.functhis = functhis;
	}

	@Override
	public KVariant FuncCall(KVariant[] args, KEnvironment env) throws KSException {
		return functhis.FuncCall(args, objthis);
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		return functhis.isInstanceOf(str);
	}

	@Override
	public boolean isValid() throws KSException {
		return functhis.isValid();
	}

	@Override
	public boolean invalidate() throws KSException {
		return functhis.invalidate();
	}

	@Override
	public KObject newInstance() throws KSException {
		return functhis.newInstance();
	}

	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KEnvironment objthis) throws KSException {
		throw new MemberNotFoundException("%"+num);
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KEnvironment objthis) throws KSException {
		if(name!=null) {
			throw new MemberNotFoundException(name);
		}
		return FuncCall(args,objthis);
	}




}
