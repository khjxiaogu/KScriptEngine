package com.khjxiaogu.scriptengine.core.exceptions;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;

public class CustomScriptException extends KSException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String getMessage() {
		if(KObject.class.isAssignableFrom(vars.getType().getType()))
			try {
				return vars.asType(KObject.class).getMemberByName("message",KEnvironment.DEFAULT, null).toString();
			} catch (KSException e) {
			}
		return vars.toString();
	}
	public KVariant getException() {
		return vars;
	}
	public void setException(KVariant vars) {
		this.vars = vars;
	}
	KVariant vars;
	public CustomScriptException(KVariant vars) {
		this.vars = vars;
	}


}
