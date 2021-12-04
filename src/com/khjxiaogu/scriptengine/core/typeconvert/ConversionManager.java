/*
 * file: ConversionManager.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core.typeconvert;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.ExtendableClosure;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.KProperty;
import com.khjxiaogu.scriptengine.core.object.internal.ObjectString;
import com.khjxiaogu.scriptengine.core.object.KOctet;

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
	private final static Map<Class<?>, Converter> conversionTable = new ConcurrentHashMap<>();

	/**
	 * Instantiates a new conversion manager.<br />
	 * 新建一个转换管理类<br />
	 */
	private ConversionManager() {
		// TODO Auto-generated constructor stub
	}
	private static ObjectString sobj=new ObjectString();
	static {// 注册转换器
		NumberFormat nf=NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(16);
		nf.setRoundingMode(RoundingMode.HALF_EVEN);
		new TypeConverter<>(Long.class, Double.class, obj -> (double) obj);
		new TypeConverter<>(Long.class, String.class, obj -> Long.toString(obj));
		new TypeConverter<>(Double.class, Long.class, obj ->(long) (double) obj);
		new TypeConverter<>(Double.class, String.class, obj -> nf.format(obj));
		new TypeConverter<>(Void.class, Long.class, obj -> 0L);
		new TypeConverter<>(Void.class, Double.class, obj -> 0D);
		new TypeConverter<>(Void.class, String.class, obj -> "");
		new TypeConverter<>(Void.class, KObject.class, obj ->null);
		new TypeConverter<>(Void.class, KOctet.class, obj ->KOctet.NullOctet);
		new TypeConverter<>(String.class, Long.class, obj -> {
			try {
				return (Long) Long.parseLong(obj);
			} catch (NumberFormatException e) {
				return 0L;
			}
		});
		new TypeConverter<>(String.class, Double.class, obj -> {
			try {
				return Double.parseDouble(obj);
			} catch (NumberFormatException e) {
				return 0D;
			}
		});
		new TypeConverter<>(String.class, KOctet.class, obj -> new KOctet(obj.getBytes()));
		new TypeConverter<>(String.class,KObject.class, obj ->{
			ExtendableClosure sar=(ExtendableClosure) sobj.newInstance();
			sar.putNativeInstance(String.class,obj);
			return sar;
		});

		new TypeConverter<>(KObject.class, String.class, obj -> {
			if (obj instanceof KProperty)
				return ((KProperty) obj).getProp(null).toType(String.class);
			return obj.toString();
		});
		
		new TypeConverter<>(KObject.class, Long.class, obj -> {
			if (obj instanceof KProperty)
				return ((KProperty) obj).getProp(null).toType(Long.class);
			throw new ConversionException("Object", "Integer");
		});
		new TypeConverter<>(KObject.class, Double.class, obj -> {
			if (obj instanceof KProperty)
				return ((KProperty) obj).getProp(null).toType(Double.class);
			throw new ConversionException("Object", "Real");
		});
		new TypeConverter<>(KObject.class, KOctet.class, obj -> {
			if (obj instanceof KProperty)
				return ((KProperty) obj).getProp(null).toType(KOctet.class);
			throw new ConversionException("Object", "Octet");
		});
		new TypeConverter<>(KOctet.class, String.class, obj -> {
			StringBuilder sb = new StringBuilder("<% ");
			for (byte element : obj.getBytes()) {
				if (element < 0x10) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(element));
				sb.append(" ");
			}
			sb.append("%>");
			return sb.toString();
		});
		new TypeConverter<>(KOctet.class,KObject.class, obj -> {
			
			return obj.getObject();
		});
		//backw
		new TypeConverter<>(Byte.class, Long.class,obj->obj.longValue());
		new TypeConverter<>(Short.class, Long.class,obj->obj.longValue());
		new TypeConverter<>(Integer.class, Long.class,obj->obj.longValue());
		new TypeConverter<>(Float.class,Double.class,obj->obj.doubleValue());
		new TypeConverter<>(Boolean.class,Long.class,obj->obj?1L:0L);
		//forw
		new TypeConverter<>(Long.class,Boolean.class,obj->obj!=0);
		new TypeConverter<>(Double.class,Boolean.class,obj->obj!=0);
		new TypeConverter<>(String.class,Boolean.class,obj->obj.length()>0);
		new TypeConverter<>(KOctet.class,Boolean.class,obj->obj.getBytes().length!=0);
		new TypeConverter<>(Void.class,Boolean.class,obj->false);
		
		new TypeConverter<>(Long.class,Integer.class,obj->obj.intValue());
		new TypeConverter<>(Double.class,Integer.class,obj->obj.intValue());
		new TypeConverter<>(String.class,Integer.class,obj->ConversionManager.getConversion(String.class,Long.class).from(obj).intValue()) ;
		new TypeConverter<>(Void.class,Integer.class,obj->0);
		
		new TypeConverter<>(Long.class,Byte.class,obj->obj.byteValue());
		new TypeConverter<>(Double.class,Byte.class,obj->obj.byteValue());
		new TypeConverter<>(String.class,Byte.class,obj->ConversionManager.getConversion(String.class,Long.class).from(obj).byteValue()) ;
		new TypeConverter<>(Void.class,Byte.class,obj->(byte)0);
		
		new TypeConverter<>(Long.class,Short.class,obj->obj.shortValue());
		new TypeConverter<>(Double.class,Short.class,obj->obj.shortValue());
		new TypeConverter<>(String.class,Short.class,obj->ConversionManager.getConversion(String.class,Long.class).from(obj).shortValue()) ;
		new TypeConverter<>(Void.class,Short.class,obj->(short)0);
		
		new TypeConverter<>(Long.class,Character.class,obj->(char)obj.intValue());
		new TypeConverter<>(Double.class,Character.class,obj->(char)obj.intValue());
		new TypeConverter<>(String.class,Character.class,obj->(char)ConversionManager.getConversion(String.class,Long.class).from(obj).shortValue()) ;
		new TypeConverter<>(Void.class,Character.class,obj->'\0');
		
		new TypeConverter<>(Long.class,Float.class,obj->obj.floatValue());
		new TypeConverter<>(Double.class,Float.class,obj->obj.floatValue());
		new TypeConverter<>(String.class,Float.class,obj->ConversionManager.getConversion(String.class,Long.class).from(obj).floatValue()) ;
		new TypeConverter<>(Void.class,Float.class,obj->0F);
		
	}

	/**
	 * Register conversion.<br />
	 * 注册转换方法
	 *
	 * @param typeConverter the type converter<br />
	 *                      转换器
	 */
	public static void registConversion(TypeConverter<?, ?> typeConverter) {
		Class<?> toType = typeConverter.getTo();
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
		Converter converter = ConversionManager.conversionTable.get(toType.getType());
		if (converter == null)
			throw new ConversionException(fromType, toType);
		return converter.getConversionEnsure(fromType.getType());
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
		Converter converter = ConversionManager.conversionTable.get(TypeInfo.forName(toType).getType());
		if (converter == null)
			throw new ConversionException(fromType, toType);
		return converter.getConversionEnsure(TypeInfo.forName(fromType).getType());
	}
	private final static TypeConverter<?,?> EqConvert=new TypeConverter<>(Object.class,Object.class,obj->obj);
	public static <From,To> TypeConverter<From,To> getConversion(Class<From> fromType,Class<To> toType) throws ConversionException {
		if(fromType.equals(toType))
			return (TypeConverter<From, To>) EqConvert;
		Converter converter = ConversionManager.conversionTable.get(toType);
		if (converter == null)
			throw new ConversionException(fromType.getSimpleName(), toType.getSimpleName());
		return (TypeConverter<From, To>) converter.getConversionEnsure(fromType);
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
		Converter converter = ConversionManager.conversionTable.get(toType.getType());
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
		Converter converter = ConversionManager.conversionTable.get(toType.getType());
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
		Converter converter = ConversionManager.conversionTable.get(type);
		if (converter == null)
			throw new ConversionException(type.getSimpleName());
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
		if(fromType==toType)return true;
		Converter converter = ConversionManager.conversionTable.get(toType.getType());
		if (converter == null)
			return false;
		return converter.hasConversion(fromType);
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
	public static boolean canConvert(Class<?> fromType,Class<?> toType) {
		if(toType.isAssignableFrom(fromType))return true;
		Converter converter = ConversionManager.conversionTable.get(toType);
		if (converter == null)
			return false;
		return converter.hasConversion(fromType);
	}
}
