package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;

/**
 * @author khjxiaogu
 * @time 2020年2月20日 file:StringParseReader.java
 */
public class StringParseReader implements ParseReader {


	String backed;
	int pos = 0;
	String name;
	int line=1;
	int col=1;
	/**
	 *
	 */
	public StringParseReader(String s) {
		// TODO Auto-generated constructor stub
		backed = s;
		name = "anoymous";
	}

	public StringParseReader(String s, String name) {
		// TODO Auto-generated constructor stub
		backed = s;
		this.name = name;
	}

	@Override
	public char read() throws KSException {
		// TODO Auto-generated method stub
		if (has())
			return backed.charAt(pos);
		throw new SyntaxError("错误的文档结尾",this);
	}

	@Override
	public char read(int off) throws KSException {
		// TODO Auto-generated method stub
		if(has(off))
			return backed.charAt(pos + off);
		throw new SyntaxError("错误的文档结尾",this);
	}

	@Override
	public char eat() throws KSException {
		// TODO Auto-generated method stub
		// System.out.print(backed.charAt(pos));
		++pos;
		++col;
		if (has()) {
			char c=read();
			if(c=='\n'){
				line++;
				col=1;
			}
			return c;
		}else if (pos > backed.length())
			throw new SyntaxError("错误的文档结尾",this);
		else
			return 0;
	}

	@Override
	public void eat(int count) throws KSException {
		// TODO Auto-generated method stub
		pos += count;
	}

	@Override
	public void rewind(char ch) {
		// TODO Auto-generated method stub
		--pos;
	}

	@Override
	public boolean has() {
		// TODO Auto-generated method stub
		return pos <= backed.length() - 1;
	}
	public boolean has(int len) {
		// TODO Auto-generated method stub
		return pos+len <= backed.length() - 1;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCol() {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public String reads(int off, int count) throws KSException {
		return backed.substring(pos + off, pos + off + count);
	}

	@Override
	public char eatAllSpace() throws KSException {
		while (Character.isWhitespace(backed.charAt(pos))) {
			pos++;
			if (pos >= backed.length())
				throw new SyntaxError("错误的文档结尾",this);
		}
		return read();
	}
	@Override
	public void eatLine() throws KSException {
		if(read()!='\n') {
			while(eat()!='\n');
		}
		eat();
	}

	@Override
	public boolean eat(char ch) throws KSException {
		if(read(1)==ch) {
			eat();
			return true;
		}
		return false;
	}
}
