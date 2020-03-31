/*
 * file: TypeConverter.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core.typeconvert;

// TODO: Auto-generated Javadoc
/**
 * Class TypeConverter.
 * a type converter to convert input into output with a function.
 * 通过一个转换函数来把输入转换为输出的转换器
 *
 * @param <From> from type<br />
 *               输入类型
 * @param <To>   to type<br />
 *               输出类型
 * @author: khjxiaogu
 *          file: TypeConverter.java
 *          time: 2020年3月29日
 */
public class TypeConverter<From, To> {

	/**
	 * The conversion function.<br />
	 * 转换函数.
	 */
	protected TypeConversionFunction<From, To> func;

	/**
	 * from type.<br />
	 * 输入类型.
	 */
	protected TypeInfo from;

	/**
	 * to type.<br />
	 * 输出类型.
	 */
	protected TypeInfo to;

	/**
	 * Instantiates a new type converter with a input type,output type and a
	 * conversion function.<br />
	 * 使用输入类型、输出类型和转换函数来新建一个类型转换器<br />
	 *
	 * @param from from type<br />
	 *             输入类型
	 * @param to   to type<br />
	 *             输出类型
	 * @param func conversion function<br />
	 *             转换函数
	 */
	public TypeConverter(Class<From> from, Class<To> to, TypeConversionFunction<From, To> func) {
		this.func = func;
		this.from = TypeInfo.forType(from);
		this.to = TypeInfo.forType(to);
		ConversionManager.registConversion(this);// auto register
	}

	/**
	 * Convert input into output.<br />
	 * 转换输入值到输出
	 *
	 * @param in the in<br />
	 *           输入类型
	 * @return return convert result <br />
	 *         返回转换结果
	 * @throws ConversionException if an conversion exception occurred.<br />
	 *                             如果conversion exception发生了
	 */
	@SuppressWarnings("unchecked")
	public Object from(Object in) throws ConversionException {
		return func.Convert((From) in);
	}

	/**
	 * Gets the from type.<br />
	 * 获取输入类型.
	 *
	 * @return from type<br />
	 *         输入类型
	 */
	public TypeInfo getFrom() {
		return from;
	}

	/**
	 * Gets the to type.<br />
	 * 获取输出类型.
	 *
	 * @return to type<br />
	 *         输出类型
	 */
	public TypeInfo getTo() {
		return to;
	}
}
