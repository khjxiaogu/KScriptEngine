package com.khjxiaogu.scriptengine.core.object.internal;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KExtendableObject;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.NativeClassClosure;

public class ObjectDictionary extends NativeClassClosure<Object>{
	private static ObjectDictionary objdic;
	public ObjectDictionary() {
		super(Object.class,"Dictionary");
		super.registerConstructor((env,arg)->{
			return new Object();
		});
		objdic=this;
	}
	public static KObject createDictionary() throws KSException {
		KExtendableObject sar=(KExtendableObject) objdic.newInstance();
		sar.callConstructor(null,sar);
		return sar;
	}
}
