package com.khjxiaogu.scriptengine.core.syntax.operator.p15;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.Closure;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;
import com.khjxiaogu.scriptengine.core.syntax.statement.ArgumentNode;

public class FuncCall extends SingleOperator implements ASTParser {
	CodeNode[] args;

	public FuncCall() {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		KVariant func = super.Child.eval(env);
		KVariant[] arg = new KVariant[args.length];
		for (int i = 0; i < args.length; i++) {
			arg[i] = args[i].eval(env);
		}
		KObject obj = (KObject) func.asType("Object");
		if (obj instanceof Closure)
			return obj.FuncCall(arg, env);
		else
			throw new ScriptException("对象不是函数");
	}

	@Override
	public int getPriority() {
		return 15;
	}

	@Override
	public Associative getAssociative() {
		return Associative.RIGHT;
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		ArgumentNode an = new ArgumentNode('(');
		an.parse(reader);
		List<CodeNode> cns = an.getAll();
		if (cns.size() != 0) {
			args = new CodeNode[cns.size()];
			args = cns.toArray(args);
		}
		return this;
	}

	@Override
	public String toString() {
		String pardesc = "";
		for (int i = 0; i < args.length; i++) {
			pardesc += args[i];
			if (i != args.length - 1) {
				pardesc += ",";
			}
		}
		return super.Child + "(" + pardesc + ")";
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		for (CodeNode cn : args) {
			Visitable.Visit(cn, parentMap);
		}
		super.Visit(parentMap);
	}

}
