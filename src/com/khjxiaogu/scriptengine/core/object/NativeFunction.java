package com.khjxiaogu.scriptengine.core.object;

import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public class NativeFunction<T> extends Closure implements CallableFunction {
	NativeMethod<T> functhis;
	Class<T> nativecls;

	public NativeFunction(Class<T> objtype, NativeMethod<T> functhis) {
		super(null);
		nativecls = objtype;
		this.functhis = functhis;
	}

	public NativeFunction(NativeMethod<T> functhis) {
		super(null);
		nativecls = null;
		this.functhis = functhis;
	}


	@Override
	public boolean isValid() {
		return super.closure == null;
	}

	@Override
	public boolean invalidate() {
		if (super.closure != null) {
			super.closure = null;
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
		if (nativecls != null)
			return functhis.call(env.getNativeInstance(nativecls), args);
		return functhis.call(null, args);
	}
	@Override
	public String toString() {
		return "(Function)"+super.getInstancePointer();
	}
	@Override
	public String getInstanceName() {
		return "Function";
	}
}
