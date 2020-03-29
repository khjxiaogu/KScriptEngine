package com.khjxiaogu.scriptengine.core.typeconvert;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:TypeConvertionFunction.java
 */
@FunctionalInterface
public interface TypeConversionFunction<From, To> {
	public To Convert(From original) throws ConversionException;
}
