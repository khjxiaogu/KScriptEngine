/*
 * file: CallableFunction.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

// TODO: Auto-generated Javadoc
/**
 * Interface CallableFunction.
 * interface for a function that can be called.
 * 可以被调用的函数对象接口
 *
 * @author: khjxiaogu
 *          file: CallableFunction.java
 *          time: 2020年3月29日
 */
public interface CallableFunction extends KObject {

	/**
	 * call function.<br />
	 * 执行函数
	 *
	 * @param args the arguments<br />
	 *             参数
	 * @param env  the override environment,null to execute in default
	 *             environment<br />
	 *             覆写的环境，填入null以默认上下文执行
	 * @return return result<br />
	 *         返回结果
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant FuncCall(KVariant[] args, KEnvironment env) throws KSException;
}
