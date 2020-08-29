package com.khjxiaogu.scriptengine.core.exceptions;

public class JVMError extends KSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JVMError() {
		super();
	}

	public JVMError(Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super("底层错误："+cause.getClass().getSimpleName()+"("+cause.getMessage()+")", cause, enableSuppression, writableStackTrace);
	}

	public JVMError(Throwable cause) {
		super("底层错误："+cause.getClass().getSimpleName()+"("+cause.getMessage()+")", cause);
	}

	public JVMError(String message) {
		super(message);
	}
	
}
