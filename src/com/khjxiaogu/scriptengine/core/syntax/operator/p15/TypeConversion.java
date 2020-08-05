package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;
import com.khjxiaogu.scriptengine.core.typeconvert.ConversionException;
import com.khjxiaogu.scriptengine.core.typeconvert.ConversionManager;
import com.khjxiaogu.scriptengine.core.typeconvert.Converter;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorTypeConvertion.java type(x) or (type)x
 */
public class TypeConversion extends SingleOperator {
	Converter converter;

	public TypeConversion(String Type) throws ConversionException {
		converter = ConversionManager.getConversion(Type);
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return converter.convert(super.Child.eval(env));
	}

	@Override
	public int getPriority() {
		return 15;
	}

	@Override
	public Associative getAssociative() {
		return Associative.LEFT;
	}

	@Override
	public String toString() {
		return "((" + converter.getOutTypeName() + ")" + super.Child.toString() + ")";
	}
}
