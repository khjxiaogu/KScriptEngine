package com.khjxiaogu.scriptengine.core.exceptions;

import com.khjxiaogu.scriptengine.core.ParseReader;

public class AssignException extends ScriptException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssignException(String describe) {
		super(describe+"不能作为等号的左值");
	}

	public AssignException(String describe, String file, int lin, int col) {
		super(describe+"不能作为等号的左值", file, lin, col);
	}

	public AssignException(String describe, ParseReader r) {
		super(describe+"不能作为等号的左值", r);
	}

}
