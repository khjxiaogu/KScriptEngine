package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.typeconvert.ConversionException;

public class FunctionClosure extends Closure implements CallableFunction {
	protected KObject objthis;
	protected CallableFunction functhis;

	public FunctionClosure(KObject functhis, KObject objthis) throws ConversionException {
		super(objthis);
		if (!(functhis instanceof CallableFunction))
			throw new ConversionException("Object", "Function");

		this.objthis = objthis;
		while (functhis instanceof FunctionClosure) {
			functhis = ((FunctionClosure) functhis).functhis;// unwrap all closures
		}
		this.functhis = (CallableFunction) functhis;
	}

	@Override
	public KVariant FuncCall(KVariant[] args, KEnvironment env) throws KSException {
		return functhis.FuncCall(args, objthis);
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
		throw new ScriptException("无法对函数对象使用new");
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KEnvironment objthis, int flag) throws KSException {
		if (name != null)
			throw new MemberNotFoundException(name);
		return FuncCall(args, objthis);
	}

	@Override
	public String getInstanceName() {
		return "Function";
	}

}
