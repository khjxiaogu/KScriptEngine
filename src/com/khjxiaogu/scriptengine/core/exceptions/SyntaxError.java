package com.khjxiaogu.scriptengine.core.exceptions;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:SyntaxError.java
 */
public class SyntaxError extends ScriptException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param describe
	 */
	public SyntaxError(String describe) {
		super("语法错误：" + describe);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param describe
	 * @param file
	 * @param lin
	 * @param col
	 */
	public SyntaxError(String describe, String file, int lin, int col) {
		super("语法错误：" + describe, file, lin, col);
		// TODO Auto-generated constructor stub
	}

	public SyntaxError() {
		super("语法错误");
	}

}
