package com.khjxiaogu.scriptengine.core.exceptions;

/**
 * @author khjxiaogu
 * @time 2020年3月4日
 *       project:khjScriptEngine
 */
public class AssemblyException extends KSException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	String detail;

	public AssemblyException(String detail) {
		this.detail = detail;
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("发生汇编异常： ");
		sb.append(detail);
		return sb.toString();
	}
}
