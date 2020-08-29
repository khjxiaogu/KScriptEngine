package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.AccessDeniedException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public class NativeProperty<Type> implements KProperty {
	@FunctionalInterface
	public interface Getter<Type>{
		public KVariant get(Type obj) throws KSException;
	}
	@FunctionalInterface
	public interface Setter<Type>{
		public void set(Type obj,KVariant x) throws KSException;
	}
	protected Class<Type> superType;
	protected Getter<Type> getter;
	protected Setter<Type> setter;
	public NativeProperty(Class<Type> superType,Getter<Type> get,Setter<Type> set) {
		this.superType=superType;
		getter=get;
		setter=set;
	}

	@Override
	public void setProp(KVariant x, KEnvironment env) throws KSException {
		if(setter==null)
			throw new AccessDeniedException();
		if (superType != null)
			setter.set(env.getNativeInstance(superType),x);
		else
			setter.set(null,x);
	}

	@Override
	public KVariant getProp(KEnvironment env) throws KSException {
		if(getter==null)
			throw new AccessDeniedException();
		if (superType != null)
			if(env!=null)
				return getter.get(env.getNativeInstance(superType));
			else
				return getter.get(null);
		return getter.get(null);
	}

}

