package com.khjxiaogu.scriptengine.core.object.internal;

import com.khjxiaogu.scriptengine.KhjScriptEngine;
import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.JVMError;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.NativeClassClosure;

public class ObjectScripts extends NativeClassClosure<Object> {

	public ObjectScripts(KhjScriptEngine engine) {
		super(Object.class,"Scripts");
		super.registerFunction("getObjectKeys",(o,args)->{
			KObject ko=args[0].asObject();
			KObject arr=ObjectArray.createArray();
			
			ko.EnumMembers(new Enumerator() {
				int i=0;
				@Override
				public boolean execute(KVariant k,int flag, KVariant v) throws KSException {
					arr.setMemberByNum(i++,k,KEnvironment.DEFAULT);
					return true;
				}
				
			},KEnvironment.IGNOREPROP);
			return KVariant.valueOf(arr);
		});
		super.registerFunction("hasNativeInstance",(o,args)->{
			KObject ko=args[0].asObject();
			try {
				return KVariant.valueOf(ko.getNativeInstance(Class.forName(args[1].toString()))!=null);
			} catch (ClassNotFoundException e) {
				throw new JVMError(e);
			}
		});
		super.registerFunction("eval",(o,args)->{
			return engine.eval(args[0].toString());
		});
	}

}
