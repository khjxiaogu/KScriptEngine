package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;

/**
 * @author khjxiaogu
 * @time 2020年3月4日
 *       project:khjScriptEngine
 */
public interface Visitable {
	public void Visit(VisitContext context) throws KSException;

	public static void Visit(Object o, VisitContext context) throws KSException {
		if (o != null && o instanceof Visitable) {
			((Visitable) o).Visit(context);
		}

	}
}
