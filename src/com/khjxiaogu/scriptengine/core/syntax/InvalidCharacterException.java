package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.Exception.ScriptException;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 * file:InvalidCharacterException.java
 */
public class InvalidCharacterException extends ScriptException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param describe
	 */
	public InvalidCharacterException(char ch) {
		super("无效字符\\u"+Character.getNumericValue(ch));
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param describe
	 * @param file
	 * @param lin
	 * @param col
	 */
	public InvalidCharacterException(char ch, String file, int lin, int col) {
		super("无效字符\\u"+Character.getNumericValue(ch), file, lin, col);
		// TODO Auto-generated constructor stub
	}

}
