/*
 * file: KEnvironment.java
 * @author khjxiaogu
 * time: 2020年3月20日
 */
package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

// TODO: Auto-generated Javadoc
/**
 * Interface KEnvironment.
 * 环境接口
 * 
 * @author: khjxiaogu
 *          file: KEnvironment.java
 *          time: 2020年3月20日
 */
public interface KEnvironment {

	/**
	 * Functional Interface Enumerator.For EnumMembers method.<br />
	 * 函数式接口，用于作为EnumMembers方法的参数
	 *
	 * @author: khjxiaogu
	 *          file: KEnvironment.java
	 *          time: 2020年3月20日
	 */
	@FunctionalInterface
	public interface Enumerator {

		/**
		 * Execute.<br />
		 *
		 * @param k the k<br />
		 * @param v the v<br />
		 * @return true, if <br />
		 *         如果，返回true。
		 * @throws KSException Any Engine Exception occured<br />
		 *                     引擎执行发生的异常。
		 */
		boolean execute(KVariant k,int flag, KVariant v) throws KSException;
	}

	/**
	 * Constant DEFAULT.Means create member if not exist and search in global
	 * scope.<br />
	 * 常量 DEFAULT.表示确保成员存在并且会在全局搜索
	 */
	public final static int DEFAULT = 0x0;

	/**
	 * Constant THISONLY.Means don't search in global scope.<br />
	 * 常量 THISONLY.表示不在全局范围搜索。
	 */
	public final static int THISONLY = 0x1;

	/**
	 * Constant IGNOREPROP.Means don't trigger setter or getter of property.<br />
	 * 常量 IGNOREPROP.表示不触发属性的setter或者getter方法
	 */
	public final static int IGNOREPROP = 0x2;

	/**
	 * Constant MUSTEXIST.Means throw exception if not exist.<br />
	 * 常量 MUSTEXIST.表示当成员不存在时抛出异常
	 */
	public final static int MUSTEXIST = 0x4;
	public final static int VAR_DATA_MASK=0x0000FFFF;
	public final static int STATIC_MEMBER=0x00010000;
	public final static int VAR_MODI_MASK=0xFFFF0000;
	/**
	 * Delete member by name.<br />
	 * 根据名字删除成员
	 *
	 * @param name the name of member<br />
	 * @return true, if delete succeed<br />
	 *         如果删除成功，返回true。
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public boolean deleteMemberByName(String name) throws KSException;

	/**
	 * Delete member by index number.<br />
	 * 根据索引删除成员
	 *
	 * @param num the index number<br />
	 *            成员索引
	 * @return true, if delete succeed<br />
	 *         如果删除成功，返回true。
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public boolean deleteMemberByNum(int num) throws KSException;

	/**
	 * Delete member by variant value.<br />
	 * 根据变量值删除成员
	 *
	 * @param var the value<br />
	 *            变量值
	 * @return true, if delete succeed<br />
	 *         如果删除成功，返回true。
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public boolean deleteMemberByVariant(KVariant var) throws KSException;

	/**
	 * Do operation by name.<br />
	 * 根据成员名对成员进行操作。
	 *
	 * @param op   the opcode<br />
	 *             操作符
	 * @param name the member name<br />
	 *             成员名
	 * @param opr  the operand<br />
	 *             操作数
	 * @return return value after operation<br />
	 *         返回 计算结果
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException;

	/**
	 * Do operation by index number.<br />
	 * 根据索引对成员进行操作
	 *
	 * @param op  the opcode<br />
	 *            操作符
	 * @param num the index number<br />
	 *            成员索引
	 * @param opr the operand<br />
	 *            操作数
	 * @return return value after operation<br />
	 *         返回 计算结果
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant doOperationByNum(AssignOperation op, int num, KVariant opr) throws KSException;

	/**
	 * Do operation by variant value.<br />
	 * 根据变量值对成员进行操作
	 *
	 * @param op  the opcode<br />
	 *            操作符
	 * @param var the variant value<br />
	 *            变量值
	 * @param opr the operand<br />
	 *            操作数
	 * @return return do operaton by variant <br />
	 *         返回 k variant
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException;

	/**
	 * Enum members.<br />
	 *
	 * @param cosumer the cosumer<br />
	 * @param flag    operation flag<br />
	 *                操作标签
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException;

	/**
	 * Func call by name.<br />
	 *
	 * @param name    the name<br />
	 * @param args    the args<br />
	 * @param objthis the objthis<br />
	 * @param flag    operation flag<br />
	 *                操作标签
	 * @return return func call by name <br />
	 *         返回 k variant
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant funcCallByName(String name, KVariant[] args, KObject objthis, int flag) throws KSException;

	/**
	 * Func call by num.<br />
	 *
	 * @param num     the num<br />
	 * @param args    the args<br />
	 * @param objthis the objthis<br />
	 * @param flag    operation flag<br />
	 *                操作标签
	 * @return return func call by num <br />
	 *         返回 k variant
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant funcCallByNum(int num, KVariant[] args, KObject objthis, int flag) throws KSException;

	/**
	 * Gets the member by name.<br />
	 * 根据名字获取成员.
	 *
	 * @param name the name of member<br />
	 *             成员名
	 * @param flag operation flag<br />
	 *             操作标签
	 * @param objthis TODO
	 * @return value of member<br />
	 *         成员值
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException;

	/**
	 * Gets the member by index number.<br />
	 * 根据索引获取成员.
	 *
	 * @param num  the index number of member<br />
	 *             成员索引
	 * @param flag operation flag<br />
	 *             操作标签
	 * @return value of member<br />
	 *         成员值
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant getMemberByNum(int num, int flag) throws KSException;

	/**
	 * Gets the member by variant value.<br />
	 * 根据变量值获取成员.
	 *
	 * @param var  the variant<br />
	 *             变量
	 * @param flag operation flag<br />
	 *             操作标签
	 * @param objthis TODO
	 * @return value of member<br />
	 *         成员值
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant getMemberByVariant(KVariant var, int flag, KObject objthis) throws KSException;

	/**
	 * Gets the native instance.<br />
	 * 获取 native instance.
	 *
	 * @param <T> the generic type<br />
	 *            泛型参数
	 * @param cls the cls<br />
	 * @return native instance<br />
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public <T> T getNativeInstance(Class<T> cls) throws KSException;

	/**
	 * Has member by name.<br />
	 *
	 * @param name the name<br />
	 * @param flag operation flag<br />
	 *             操作标签
	 * @return true, if <br />
	 *         如果，返回true。
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public boolean hasMemberByName(String name, int flag) throws KSException;

	/**
	 * Has member by num.<br />
	 *
	 * @param num the num<br />
	 * @return true, if <br />
	 *         如果，返回true。
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public boolean hasMemberByNum(int num) throws KSException;

	/**
	 * Has member by variant.<br />
	 *
	 * @param var the var<br />
	 * @return true, if <br />
	 *         如果，返回true。
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public boolean hasMemberByVariant(KVariant var) throws KSException;

	/**
	 * Put native instance.<br />
	 *
	 * @param nis the nis<br />
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public void putNativeInstance(Object nis) throws KSException;
	/**
	 * Put native instance.<br />
	 *
	 * @param nis the nis<br />
	 * @throws KSException Any Engine Exception occured<br />
	 *                     引擎执行发生的异常。
	 */
	public boolean hasNativeInstance(Class<?> cls) throws KSException;

	/**
	 * Set member by name.<br />
	 * 根据成员名设置成员值
	 *
	 * @param name the name<br />
	 * @param val  the value<br />
	 * @param flag operation flag<br />
	 *             操作标签
	 * @return return value of val<br />
	 *         返回val的值
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException;

	/**
	 * Set member by index number.<br />
	 * 根据索引设置成员
	 *
	 * @param num  the index number of member<br />
	 *             成员索引
	 * @param val  the value<br />
	 *             值
	 * @param flag operation flag<br />
	 *             操作标签
	 * @return return value of val<br />
	 *         返回val的值
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException;

	/**
	 * Set member by variant value.<br />
	 * 根据变量值获得成员
	 *
	 * @param var  the var<br />
	 * @param val  the val<br />
	 * @param flag operation flag<br />
	 *             操作标签
	 * @return return set member by variant <br />
	 *         返回 k variant
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException;

	/**
	 * Gets the this object.<br />
	 * 类内获取 this.
	 *
	 * @return this<br />
	 * @throws ScriptException
	 */
	public KEnvironment getThis() throws KSException;

	/**
	 * Gets the super class.<br />
	 * 类内获取超类.
	 *
	 * @return super<br />
	 * @throws InvalidSuperClassException
	 */
	public KEnvironment getSuper() throws KSException;
}
