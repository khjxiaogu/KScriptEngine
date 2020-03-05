package com.khjxiaogu.scriptengine.core.exceptions;

import com.khjxiaogu.scriptengine.core.ParseReader;

public class ScriptException extends KSException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public int line;
	public int colume;
	public String filename;
	public String detail = "未知异常";

	public ScriptException(String describe) {
		detail = describe;
		// TODO Auto-generated constructor stub
	}

	public ScriptException(String describe, String file, int lin, int col) {
		detail = describe;
		filename = file;
		line = lin;
		colume = col;
		// TODO Auto-generated constructor stub
	}

	public ScriptException(String describe, ParseReader r) {
		detail = describe;
		filename = r.getName();
		line = r.getLine();
		colume = r.getCol();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		if (filename != null) {
			sb.append("位于 ");
			sb.append(filename);
			sb.append(" 的第");
			sb.append(line);
			sb.append("行第 ");
			sb.append(colume);
			sb.append("列");
		}
		sb.append("发生脚本异常：");
		sb.append(detail);
		return sb.toString();
	}
}
