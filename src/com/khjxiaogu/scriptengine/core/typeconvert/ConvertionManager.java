package com.khjxiaogu.scriptengine.core.typeconvert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.KProperty;

public class ConvertionManager {
	private static Map<TypeInfo, Converter> convertionTable = new ConcurrentHashMap<>();

	private ConvertionManager() {
		// TODO Auto-generated constructor stub
	}

	static {
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
			throw new ConvertionException("Object", "Integer");
		});
		new TypeConverter<>(KObject.class, Double.class, (obj) -> {
			if (obj instanceof KProperty)
				return (Double) ((KProperty) obj).getProp(null).toType("Real");
			throw new ConvertionException("Object", "Real");
		});
		new TypeConverter<>(KObject.class, byte[].class, (obj) -> {
			if (obj instanceof KProperty)
				return (byte[]) ((KProperty) obj).getProp(null).toType("Octet");
			throw new ConvertionException("Object", "Octet");
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

	public static void registConvertion(TypeConverter<?, ?> typeConverter) {
		TypeInfo toType = typeConverter.getTo();
		Converter converter = ConvertionManager.convertionTable.get(toType);
		if (converter == null) {
			converter = new Converter(toType);
			ConvertionManager.convertionTable.put(toType, converter);
		}
		converter.registConvert(typeConverter.getFrom(), typeConverter);
	}

	public static TypeConverter<?, ?> getConvertion(TypeInfo fromType, TypeInfo toType) throws ConvertionException {
		Converter converter = ConvertionManager.convertionTable.get(toType);
		if (converter == null)
			throw new ConvertionException(fromType, toType);
		return converter.getConvertionEnsure(fromType);
	}

	public static TypeConverter<?, ?> getConvertion(String fromType, String toType) throws ConvertionException {
		Converter converter = ConvertionManager.convertionTable.get(TypeInfo.forName(toType));
		if (converter == null)
			throw new ConvertionException(fromType, toType);
		return converter.getConvertionEnsure(TypeInfo.forName(fromType));
	}

	public static Converter getConvertion(TypeInfo toType) throws ConvertionException {
		Converter converter = ConvertionManager.convertionTable.get(toType);
		if (converter == null)
			throw new ConvertionException(toType);
		return converter;
	}

	public static Converter getConvertion(String name) throws ConvertionException {
		TypeInfo toType = TypeInfo.forName(name);
		Converter converter = ConvertionManager.convertionTable.get(toType);
		if (converter == null)
			throw new ConvertionException(toType);
		return converter;
	}

	public static Converter getConvertion(Class<?> type) throws ConvertionException {
		TypeInfo toType = TypeInfo.forType(type);
		Converter converter = ConvertionManager.convertionTable.get(toType);
		if (converter == null)
			throw new ConvertionException(toType);
		return converter;
	}

	public static boolean canConvert(TypeInfo fromType, TypeInfo toType) {
		Converter converter = ConvertionManager.convertionTable.get(toType);
		if (converter == null)
			return false;
		return converter.hasConvertion(fromType);
	}
}
