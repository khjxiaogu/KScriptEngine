/*
 * file: ConversionManager.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core.typeconvert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.KProperty;

// TODO: Auto-generated Javadoc
/**
 * Class ConversionManager.
 *
 * @author: khjxiaogu
 *          file: ConversionManager.java
 *          time: 2020年3月29日
 */
public class ConversionManager {

	/**
	 * The conversion table.<br />
	 * 转换表.
	 */
	private final static Map<TypeInfo, Converter> conversionTable = new ConcurrentHashMap<>();

	/**
	 * Instantiates a new conversion manager.<br />
	 * 新建一个转换管理类<br />
	 */
	private ConversionManager() {
		// TODO Auto-generated constructor stub
	}

	static {// 注册转换器
		new TypeConverter<>(Integer.class, Double.class, (obj) -> {
			return (double) obj;
		});
		new TypeConverter<>(Integer.class, String.class, (obj) -> {
			return Integer.toString(obj);
		});
		new TypeConverter<>(Double.class, Integer.class, (obj) -> {
			return (int) (double) obj;
		});
		new TypeConverter<>(Double.class, String.class, (obj) -> {
			return Double.toString(obj);
		});
		new TypeConverter<>(Void.class, Integer.class, (obj) -> {
			return 0;
		});
		new TypeConverter<>(Void.class, Double.class, (obj) -> {
			return 0D;
		});
		new TypeConverter<>(Void.class, String.class, (obj) -> {
			return "";
		});
		new TypeConverter<>(Void.class, KObject.class, (obj) -> {
			return null;
		});
		new TypeConverter<>(Void.class, byte[].class, (obj) -> {
			return new byte[0];
		});
		new TypeConverter<>(String.class, Integer.class, (obj) -> {
			try {
				return (int) Double.parseDouble(obj);
			} catch (NumberFormatException e) {
				return 0;
			}
		});
		new TypeConverter<>(String.class, Double.class, (obj) -> {
			try {
				return Double.parseDouble(obj);
			} catch (NumberFormatException e) {
				return 0D;
			}
		});
		new TypeConverter<>(String.class, byte[].class, (obj) -> {
			return obj.getBytes();
		});
		new TypeConverter<>(KObject.class, String.class, (obj) -> {
			if (obj instanceof KProperty)
				return (String) ((KProperty) obj).getProp(null).toType("String");
			return obj.toString();
		});
		new TypeConverter<>(KObject.class, Integer.class, (obj) -> {
			if (obj instanceof KProperty)
				return (Integer) ((KProperty) obj).getProp(null).toType("Integer");
			throw new ConversionException("Object", "Integer");
		});
		new TypeConverter<>(KObject.class, Double.class, (obj) -> {
			if (obj instanceof KProperty)
				return (Double) ((KProperty) obj).getProp(null).toType("Real");
			throw new ConversionException("Object", "Real");
		});
		new TypeConverter<>(KObject.class, byte[].class, (obj) -> {
			if (obj instanceof KProperty)
				return (byte[]) ((KProperty) obj).getProp(null).toType("Octet");
			throw new ConversionException("Object", "Octet");
		});
		new TypeConverter<>(byte[].class, String.class, (obj) -> {
			StringBuilder sb = new StringBuilder("<% ");
			for (byte element : obj) {
				if (element < 0x10) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(element));
				sb.append(" ");
			}
			sb.append("%>");
			return sb.toString();
		});
	}

	/**
	 * Register conversion.<br />
	 * 注册转换方法
	 *
	 * @param typeConverter the type converter<br />
	 *                      转换器
	 */
	public static void registConversion(TypeConverter<?, ?> typeConverter) {
		TypeInfo toType = typeConverter.getTo();
		Converter converter = ConversionManager.conversionTable.get(toType);
		if (converter == null) {
			converter = new Converter(toType);
			ConversionManager.conversionTable.put(toType, converter);
		}
		converter.registConvert(typeConverter.getFrom(), typeConverter);
	}

	/**
	 * Gets the conversion by type info.<br />
	 * 根据类型信息获取对应输入输出的转换器.
	 *
	 * @param fromType from type<br />
	 *                 输入类型
	 * @param toType   to type<br />
	 *                 输出类型
	 * @return converter<br />
	 *         转换器
	 * @throws ConversionException if an conversion exception occurred.<br />
	 *                             如果转换异常发生了
	 */
	public static TypeConverter<?, ?> getConversion(TypeInfo fromType, TypeInfo toType) throws ConversionException {
		Converter converter = ConversionManager.conversionTable.get(toType);
		if (converter == null)
			throw new ConversionException(fromType, toType);
		return converter.getConversionEnsure(fromType);
	}

	/**
	 * Gets the conversion by name.<br />
	 * 根据类型名获取输入输出的转换器.
	 *
	 * @param fromType from type<br />
	 *                 输入类型
	 * @param toType   to type<br />
	 *                 输出类型
	 * @return converter<br />
	 *         转换器
	 * @throws ConversionException if an conversion exception occurred.<br />
	 *                             如果转换异常发生了
	 */
	public static TypeConverter<?, ?> getConversion(String fromType, String toType) throws ConversionException {
		Converter converter = ConversionManager.conversionTable.get(TypeInfo.forName(toType));
		if (converter == null)
			throw new ConversionException(fromType, toType);
		return converter.getConversionEnsure(TypeInfo.forName(fromType));
	}

	/**
	 * Gets the output conversion by output type info.<br />
	 * 根据输出类型信息获取对应的转换器
	 *
	 * @param toType to type<br />
	 *               输出类型
	 * @return conversion<br />
	 * @throws ConversionException if an conversion exception occurred.<br />
	 *                             如果转换异常发生了
	 */
	public static Converter getConversion(TypeInfo toType) throws ConversionException {
		Converter converter = ConversionManager.conversionTable.get(toType);
		if (converter == null)
			throw new ConversionException(toType);
		return converter;
	}

	/**
	 * Gets the output conversion by output type name.<br />
	 * 根据输出类型名获取对应的转换器
	 *
	 * @param toType to type<br />
	 *               输出类型
	 * @return conversion<br />
	 * @throws ConversionException if an conversion exception occurred.<br />
	 *                             如果转换异常发生了
	 */
	public static Converter getConversion(String name) throws ConversionException {
		TypeInfo toType = TypeInfo.forName(name);
		Converter converter = ConversionManager.conversionTable.get(toType);
		if (converter == null)
			throw new ConversionException(toType);
		return converter;
	}

	/**
	 * Gets the conversion by output type.<br />
	 * 根据输出类型获取对应的转换器
	 *
	 * @param toType to type<br />
	 *               输出类型
	 * @return conversion<br />
	 * @throws ConversionException if an conversion exception occurred.<br />
	 *                             如果转换异常发生了
	 */
	public static Converter getConversion(Class<?> type) throws ConversionException {
		TypeInfo toType = TypeInfo.forType(type);
		Converter converter = ConversionManager.conversionTable.get(toType);
		if (converter == null)
			throw new ConversionException(toType);
		return converter;
	}

	/**
	 * Check if from type can be convert to to type.<br />
	 * 检查输入类型是否可以被转换为输出类型
	 *
	 * @param fromType from type<br />
	 *                 输入类型
	 * @param toType   to type<br />
	 *                 输出类型
	 * @return true, if conversion exists<br />
	 *         如果转换存在，返回true。
	 */
	public static boolean canConvert(TypeInfo fromType, TypeInfo toType) {
		Converter converter = ConversionManager.conversionTable.get(toType);
		if (converter == null)
			return false;
		return converter.hasConversion(fromType);
	}
}
