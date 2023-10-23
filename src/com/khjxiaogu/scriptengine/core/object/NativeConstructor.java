package com.khjxiaogu.scriptengine.core.object;

import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

@FunctionalInterface
public interface NativeConstructor<T> {
	public T call(KEnvironment objthis, KVariant... arg)throws KSException;
}
class NativeConstructorClosure<T> extends Closure implements CallableFunction {
	NativeConstructor<T> functhis;

	public NativeConstructorClosure(NativeConstructor<T> functhis) {
		super(null);
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
	public KVariant FuncCall(KVariant[] args, KObject objthis) throws KSException {
		if (args != null) {
			args = Arrays.copyOf(args, args.length);
		}
		objthis.putNativeInstance(functhis.call(objthis, args));
		return null;
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