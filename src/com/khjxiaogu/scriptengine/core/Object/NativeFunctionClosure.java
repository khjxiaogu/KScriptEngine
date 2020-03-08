package com.khjxiaogu.scriptengine.core.Object;

import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public class NativeFunctionClosure extends Closure implements CallableFunction {
	NativeFunction<Object> functhis;

	public NativeFunctionClosure(KEnvironment env) {
		super(env);
	}

	@Override
	public boolean isInstanceOf(String str) {
		return str.equals("Function");
	}

	@Override
	public boolean isValid() {
		return super.Closure == null;
	}

	@Override
	public boolean invalidate() {
		if (super.Closure != null) {
			super.Closure = null;
			functhis = null;
			return true;
		}
		return false;
	}

	@Override
	public KObject newInstance() throws ContextException {
		throw new ContextException();
	}

	@Override
	public KVariant FuncCall(KVariant[] args, KEnvironment env) throws KSException {
		if (args != null) {
			args = Arrays.copyOf(args, args.length);
		}
		if (env != null && env instanceof NativeClosure)
			return functhis.call(((NativeClosure) env).getObjthis(), args);
		else
			return functhis.call(null, args);
	}
}
