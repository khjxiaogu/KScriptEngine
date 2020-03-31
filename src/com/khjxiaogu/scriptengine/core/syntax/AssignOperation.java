/*
 * file: AssignOperation.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core.syntax;

// TODO: Auto-generated Javadoc
/**
 * Enum AssignOperation.
 * a enum of assign operations.
 * 赋值操作的枚举
 *
 * @author khjxiaogu
 * @time 2020年3月2日
 *       project:khjScriptEngine
 */
public enum AssignOperation {

	/**
	 * +=
	 */
	ADD,
	/**
	 * >>>=
	 */
	ARSH,
	/**
	 * &=
	 */
	BAND,
	/**
	 * |=
	 */
	BOR,
	/**
	 * ^=
	 */
	BXOR,
	/**
	 * /=
	 */
	DIV,
	/**
	 * =
	 */
	EQ,
	/**
	 * \=
	 */
	FDIV,
	/**
	 * &&=
	 */
	LAND,
	/**
	 * ||=
	 */
	LOR,
	/**
	 * <<=
	 */
	LSH,
	/**
	 * -=
	 */
	MIN,
	/**
	 * %=
	 */
	MOD,
	/**
	 * *=
	 */
	MUL,
	/**
	 * >>=
	 */
	RSH;
}
