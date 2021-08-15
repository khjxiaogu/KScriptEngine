package com.khjxiaogu.scriptengine.core.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;

public class ScriptException extends KSException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public String detail = "未知异常";
	public List<String> trace=new ArrayList<>();
	public ScriptException(String describe) {
		detail = describe;
		// TODO Auto-generated constructor stub
	}

	public ScriptException(String describe, String file, int lin, int col) {
		detail = describe;
		fillTrace(file,lin,col);
		// TODO Auto-generated constructor stub
	}

	public ScriptException(String describe, ParseReader r) {
		detail = describe;
		fillTrace(r.getName(),r.getLine(),r.getCol());
		// TODO Auto-generated constructor stub
	}

	public ScriptException() {
		super();
		detail ="unknown error";
		
	}

	public ScriptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		detail =message;
	}

	public ScriptException(String message, Throwable cause) {
		super(message, cause);
		detail =message;
	}

	public ScriptException(Throwable cause) {
		super(cause);
		detail =cause.getClass().getSimpleName();
	}

	public void fillTrace(String file,int line,int col) {
		trace.add("at ("+file+") "+line+"行"+col+"列");
	}
	public void fillTrace(String file,int line,int col,String info) {
		if(info.length()>=20) {
			info=info.substring(0,20)+"...";
		}
		trace.add("at ("+file+") "+line+"行"+col+"列："+info);
	}
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("发生脚本异常：");
		sb.append(detail);
		sb.append("\nTrace：");
		for(String s:trace) {
			sb.append(s);
			sb.append("\n");
		}
		return sb.toString();
	}
}
