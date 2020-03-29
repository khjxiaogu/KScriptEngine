package com.khjxiaogu.scriptengine.core.syntax.block;

import java.util.ArrayList;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.GlobalEnvironment;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;

public class GlobalCodeBlock extends CodeBlock {

	public GlobalCodeBlock() {
		super(CodeBlockAttribute.STATEMENT);
	}

	public KVariant eval() throws KSException {
		GlobalEnvironment cbenv = GlobalEnvironment.getGlobal();
		int i = 0;
		if (nodes.size() == 0)
			return null;
		try {
			KVariant result = null;
			for (; i < nodes.size(); i++) {
				result = nodes.get(i).eval(cbenv);
			}
			return result;
		} catch (ScriptException e) {
			e.filename = name;
			e.colume = 0;
			e.line = i + 1;
			throw e;
		}
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		name = reader.getName();
		// TODO Auto-generated method stub
		try {
			while (reader.has()) {
				put(parser.parseUntilOrEnd(reader, ';'));
			}
		} catch (SyntaxError e) {
			e.filename = name;
			e.colume = 0;
			e.line = nodes.size() + 1;
			throw e;
		}
		super.VisitAsChild(new ArrayList<String>());
		return this;
	}
}
