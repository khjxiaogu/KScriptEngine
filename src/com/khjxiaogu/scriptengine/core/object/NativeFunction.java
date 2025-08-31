package com.khjxiaogu.scriptengine.core.object;

import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;

public class NativeFunction<T> extends KAbstractObject implements CallableFunction {
	NativeMethod<T> functhis;
	Class<T> nativecls;

	public NativeFunction(Class<T> objtype, NativeMethod<T> functhis) {
		super();
		nativecls = objtype;
		this.functhis = functhis;
	}

	public NativeFunction(NativeMethod<T> functhis) {
		super();
		nativecls = null;
		this.functhis = functhis;
	}


	@Override
	public boolean isValid() {
		return functhis != null;
	}

	@Override
	public boolean invalidate() {
		if (functhis != null) {
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
		if (nativecls != null)
			return functhis.call(objthis.getNativeInstance(nativecls), args);
		return functhis.call(null, args);
	}
	@Override
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException {
		if(name==null)
			return KVariant.valueOf(new FunctionClosure(this,objthis));
		throw new MemberNotFoundException(name);
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		if(name==null)return true;
		return super.hasMemberByName(name, flag);
	}

	@Override
	public String getInstanceName() {
		return "Function";
	}
}
