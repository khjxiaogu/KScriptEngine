package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.typeconvert.ConvertionException;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:NumberNode.java
 */
public class NumberNode implements ASTParser, CodeNode {

	/**
	 *
	 */
	KVariant number = new KVariant();

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
		Double value = 0.0D;
		Double dot = 1D;
		boolean dotted = false;
		boolean hex=false;
		boolean oct=false;
		int degit=10;
		char c = reader.read();
		char c2=reader.read(1);
		if(c=='0') {
			if(c2=='x'||c2=='X') {
				hex=true;
				degit=16;
			}else{
				oct=true;
				degit=8;
			}
		}
		while (true) {
			if (c >= '0' && c <= '9') {
				if(oct&&c>='8') {
					throw new ScriptException("错误的八进制数");
				}
				value *= degit;
				value += c - '0';
				if (dotted) {
					dot *= degit;
				}
				
			} else if (c == '.') {
				dotted = true;
			} else if(hex){
				char lowerc=Character.toLowerCase(c);
				if (lowerc>='a'&&lowerc<='f') {
					value *= degit;
					value += c - 'a'+9;
					if (dotted) {
						dot *= degit;
					}
					
				}
			}else{
				break;
			}
			c = reader.eat();
		}
		value /= dot;
		number.setNumber(value);
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String toString() {
		return number.toString();
	}
}
