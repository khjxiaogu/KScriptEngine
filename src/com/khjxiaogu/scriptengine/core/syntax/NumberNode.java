package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:NumberNode.java
 */
public class NumberNode extends ConstantNode implements ASTParser, CodeNode {

	/**
	 *
	 */
	

	public NumberNode() {
		// TODO Auto-generated constructor stub
	}

	public NumberNode(int i) throws KSException {
		// TODO Auto-generated constructor stub
		super(KVariant.valueOf(i));
	}

	public NumberNode(double i) throws KSException {
		// TODO Auto-generated constructor stub
		super(KVariant.valueOf(i));
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		Double value = 0.0D;
		Double dot = 1D;
		boolean dotted = false;
		boolean hex = false;
		boolean oct = false;
		int degit = 10;
		char c = reader.read();
		
		char c2 = reader.read(1);
		if (c == '0'&&c2!='.') {
			if (c2 == 'x' || c2 == 'X') {
				hex = true;
				degit = 16;
				reader.eat();
			} else {
				oct = true;
				degit = 8;
			}
			c=reader.eat();
			
		}
		while (true) {
			if (c >= '0' && c <= '9') {
				if (oct && c >= '8')
					throw new ScriptException("错误的八进制数");
				value *= degit;
				value += c - '0';
				if (dotted) {
					dot *= degit;
				}

			} else if (c == '.') {
				dotted = true;
			} else if (hex) {
				char lowerc = Character.toLowerCase(c);
				if (lowerc >= 'a' && lowerc <= 'f') {
					value *= degit;
					value += c - 'a' + 10;
					if (dotted) {
						dot *= degit;
					}

				}else break;
			} else {
				break;
			}
			c = reader.eat();
		}
		value /= dot;
		this.value=KVariant.valueOf(value);
		// TODO Auto-generated method stub
		return this;
	}


}
