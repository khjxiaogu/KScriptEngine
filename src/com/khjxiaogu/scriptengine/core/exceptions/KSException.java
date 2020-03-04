package com.khjxiaogu.scriptengine.core.exceptions;

public class KSException extends Exception {

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
