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
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.Nop;
import com.khjxiaogu.scriptengine.core.syntax.NumberNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.StringNode;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public class DictionaryNode implements ASTParser, CodeNode, ObjectOperator {
	private static class Node{
		String assignment;
		CodeNode exp;
		public Node(CodeNode assignment, CodeNode exp) throws SyntaxError {
			if(assignment instanceof LiteralNode) {
				this.assignment = ((LiteralNode) assignment).getToken();
			}else if(assignment instanceof StringNode) {
				this.assignment=assignment.toString();
			}else if(assignment instanceof NumberNode) {
				this.assignment=((NumberNode)assignment).getValue().toString();
			}else
				throw new SyntaxError("错误的词典构造");
			this.exp = exp;
		}
		@Override
		public String toString() {
			return "\""+assignment+"\"=>"+exp.toString();
		}
	}
	public DictionaryNode() {
	}
	private List<Node> elms=new ArrayList<>();
	@Override
	public void Visit(List<String> parentMap) throws KSException {
		for(Node elm:elms) {
			Visitable.Visit(elm.exp, parentMap);
		}
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
		KObject ko=ObjectDictionary.createDictionary();
		int i=0;
		for(Node co:elms) {
			ko.setMemberByName(co.assignment,co.exp.eval(env),KEnvironment.DEFAULT);
		}
		return ko;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		KObject ko=ObjectDictionary.createDictionary();
		int i=0;
		for(Node co:elms) {
			ko.setMemberByName(co.assignment,co.exp.eval(env),KEnvironment.DEFAULT);
		}
		return new KVariant(ko);
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser sp=new StatementParser();
		CodeNode assign=null;
		outer:
			while(true) {
				CodeNode ln=sp.parseUntil(reader,',',']','=',':');
				char c=reader.read();
				char x=reader.eat();
				switch(c) {
				case ',':elms.add(new Node(assign,ln));assign=null;break;
				case '=':
					if(x!='>')
						throw new SyntaxError("错误的var语句，错误出现的"+c,reader);
				reader.eat();
				case ':':assign=ln;break;
				case ']':if(assign!=null)elms.add(new Node(assign,ln));break outer;
				default:throw new SyntaxError("错误的var语句，错误出现的"+c,reader);
				}
			}
		return this;
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder("%[");
		for(Node elm:elms) {
			sb.append(elm.toString());
			sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
}
