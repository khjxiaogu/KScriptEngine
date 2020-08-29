/*
 * file: ConversionException.java
 * @author khjxiaogu
 * time: 2020年3月30日
 */
package com.khjxiaogu.scriptengine.core.typeconvert;

import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;

// TODO: Auto-generated Javadoc
/**
 * Class ConversionException.
 * 类型转换异常
 *
 * @author: khjxiaogu
 *          file: ConversionException.java
 *          time: 2020年3月30日
 */
public class ConversionException extends ScriptException {

	/**
	 * Constant serialVersionUID.<br />
	 * 常量 serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private static final String format = "无法从(%s)转换为(%s)";
	private static final String format2 = "无法转换为(%s)";

	/**
	 * Instantiates a new conversion exception.<br />
	 * 产生一个类型转换异常<br />
	 *
	 * @param fromType the from type<br />
	 * @param ToType   the to type<br />
	 */
	public ConversionException(TypeInfo fromType, TypeInfo ToType) {
		super(String.format(ConversionException.format, fromType.getName(), ToType.getName()));
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new conversion exception.<br />
	 * 产生一个类型转换异常<br />
	 *
	 * @param ToType the to type<br />
	 */
	public ConversionException(TypeInfo ToType) {
		super(String.format(ConversionException.format2, ToType.getName()));
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new conversion exception.<br />
	 * 产生一个类型转换异常<br />
	 *
	 * @param fromType the from type<br />
	 * @param ToType   the to type<br />
	 */
	public ConversionException(String fromType, String ToType) {
		super(String.format(ConversionException.format, fromType, ToType));
		// TODO Auto-generated constructor stub
	}

	public ConversionException(String ToType) {
		super(String.format(ConversionException.format2, ToType));
	}

	/**
	 * Set type.<br />
	 *
	 * @param fromType the from type<br />
	 * @param ToType   the to type<br />
	 */
	public void setType(String fromType, String ToType) {
		detail = String.format(ConversionException.format, fromType, ToType);
	}
}
