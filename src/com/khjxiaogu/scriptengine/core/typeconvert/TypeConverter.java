package com.khjxiaogu.scriptengine.core.typeconvert;

public class TypeConverter<From, To> {
	TypeConvertionFunction<From, To> func;
	TypeInfo from;
	TypeInfo to;

	public TypeConverter(Class<From> from,Class<To> to,TypeConvertionFunction<From, To> func) {
		this.func = func;
		this.from = TypeInfo.forType(from);
		this.to = TypeInfo.forType(to);
		ConvertionManager.registConvertion(this);
	}

	@SuppressWarnings("unchecked")
	public Object from(Object in) throws ConvertionException {
		return func.Convert((From) in);
	}

	public TypeInfo getFrom() {
		return from;
	}

	public TypeInfo getTo() {
		return to;
	};
}
