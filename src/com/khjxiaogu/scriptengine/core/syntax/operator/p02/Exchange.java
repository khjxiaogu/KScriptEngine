package com.khjxiaogu.scriptengine.core.syntax.operator.p02;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.KVariantReference;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:Exchange.java
 */
public class Exchange extends DoubleOperator {

	/**
	 *
	 */
	public Exchange() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		KVariantReference l=((Assignable)left).evalAsRef(env);
		KVariantReference r=((Assignable)right).evalAsRef(env);
		KVariant leftv = l.getValue();
		l.setValue(r.getValue());
		r.setValue(leftv);
		return KVariant.valueOf();
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return "<->";
	}
}
