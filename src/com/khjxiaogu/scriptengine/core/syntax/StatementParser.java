package com.khjxiaogu.scriptengine.core.syntax;

import java.util.ArrayList;
import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.block.Block;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.DoubleOperator;
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
	CodeNode last = null;

	public StatementParser() {
		// TODO Auto-generated constructor stub
	}

	public void put(CodeNode node) {
		if (node != null) {
			nodes.add(last = node);
		}
	}

	public void clear() {
		nodes.clear();
		td.reset();
	}

	public CodeNode parseTree(ParseReader reader) throws KSException {// 1+2*3+4*5
		Operator ret = null;// top level operator
		Operator pending = null;// deciding operator
		CodeNode last = null;// last node
		if (nodes.size() == 1)
			return nodes.get(0);
		for (CodeNode current : nodes) {
			//System.out.println(current.getClass().getSimpleName());
			
			if (StatementParser.isOperator(current)) {
				Operator op = (Operator) current;
				if (pending != null) {
					if (pending.getPriority() >= op.getPriority()
							+ (op.getAssociative() == Associative.RIGHT ? 0 : 1)) {
						// ret.setChildren(null,op);
						pending.setChildren(null, last);
						op.setChildren(ret, null);
						ret = op;
						last = null;
					} else if(last==null&&pending instanceof DoubleOperator) {
							CodeNode cnr=((DoubleOperator)pending).getRight();
							op.setChildren(cnr,null);
							pending.setChildren(null,op);
						}else {
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
			} else
				throw new SyntaxError("错误出现的 '" + current.toString() + "在" + last.toString() + "'之后,缺失运算符.",reader);
		}
		if (last != null) {
			if (pending != null) {
				pending.setChildren(null, last);
			} else
				return last;
		}
		// System.out.println(ret.getClass().getSimpleName());
		return ret;
	}

	public CodeNode parseUntilOrBlock(ParseReader reader, char until) throws KSException {
		while (true) {
			char c = reader.eatAllSpace();
			if (c != until) {
				put(td.parse(reader));
				if (last instanceof Block) {
					break;
				}
				// td.reset();
			} else {
				break;
			}
		}
		CodeNode ret = parseTree(reader);
		if (ret == null) {
			ret = new Nop();
		}
		if (reader.read() == until) {
			reader.eat();
		}
		td.reset();
		clear();
		return ret;
	}

	public CodeNode parseUntil(ParseReader reader, char... untils) throws KSException {
		Arrays.parallelSort(untils);
		while (true) {
			char c = reader.eatAllSpace();
			int srh = Arrays.binarySearch(untils, c);
			// System.out.println(srh);
			if (srh < 0) {
				put(td.parse(reader));
				// td.reset();
			} else {
				// System.out.println("break");
				break;
			}
		}
		CodeNode ret = parseTree(reader);
		if (ret == null) {
			ret = new Nop();
		}
		td.reset();
		clear();
		return ret;
	}

	public CodeNode parseUntilOrEnd(ParseReader reader, char until) throws KSException {
		while (true) {
			if (!reader.has()) {
				break;
			}
			char c = reader.eatAllSpace();
			if (!reader.has()) {
				break;
			}
			if (c != until) {
				put(td.parse(reader));
				if (last instanceof Block) {
					break;
				}
			} else {
				break;
			}
		}
		CodeNode ret = parseTree(reader);
		if (ret == null) {
			ret = new Nop();
		}
		if (reader.has() && reader.read() == until) {
			reader.eat();
		}
		td.reset();
		clear();
		return ret;
	}

	static boolean isOperator(CodeNode c) {
		return c instanceof Operator;
	}
}
