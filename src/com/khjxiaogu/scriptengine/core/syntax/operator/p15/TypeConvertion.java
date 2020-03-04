package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;
import com.khjxiaogu.scriptengine.core.typeconvert.Converter;
import com.khjxiaogu.scriptengine.core.typeconvert.ConvertionException;
import com.khjxiaogu.scriptengine.core.typeconvert.ConvertionManager;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorTypeConvertion.java type(x) or (type)x
 */
public class TypeConvertion extends SingleOperator {
	Converter converter;

	public TypeConvertion(String Type) throws ConvertionException {
		converter = ConvertionManager.getConvertion(Type);
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
