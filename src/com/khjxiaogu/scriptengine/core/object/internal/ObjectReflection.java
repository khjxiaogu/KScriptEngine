package com.khjxiaogu.scriptengine.core.object.internal;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.JVMError;
import com.khjxiaogu.scriptengine.core.object.NativeClassClosure;


public class ObjectReflection extends NativeClassClosure<Object> {

	public ObjectReflection() {
		super(Object.class,"Reflection");
		super.registerFunction("getClass",(o,args)->{
			try {
				return new KVariant(JavaClassWrapper.getWrapper(Class.forName(args[0].toString())));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				throw new JVMError(e);
			}
		});
	}

}
