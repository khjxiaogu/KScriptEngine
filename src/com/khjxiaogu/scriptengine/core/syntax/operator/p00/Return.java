package com.khjxiaogu.scriptengine.core.syntax.operator.p00;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月21日 file:Return.java
 */
public class Return extends SingleOperator {

	/**
	 *
	 */
	public Return() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (env instanceof CodeBlockEnvironment) {
			((CodeBlockEnvironment) env).Return(super.Child.eval(env));
			return null;
		}
		throw new ScriptException("错误的return语句");
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public String toString() {
		return "return " + Child.toString();
	}

	@Override
	public Associative getAssociative() {
		// TODO Auto-generated method stub
		return Associative.LEFT;
	}

}
