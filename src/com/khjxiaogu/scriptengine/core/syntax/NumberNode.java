package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.typeconvert.ConvertionException;

/**
 * @author khjxiaogu
 * @time 2020年2月19日
 * file:NumberNode.java
 */
public class NumberNode implements ASTParser, CodeNode {

	/**
	 * 
	 */
	KVariant number=new KVariant();
	public NumberNode() {
		// TODO Auto-generated constructor stub
	}

	public NumberNode(int i) throws ConvertionException {
		// TODO Auto-generated constructor stub
		number.setNumber((double) i);
	}
	public NumberNode(double i) throws ConvertionException {
		// TODO Auto-generated constructor stub
		number.setNumber(i);
	}
	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return number;
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		Double value=0.0D;
		Double dot=1D;
		boolean dotted=false;
		char c=reader.read();
		while(true) {
			if(c>='0'&&c<='9') 	{
				value*=10;
				value+=c-'0';
				if(dotted) {
					dot*=10;
				}
			}else if(c=='.') {
				dotted=true;
			}else
				break;
			c=reader.eat();
		}
		value/=dot;
		number.setNumber(value);
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public String toString() {
		return number.toString();
	}
}
