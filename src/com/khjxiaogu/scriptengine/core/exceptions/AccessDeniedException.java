package com.khjxiaogu.scriptengine.core.exceptions;

public class AccessDeniedException extends ScriptException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccessDeniedException() {
		super("写入了只读变量");
	}

}
