package com.khjxiaogu.scriptengine.core.syntax;

import java.util.ArrayList;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.Operator;

/**
 * @author khjxiaogu
 * @time 2020年2月20日
 * file:StatementParser.java
 */
public class StatementParser {

	/**
	 * 
	 */
	ArrayList<CodeNode> nodes=new ArrayList<>();
	TokenDecider td=new TokenDecider();
	public StatementParser() {
		// TODO Auto-generated constructor stub
	}
	public void put(CodeNode node) {
		nodes.add(node);
	}
	public void clear() {
		nodes.clear();
	}
	public CodeNode parseTree() throws KSException{//1+2*3+4*5
		Operator ret=null;//top level operator
		Operator pending=null;//deciding operator
		CodeNode last=null;//last node
		for(int i=0;i<nodes.size();i++) {
			CodeNode current=nodes.get(i);
			//System.out.println(current.getClass().getSimpleName());
			if(isOperator(current)) {
				Operator op=(Operator) current;
				if(pending!=null) {
					if(pending.getPriority()>=op.getPriority()+((op.getAssociative()==Associative.RIGHT)?1:0)) {
						//ret.setChildren(null,op);
						pending.setChildren(null,last);
						op.setChildren(ret,null);
						ret=op;
						last=null;
					}else{
						pending.setChildren(null,op);
						op.setChildren(last,null);
						last=null;
					}
				}else {
					op.setChildren(last,null);
					last=null;
				}
				pending=op;
				if(ret==null) {
					ret=op;
					continue;
				}
			}else if(last==null) {
				last=current;
			}else
				throw new SyntaxError("unexpected '"+current.toString()+"', excpected operator.");
		}
		if(last!=null)
			if(pending!=null)
				pending.setChildren(null,last);
			else
				return last;
		//System.out.println(ret.getClass().getSimpleName());
		return ret;
	}
	public CodeNode parseUntil(ParseReader reader,char until) throws KSException {
		while(true) {
			char c=reader.read();
			
			while(Character.isWhitespace(c))
				c=reader.eat();
			if(c!=until) {
				put(td.parse(reader));
			}else {
				CodeNode ret=parseTree();
				reader.eat();
				td.reset();
				clear();
				return ret;
			}
		}
	}
	public CodeNode parseUntilOrEnd(ParseReader reader,char until) throws KSException {
		while(true) {
			if(!reader.has())
				break;
			char c=reader.read();
			//System.out.print(c);
			while(Character.isWhitespace(c))
				c=reader.eat();
			if(!reader.has())
				break;
			if(c!=until) {
				put(td.parse(reader));
			}else {
				break;
			}
		}
		CodeNode ret=parseTree();
		reader.eat();
		td.reset();
		clear();
		return ret;
	}
	static boolean isOperator(CodeNode c) {
		return c instanceof Operator;
	}
}
