package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.Exception.KSException;

/**
 * @author khjxiaogu
 * @time 2020年2月20日
 * file:StringParseReader.java
 */
public class StringParseReader implements ParseReader {
	String backed;
	int pos=0;
	String name;
	/**
	 * 
	 */
	public StringParseReader(String s) {
		// TODO Auto-generated constructor stub
		backed=s;
		name="anoymous";
	}
	public StringParseReader(String s,String name) {
		// TODO Auto-generated constructor stub
		backed=s;
		this.name=name;
	}
	@Override
	public char read() throws KSException {
		// TODO Auto-generated method stub
		if(has())
			return backed.charAt(pos);
		else
			return 0;
	}

	@Override
	public char read(int off) throws KSException {
		// TODO Auto-generated method stub
		return backed.charAt(pos+off);
	}

	@Override
	public char eat() throws KSException {
		// TODO Auto-generated method stub
		//System.out.print(backed.charAt(pos));
		++pos;
		return read();
	}

	@Override
	public void eat(int count) throws KSException {
		// TODO Auto-generated method stub
		pos+=count;
	}

	@Override
	public void rewind(char ch) {
		// TODO Auto-generated method stub
		--pos;
	}

	@Override
	public boolean has() {
		// TODO Auto-generated method stub
		return pos<=backed.length()-1;
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

}
