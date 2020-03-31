/*
 * file: Converter.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core.typeconvert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.KVariant;

/**
 * Class Converter.
 * 目标类型转换器
 * 用于把任何其他类型都转换为目标类型
 *
 * @author: khjxiaogu
 *          file: Converter.java
 *          time: 2020年3月29日
 */
public class Converter {

	/**
	 * The converters.<br />
	 * 转换器.
	 */
	protected final Map<TypeInfo, TypeConverter<?, ?>> converters = new ConcurrentHashMap<>();

	/**
	 * The output type.<br />
	 * 输出类型.
	 */
	protected TypeInfo type;

	/**
	 * Instantiates a new converter with a type.<br />
	 * 使用一个类型新建一个转换器类<br />
	 *
	 * @param type the output type<br />
	 *             输出类型
	 */
	public Converter(TypeInfo type) {
		this.type = type;
	}

	/**
	 * Convert a variant's value to output type and output as a new variant.<br />
	 * 把一个变量的值转换为输出类型并输出新的变量
	 *
	 * @param in the input<br />
	 *           输入
	 * @return return conversion result <br />
	 *         返回转换结果
	 * @throws ConversionException if no such conversion.<br />
	 *                             如果没有这种转换
	 */
	public KVariant convert(KVariant in) throws ConversionException {
		TypeInfo t = in.getType();
		if (t.equals(type))
			return new KVariant(in);
		TypeConverter<?, ?> converter;
		if ((converter = converters.get(t)) != null)
			return new KVariant(converter.from(in), type);
		throw new ConversionException(t, type);
	}

	/**
	 * Convert a variant's value to output type and output value.<br />
	 * 把一个变量的值转换为输出类型并输出值
	 *
	 * @param in the input<br />
	 *           输入
	 * @return return result value<br />
	 *         返回转换结果值
	 * @throws ConversionException if no such conversion.<br />
	 *                             如果没有这种转换
	 */
	public Object from(KVariant in) throws ConversionException {
		TypeInfo t = in.getType();
		if (t.equals(type))
			return in.getValue();
		TypeConverter<?, ?> converter;
		if ((converter = converters.get(t)) != null)
			return converter.from(in.getValue());
		throw new ConversionException(t, type);
	}

	/**
	 * Gets the conversion of from type to output type, return null if not
	 * found<br />
	 * 获取由from type转换为输出类型的转换器，如果没有这种转换，返回null
	 *
	 * @param fromType the from type<br />
	 *                 输入类型
	 * @return converter of specific type<br />
	 *         对应类型的转换器
	 */
	public TypeConverter<?, ?> getConversion(TypeInfo fromType) {
		return converters.get(fromType);
	}

	/**
	 * ensure a conversion exists, throws error if not found.<br />
	 * 获取由from type转换为输出类型的转换器，如果没有这种转换，产生异常。
	 *
	 * @param fromType the from type<br />
	 * @return converter of specific type<br />
	 *         对应类型的转换器
	 * @throws ConversionException if no such conversion.<br />
	 *                             如果没有这种转换
	 */
	public TypeConverter<?, ?> getConversionEnsure(TypeInfo fromType) throws ConversionException {
		TypeConverter<?, ?> conv = converters.get(fromType);
		if (conv == null)
			throw new ConversionException(fromType, type);
		return conv;
	}

	/**
	 * Check if a conversion exist.<br />
	 * 检查某种类型转换器是否存在
	 *
	 * @param fromType the from type<br />
	 *                 输入类型
	 * @return true, if exists<br />
	 *         如果存在，返回true。
	 */
	public boolean hasConversion(TypeInfo fromType) {
		return converters.containsKey(fromType);
	}

	/**
	 * Gets the output type.<br />
	 * 获取输出类型.
	 *
	 * @return output type<br />
	 *         输出类型
	 */
	public Class<?> getOutType() {
		return type.getType();
	}

	/**
	 * Gets the output type info.<br />
	 * 获取输出类型信息.
	 *
	 * @return output type info<br />
	 *         输出类型信息
	 */
	public TypeInfo getOutTypeInfo() {
		return type;
	}

	/**
	 * Gets the name of output type.<br />
	 * 获取输出类型名.
	 *
	 * @return output type name<br />
	 *         输出类型名
	 */
	public String getOutTypeName() {
		return type.getName();
	}

	@Override
	public String toString() {
		return "Converter to " + type.getName();
	}

	/**
	 * Register conversion.<br />
	 *
	 * @param fromType  the from type<br />
	 *                  输入类型
	 * @param converter the converter<br />
	 *                  转换器
	 */
	public void registConvert(TypeInfo fromType, TypeConverter<?, ?> converter) {
		converters.put(fromType, converter);
	}
}
