package com.khjxiaogu.scriptengine.core.typeconvert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.Object.KVariant;

public class Converter {
	Map<TypeInfo, TypeConverter<?, ?>> converters = new ConcurrentHashMap<>();
	TypeInfo type;

	public Converter(TypeInfo type) {
		this.type = type;
	}

	public KVariant convert(KVariant in) throws ConvertionException {
		TypeInfo t = in.getType();
		if (t.equals(type))
			return new KVariant(in);
		TypeConverter<?, ?> converter;
		if ((converter = converters.get(t)) != null)
			return new KVariant(converter.from(in), type);
		throw new ConvertionException(t, type);
	}

	public Object from(KVariant in) throws ConvertionException {
		TypeInfo t = in.getType();
		if (t.equals(type))
			return in.getValue();
		TypeConverter<?, ?> converter;
		if ((converter = converters.get(t)) != null)
			return converter.from(in.getValue());
		throw new ConvertionException(t, type);
	}

	public TypeConverter<?, ?> getConvertion(TypeInfo fromType) {
		return converters.get(fromType);
	}

	public TypeConverter<?, ?> getConvertionEnsure(TypeInfo fromType) throws ConvertionException {
		TypeConverter<?, ?> conv = converters.get(fromType);
		if (conv == null)
			throw new ConvertionException(fromType, type);
		return conv;
	}

	public boolean hasConvertion(TypeInfo fromType) {
		return converters.containsKey(fromType);
	}

	public Class<?> getOutType() {
		return type.getType();
	}

	public TypeInfo getOutTypeInfo() {
		return type;
	}

	public String getOutTypeName() {
		return type.getName();
	}

	@Override
	public String toString() {
		return "Converter to " + type.getName();
	}

	public void registConvert(TypeInfo fromType, TypeConverter<?, ?> converter) {
		converters.put(fromType, converter);
	}
}
