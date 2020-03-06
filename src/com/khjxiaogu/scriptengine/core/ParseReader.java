package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:ParseReader.java
 */
public interface ParseReader {
	public char read() throws KSException;

	public char read(int off) throws KSException;

	public String reads(int off, int count) throws KSException;

	public char eat() throws KSException;

	public void eat(int count) throws KSException;

	public char eatAll() throws KSException;

	public void rewind(char ch);

	public boolean has();

	public String getName();

	public int getLine();

	public int getCol();
}
