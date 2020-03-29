package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

/**
 * Interface ASTParser.
 * Parsers that can read from readers and return a AST(Abstract Syntax Tree) node.
 * 可以从读取器读取并且返回一个抽象语法树节点的解析器。
 * @author khjxiaogu
 * @time 2020年2月19日 file:OperatorFactory.java
 */
public interface ASTParser {
	
	/**
	 * Parse from a reader.<br />
	 * 从一个读取器解析。
	 * @param reader reader<br />读取器
	 * @return return parsed code node <br />
	 *         返回解析过的代码节点
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public CodeNode parse(ParseReader reader) throws KSException;
}
