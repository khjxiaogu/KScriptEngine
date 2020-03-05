package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlockEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Case;

public class SwitchStatement extends CodeBlock {

	List<Case> cases = new ArrayList<>();
	List<Integer> casepos = new ArrayList<>();
	int defaultpos = -1;
	CodeNode cond;

	public SwitchStatement() {
		super(CodeBlockAttribute.BREAKABLE);
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		CodeBlockEnvironment cbenv = new CodeBlockEnvironment(env, off, siz, this, attr);
		KVariant res = cond.eval(cbenv);

		int i = defaultpos;
		for (int j = 0; j < cases.size(); j++) {
			if (res.equals(cases.get(j).getCond().eval(cbenv))) {
				i = casepos.get(j);
				break;
			}
		}
		if (nodes.size() == 0 || i == -1)
			return null;
		try {
			for (; i < nodes.size(); i++) {
				nodes.get(i).eval(cbenv);
				if (cbenv.isSkipped()) {
					break;
				}
			}
			if (cbenv.isStopped())
				return null;
			return null;
		} catch (ScriptException e) {
			e.filename = name;
			e.colume = 0;
			e.line = i + 1;
			throw e;
		}
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		while (true) {
			if (!reader.has()) {
				break;
			}
			char c = reader.read();
			//
			while (Character.isWhitespace(c)) {
				c = reader.eat();
			}
			if (!reader.has()) {
				break;
			}
			if (c == '(') {
				parser.clear();
				c = reader.eat();
				cond = parser.parseUntilOrBlock(reader, ')');
				c = reader.read();
				while (Character.isWhitespace(c)) {
					c = reader.eat();
				}
				// System.out.println(c);
				if (c == '{') {
					c = reader.eat();
					break;
				} else
					throw new SyntaxError("无效的switch语句");
			} else
				throw new SyntaxError("无效的switch语句");
		}
		super.parse(reader);
		for (int i = 0; i < super.nodes.size(); i++) {
			CodeNode cur = super.nodes.get(i);
			if (cur instanceof Case) {
				cases.add((Case) cur);
				casepos.add(i);
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return "switch(" + cond.toString() + ")" + super.toString();
	}

	@Override
	public void Visit(List<String> parentMap) {
		Visitable.Visit(cond, parentMap);
		super.Visit(parentMap);
	}
}
