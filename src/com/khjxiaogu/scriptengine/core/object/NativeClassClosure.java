package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.NativeProperty.Getter;
import com.khjxiaogu.scriptengine.core.object.NativeProperty.Setter;

public class NativeClassClosure<T> extends ExtendableClosure {
	Class<T> nativecls;
	public NativeClassClosure(Class<T> nativecls,String name) {
		super(name);
		this.nativecls=nativecls;
	}
	protected T getNativeInstance() throws KSException {
		return super.getNativeInstance(nativecls);
	}
	protected void registerFunction(String name,NativeMethod<T> func) {
		try {
			this.setMemberByName(name,KVariant.valueOf(new NativeFunction<T>(nativecls,func)),KEnvironment.THISONLY);
		} catch (KSException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	protected void registerConstructor(NativeConstructor<T> ctor) {
		try {
			this.setMemberByName(clsname,KVariant.valueOf(new NativeConstructorClosure<T>(ctor)), KEnvironment.THISONLY);
		} catch (KSException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	protected void registerProperty(String name,KProperty prop) {
		try {
			this.setMemberByName(name,KVariant.valueOf(new PropertyClosure(prop)), KEnvironment.IGNOREPROP|KEnvironment.THISONLY);
		} catch (KSException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	protected void registerProperty(String name,Getter<T> gtr,Setter<T> str) {
		try {
			this.setMemberByName(name,KVariant.valueOf(new PropertyClosure(new NativeProperty<T>(this.nativecls,gtr,str))), KEnvironment.IGNOREPROP|KEnvironment.THISONLY);
		} catch (KSException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
