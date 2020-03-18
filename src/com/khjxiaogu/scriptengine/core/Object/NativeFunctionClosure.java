package com.khjxiaogu.scriptengine.core.Object;

import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public class NativeFunctionClosure<T> extends Closure implements CallableFunction {
	NativeFunction<T> functhis;
	Class<T> nativecls;

	public NativeFunctionClosure(Class<T> objtype,NativeFunction<T> functhis) {
		super(null);
		nativecls=objtype;
		this.functhis = functhis;
	}
	public NativeFunctionClosure(NativeFunction<T> functhis) {
		super(null);
		nativecls=null;
		this.functhis = functhis;
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
		if(nativecls!=null)
			return functhis.call(env.getNativeInstance(nativecls), args);
		else
			return functhis.call(null, args);
	}

}
