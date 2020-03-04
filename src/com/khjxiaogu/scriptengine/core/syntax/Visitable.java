package com.khjxiaogu.scriptengine.core.syntax;

import java.util.List;

/**
 * @author khjxiaogu
 * @time 2020年3月4日
 * project:khjScriptEngine
 */
public interface Visitable {
	public void Visit(List<String> parentMap);
	public static void Visit(Object o,List<String> parent) {
		if(o!=null&&o instanceof Visitable)
			((Visitable) o).Visit(parent);
		
	}
}
