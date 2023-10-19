package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.Parser;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:OperatorEvalString.java x!
 */
public class EvalString extends SingleOperator {

	/**
	 *
	 */
	public EvalString() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (env instanceof CodeBlockEnvironment)
			return Parser.getParser()
					.parse(super.Child.eval(env).asType(String.class), ((CodeBlockEnvironment) env).getSymbol())
					.eval(env);
		return Parser.getParser().parse(super.Child.eval(env).asType(String.class)).eval(env);
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public Associative getAssociative() {
		// TODO Auto-generated method stub
		return Associative.RIGHT;
	}

	@Override
	public String toString() {
		return "(" + super.Child.toString() + "!)";
	}

}
