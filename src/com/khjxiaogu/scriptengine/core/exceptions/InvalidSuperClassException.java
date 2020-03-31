package com.khjxiaogu.scriptengine.core.exceptions;

public class InvalidSuperClassException extends ScriptException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public InvalidSuperClassException() {
		super("无法定位超类");
	}

}
