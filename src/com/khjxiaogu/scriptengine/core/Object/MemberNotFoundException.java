package com.khjxiaogu.scriptengine.core.Object;

import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:MemberNotFoundException.java
 */
public class MemberNotFoundException extends ScriptException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param describe
	 */
	public MemberNotFoundException(String describe) {
		super("无法找到成员" + describe);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param describe
	 * @param file
	 * @param lin
	 * @param col
	 */
	public MemberNotFoundException(String describe, String file, int lin, int col) {
		super("无法找到成员" + describe, file, lin, col);
		// TODO Auto-generated constructor stub
	}

}
