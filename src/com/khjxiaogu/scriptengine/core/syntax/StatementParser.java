package com.khjxiaogu.scriptengine.core.syntax;

import java.util.ArrayList;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.Operator;

/**
 * @author khjxiaogu
 * @time 2020年2月20日 file:StatementParser.java
 */
public class StatementParser {

	/**
	 * 
	 */
	ArrayList<CodeNode> nodes = new ArrayList<>();
	TokenDecider td = new TokenDecider();
	CodeNode last=null;
	public StatementParser() {
		// TODO Auto-generated constructor stub
	}

	public void put(CodeNode node) {
		nodes.add(last=node);
	}

	public void clear() {
		nodes.clear();
		td.reset();
	}

	public CodeNode parseTree() throws KSException {// 1+2*3+4*5
		Operator ret = null;// top level operator
		Operator pending = null;// deciding operator
		CodeNode last = null;// last node
		for (CodeNode current : nodes) {
			// System.out.println(current.getClass().getSimpleName());
			if (isOperator(current)) {
				Operator op = (Operator) current;
				if (pending != null) {
					if (pending.getPriority() >= op.getPriority()
							+ (op.getAssociative() == Associative.RIGHT ? 1 : 0)) {
						// ret.setChildren(null,op);
						pending.setChildren(null, last);
						op.setChildren(ret, null);
						ret = op;
						last = null;
					} else {
						pending.setChildren(null, op);
						op.setChildren(last, null);
						last = null;
					}
				} else {
					op.setChildren(last, null);
					last = null;
				}
				pending = op;
				if (ret == null) {
					ret = op;
					continue;
				}
			} else if (last == null) {
				last = current;
			} else {
				throw new SyntaxError("unexpected '" + current.toString()+"after"+last.toString() + "', excpected operator.");
			}
		}
		if (last != null) {
			if (pending != null) {
				pending.setChildren(null, last);
			} else {
				return last;
			}
		}
		// System.out.println(ret.getClass().getSimpleName());
		return ret;
	}

	public CodeNode parseUntil(ParseReader reader, char until) throws KSException {
		while (true) {
			char c = reader.read();

			while (Character.isWhitespace(c)) {
				c = reader.eat();
			}
			if (c != until) {
				put(td.parse(reader));
			} else {
				CodeNode ret = parseTree();
				if(ret==null)
					ret=new Nop();
				reader.eat();
				td.reset();
				clear();
				return ret;
			}
		}
	}

	public CodeNode parseUntilOrEnd(ParseReader reader, char until) throws KSException {
		while (true) {
			if (!reader.has()) {
				break;
			}
			char c = reader.read();
			// System.out.print(c);
			while (Character.isWhitespace(c)) {
				c = reader.eat();
			}
			if (!reader.has()) {
				break;
			}
			if (c != until) {
				put(td.parse(reader));
				if(last instanceof Block)
					break;
			} else {
				break;
			}
		}
		CodeNode ret = parseTree();
		if(reader.has()&&reader.read()==until)
			reader.eat();
		td.reset();
		clear();
		return ret;
	}

	static boolean isOperator(CodeNode c) {
		return c instanceof Operator;
	}
}
