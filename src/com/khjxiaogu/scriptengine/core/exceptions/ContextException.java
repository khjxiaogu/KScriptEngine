package com.khjxiaogu.scriptengine.core.exceptions;

public class ContextException extends ScriptException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public ContextException() {
		super("执行上下文错误");
	}
	public ContextException(String name) {
		super("执行上下文错误"+name);
	}
	public ContextException(Class<?> name) {
		super("执行上下文错误"+name.getSimpleName());
	}
}
