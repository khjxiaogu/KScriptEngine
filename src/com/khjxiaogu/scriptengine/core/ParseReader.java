/*
 * file: ParseReader.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;

// TODO: Auto-generated Javadoc
/**
 * Interface ParseReader.
 * reader for parsers
 * 为解析器设计的读取器
 *
 * @author khjxiaogu
 * @time 2020年2月19日 file:ParseReader.java
 */
public interface ParseReader {

	/**
	 * Read a char from current position.<br />
	 * 从当前位置读取一个字符
	 *
	 * @return return readed<br />
	 *         返回读取后的字符
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public char read() throws KSException;

	/**
	 * Read char from an offset after current position.<br />
	 * 从当前位置后offset个字符读取
	 *
	 * @param off the offset<br />
	 *            位移
	 * @return return readed <br />
	 *         返回读取后的字符
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public char read(int off) throws KSException;

	/**
	 * Read a string from a start offset and length.<br />
	 * 从指定位置开始读取一定长度的字符串
	 *
	 * @param off   the offset<br />
	 *              位移
	 * @param count read length<br />
	 *              读取长度
	 * @return return readed <br />
	 *         返回读取后的字符串
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public String reads(int off, int count) throws KSException;

	/**
	 * Move the position a char after,returns char at new position.<br />
	 * 指针向后移动一个字符，返回之后的字符
	 *
	 * @return return chat at new position<br />
	 *         返回之后的字符
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public char eat() throws KSException;

	/**
	 * Move the position a number of chars later.<br />
	 * 指针向后移动count个字符。
	 *
	 * @param count the count<br />
	 *              移动量
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public void eat(int count) throws KSException;

	/**
	 * Move the position to skip all spaces.<br />
	 * 指针向后移动直到目标字符不是空格字符，返回之后的字符
	 *
	 * @return return char at new position<br />
	 *         返回之后的字符
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public char eatAllSpace() throws KSException;
	
	/**
	 * skip whole line.<br />
	 * 跳到下一行。
	 * @throws KSException Any Engine Exception occurred<br />
	 *                     引擎执行发生的异常。
	 */
	public void eatLine() throws KSException;
	/**
	 * Move position a char forward,putting ch back if needed.<br />
	 * 指针向前移动一个字符，如果需要的话把ch重新放入。
	 * remarks: ch may be ignored if the stream is buffered, and only one char would
	 * be rewind.
	 *
	 * @param ch the char to insert<br />
	 *           要被插入的字符
	 */
	public void rewind(char ch);

	/**
	 * Check if the reader has content.<br />
	 * 检查读取器是否还有剩余的内容
	 *
	 * @return true, if the reader still has content to read<br />
	 *         如果还有剩余，返回true。
	 */
	public boolean has();

	/**
	 * Gets the reader's name for logging, which often is the file or script
	 * name.<br />
	 * 获取读取器的名字用于填写日志，通常是读取器指向的文件名.
	 *
	 * @return reader's name<br />
	 *         读取器的名字
	 */
	public String getName();

	/**
	 * Gets the current line.<br />
	 * 获取当前行.
	 *
	 * @return line<br />
	 */
	public int getLine();

	/**
	 * Gets the column.<br />
	 * 获取当前列.
	 *
	 * @return column<br />
	 */
	public int getCol();
}
