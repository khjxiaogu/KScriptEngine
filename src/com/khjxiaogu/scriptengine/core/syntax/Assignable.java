/*
 * file: Assignable.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;

// TODO: Auto-generated Javadoc
/**
 * Interface Assignable.
 *
 * @author: khjxiaogu
 *          file: Assignable.java
 *          time: 2020年3月29日
 */
public interface Assignable {
	
	/**
	 * Assign current node with value.<br />
	 * 给当前节点指向的值赋值
	 * @param env the environment<br />执行上下文
	 * @param val the value to assign<br />要进行的赋值
	 * @return return assigned value <br />
	 *         返回赋值后的值
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant assign(KEnvironment env, KVariant val) throws KSException;

	/**
	 * Do assign operation, such as +=.<br />
	 * 执行形同+=的赋值运算符
	 * @param env the environment<br />执行上下文
	 * @param val the value to assign<br />要进行的赋值
	 * @param op  the operation to execute<br />要执行的元素
	 * @return return assigned value <br />
	 *         返回赋值后的值
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException;

}
