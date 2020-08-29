package com.khjxiaogu.scriptengine.core.exceptions;

public class KSException extends Exception {

	public KSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public KSException(String message, Throwable cause) {
		super(message, cause);
	}

	public KSException(String message) {
		super(message);
	}

	public KSException(Throwable cause) {
		super(cause);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public KSException() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		return "发生异常：" + super.getMessage();
	}

}
