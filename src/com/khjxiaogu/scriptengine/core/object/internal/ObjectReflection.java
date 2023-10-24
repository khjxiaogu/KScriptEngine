package com.khjxiaogu.scriptengine.core.object.internal;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.JVMError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.NativeClassClosure;


public class ObjectReflection extends NativeClassClosure<Object> {

	public ObjectReflection() {
		super(Object.class,"Reflection");
		super.registerFunction("getClass",(o,args)->{
			try {
				return KVariant.valueOf(JavaClassWrapper.getWrapper(Class.forName(args[0].toString())));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				throw new JVMError(e);
			}
		});
		super.registerFunction("import",(o,args)->{
			try {
				String clsname=args[1].toString();
				JavaClassWrapper<?> jcw=JavaClassWrapper.getWrapper(Class.forName(clsname));
				KVariant kv=KVariant.valueOf(jcw);
				args[0].asObject().setMemberByName(clsname.substring(clsname.lastIndexOf(".")+1),kv,KEnvironment.DEFAULT);
				return kv;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				throw new JVMError(e);
			}
		});
	}

}
