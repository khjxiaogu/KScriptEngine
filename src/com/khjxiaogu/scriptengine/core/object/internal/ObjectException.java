package com.khjxiaogu.scriptengine.core.object.internal;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.NativeClassClosure;
import com.khjxiaogu.scriptengine.core.object.NativeProperty;

public class ObjectException extends NativeClassClosure<KSException> {
	public static ObjectException instance;
	public ObjectException() {
		super(KSException.class,"Exception");
		instance=this;
		super.registerProperty("message",new NativeProperty<KSException>(
			KSException.class,
			(e)->{
				return KVariant.valueOf(e.getMessage());
			},null
		));
		super.registerConstructor((env,args)->{
			return new CustomException(args[0].toString());
		});
	}
	public static KObject getException(KSException os) {
		
		try {
			KObject ko=instance.newInstance();
			ko.putNativeInstance(os);
			return ko;
		} catch (KSException ignored) {
		}
		return null;
	}

}
class CustomException extends KSException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;
	public CustomException(String desc) {
		msg=desc;
	}
	@Override
	public String getMessage() {
		return msg;
	}
	
}
