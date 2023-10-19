package com.khjxiaogu.scriptengine.core.object.internal;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;
import com.khjxiaogu.scriptengine.core.syntax.Nop;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public class ArrayNode implements CodeNode,ASTParser,ObjectOperator {
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder("[");
		for(CodeNode elm:elms) {
			sb.append(elm.toString());
			sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}

	private List<CodeNode> elms=new ArrayList<>();
	@Override
	public void Visit(List<String> parentMap) throws KSException {
		for(CodeNode co:elms) {
			Visitable.Visit(co, parentMap);
		}
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		KObject ko=ObjectArray.createArray();
		int i=0;
		for(CodeNode co:elms) {
			ko.setMemberByNum(i++,co.eval(env),KEnvironment.DEFAULT);
		}
		return KVariant.valueOf(ko);
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser sp=new StatementParser();
		outer:
			while(true) {
				CodeNode ln=sp.parseUntil(reader,',',']');
				char c=reader.read();
				reader.eat();
				switch(c) {
				case ',':elms.add(ln);break;
				case ']':if(!(ln instanceof Nop))elms.add(ln);break outer;
				default:throw new SyntaxError("错误的数组语句，错误出现的"+c,reader);
				}
			}
		return this;
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
	}

	@Override
	public KEnvironment getObject(KEnvironment env) throws KSException {
		KObject ko=ObjectArray.createArray();
		int i=0;
		for(CodeNode co:elms) {
			ko.setMemberByNum(i++,co.eval(env),KEnvironment.DEFAULT);
		}
		return ko;
	}

}
