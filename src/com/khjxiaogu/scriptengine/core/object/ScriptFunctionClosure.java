package com.khjxiaogu.scriptengine.core.object;

import java.util.Arrays;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlock;

/**
 * @author khjxiaogu
 * @time 2020年3月5日
 *       project:khjScriptEngine
 */
public class ScriptFunctionClosure extends Closure implements CallableFunction {

	/**
	 *
	 */
	CodeBlock functionBody;
	CodeNode[] defargs;
	int off;

	public ScriptFunctionClosure(KEnvironment env, CodeBlock functionBody, int off, CodeNode[] args) {
		super(env);
		this.functionBody = functionBody;
		this.off = off;
		defargs = args;
	}

	@Override
	public String toString() {
		return "(Function)"+super.getInstancePointer();
	}

	@Override
	public boolean isValid() {
		return functionBody == null;
	}

	@Override
	public boolean invalidate() {
		if (functionBody != null) {
			functionBody = null;
			closure = null;
			return true;
		}
		return false;
	}

	@Override
	public KObject newInstance() throws ContextException {
		throw new ContextException();
	}

	@Override
	public KVariant FuncCall(KVariant[] args, KEnvironment env) throws KSException {
		KEnvironment tenv;
		if (args.length < off) {
			args = Arrays.copyOf(args, args.length);
		}
		for (int i = 0; i < args.length; i++) {
			if ((args[i] == null || args[i].getType().getType() == Void.class) && defargs[i] != null) {
				args[i] = defargs[i].eval(closure);
			} else if (args[i] == null) {
				args[i] = new KVariant();
			}
		}
		if (env != null) {
			tenv = new ArrayEnvironment(env, off, args);
		} else {
			tenv = new ArrayEnvironment(super.closure, off, args);
		}
		return functionBody.eval(tenv);
	}

	@Override
	public String getInstanceName() {
		return "Function";
	}
}
