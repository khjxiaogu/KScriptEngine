package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 * file:OperatorFactory.java
 */
public interface ASTParser {
	public CodeNode parse(ParseReader reader) throws KSException;
}
