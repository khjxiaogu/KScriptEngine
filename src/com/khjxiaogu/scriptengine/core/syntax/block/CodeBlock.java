package com.khjxiaogu.scriptengine.core.syntax.block;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.JVMError;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;
import com.khjxiaogu.scriptengine.core.syntax.Nop;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.VisitContext;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public class CodeBlock implements Block, Visitable {
	protected List<CodeNode> nodes = new ArrayList<>();
	protected StatementParser parser = new StatementParser();
	protected CodeBlockAttribute attr;
	protected String name;
	protected int off;
	protected int siz;
	protected String[] symbol;

	public CodeBlock(CodeBlockAttribute attr) {
		// TODO Auto-generated constructor stub
		this.attr = attr;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		CodeBlockEnvironment cbenv = new CodeBlockEnvironment(env, off, siz, this, attr, symbol);
		int i = 0;
		if (nodes.size() == 0)
			return null;
		try {
			KVariant result = KVariant.valueOf();
			for (; i < nodes.size(); i++) {
				result = nodes.get(i).eval(cbenv);
				if (cbenv.isSkipped()) {
					break;
				}
			}
			if (attr == CodeBlockAttribute.STATEMENT)
				return result;
			else if (attr == CodeBlockAttribute.RETURNABLE)
				return cbenv.getRet();
			else if (attr == CodeBlockAttribute.BREAKABLE) {
				if (cbenv.isStopped())
					return null;
				return KVariant.TRUE;
			}
		} catch (ScriptException e) {
			e.fillTrace(name,i+1,0,nodes.get(i).toString());
			throw e;
		}catch(Exception e) {
			throw new JVMError(e);
		}
		return null;
	}

	/**
	 * for class object,would run without changing context
	 *
	 * @param env
	 * @return
	 * @throws KSException
	 */
	@Override
	public void init(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		int i = 0;
		if (nodes.size() == 0)
			return;
		try {
			for (; i < nodes.size(); i++) {
				CodeNode cn;
				cn = nodes.get(i);
				if (cn.getClass() == CodeBlock.class) {
					((CodeBlock) cn).attr = CodeBlockAttribute.OBJECT;
					((CodeBlock) cn).init(env);
				} else {
					cn.eval(env);
				}
			}
		} catch (ScriptException e) {
			e.fillTrace(name,i+1,0,nodes.get(i).toString());
			throw e;
		}catch(Exception e) {
			throw new JVMError(e);
		}
	}

	public void put(CodeNode node) {
		if(node instanceof Nop)return;
		nodes.add(node);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (CodeNode node : nodes) {
			sb.append(node.toString());
			sb.append(";\n");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		name = reader.getName();
		// TODO Auto-generated method stub
		try {
			while (true) {
				if (!reader.has()) {
					break;
				}
				char c = reader.eatAllSpace();
				if (!reader.has()) {
					break;
				}

				if (c != '}') {
					if (attr == CodeBlockAttribute.STATEMENT) {
						put(parser.parseUntilOrEnd(reader, ';'));
					} else {
						put(parser.parseUntilOrBlock(reader, ';'));
					}
					parser.clear();
				} else {
					reader.eat();
					break;
				}
			}
		} catch (SyntaxError e) {
			e.fillTrace(name,reader.getLine(),reader.getCol());
			throw e;
		}
		return this;
	}

	public CodeNode parseExp(ParseReader reader) throws KSException {
		name = reader.getName();
		// TODO Auto-generated method stub
		try {
			put(parser.parseUntilOrBlock(reader, ';'));
			parser.clear();
		} catch (SyntaxError e) {
			e.fillTrace(name,reader.getLine(),reader.getCol());
			throw e;
		}
		return this;
	}

	@Override
	public void Visit(VisitContext context) throws KSException {
		VisitContext cur=context.child();
		off = cur.getOffset();
		for (int i = 0; i < nodes.size(); i++) {
			CodeNode node = nodes.get(i);
			Visitable.Visit(node, cur);
		}
		siz = cur.getCurrentSize();
		symbol = cur.getSymbols();
	}
}
