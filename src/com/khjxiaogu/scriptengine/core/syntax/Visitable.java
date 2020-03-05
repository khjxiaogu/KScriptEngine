package com.khjxiaogu.scriptengine.core.syntax;

import java.util.List;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;

/**
 * @author khjxiaogu
 * @time 2020年3月4日
 *       project:khjScriptEngine
 */
public interface Visitable {
	public void Visit(List<String> parentMap) throws KSException;

	public static void Visit(Object o, List<String> parent) throws KSException {
		if (o != null && o instanceof Visitable) {
			((Visitable) o).Visit(parent);
		}

	}
}
