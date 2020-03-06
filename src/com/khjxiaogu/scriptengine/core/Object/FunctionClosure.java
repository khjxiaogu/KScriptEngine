package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public class FunctionClosure extends Closure {
	KObject functhis;
	KObject objthis;
	public FunctionClosure(KObject objthis,KObject functhis) {
		super(objthis);
		this.objthis=objthis;
		this.functhis = functhis;
	}
	@Override
	public KVariant FuncCall(KVariant[] args, KEnvironment env) throws KSException {
		if(env==null)
			return functhis.FuncCall(args,objthis);
		else
			return functhis.FuncCall(args,env);
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


}
