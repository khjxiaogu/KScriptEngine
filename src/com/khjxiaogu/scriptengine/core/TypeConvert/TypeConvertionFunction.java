package com.khjxiaogu.scriptengine.core.TypeConvert;

/**
 * @author khjxiaogu
 * @time 2020年2月16日
 * file:TypeConvertionFunction.java
 */
@FunctionalInterface
public interface TypeConvertionFunction<From,To>{
	public To Convert(From original) throws ConvertionException;
}
