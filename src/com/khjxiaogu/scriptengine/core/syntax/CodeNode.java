/*
 * file: CodeNode.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;

/**
 * Interface CodeNode.
 *
 * @author: khjxiaogu
 *          file: CodeNode.java
 *          time: 2020年3月29日
 */
public interface CodeNode extends Cloneable {
	
	/**
	 * Eval in specific environment.<br />
	 *
	 * @param env envrionment for eval<br />执行上下文
	 * @return return result <br />
	 *         返回结果
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant eval(KEnvironment env) throws KSException;
}
