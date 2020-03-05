package com.khjxiaogu.scriptengine.core.syntax;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:CodeBlockAttribute.java
 */
public enum CodeBlockAttribute {
	/**
	 * for function use
	 */
	RETURNABLE,
	/**
	 * for classes
	 */
	OBJECT,
	/**
	 * for normal blocks
	 */
	NORMAL,
	/**
	 * for loop and switch
	 */
	BREAKABLE,
	/**
	 * for evaluation and execute
	 */
	STATEMENT
}
