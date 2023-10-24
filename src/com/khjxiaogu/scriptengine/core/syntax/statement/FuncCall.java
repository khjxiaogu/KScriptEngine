package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.KVariantReference;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.VisitContext;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;

public class FuncCall extends SingleOperator implements ASTParser{
	protected CodeNode[] args;

	public FuncCall() {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		if(super.Child instanceof Assignable) {
			KVariantReference func =((Assignable)super.Child).evalAsRef(env);
			KVariant[] arg;
			if(args!=null) {
				arg= new KVariant[args.length];
				for (int i = 0; i < args.length; i++) {
					arg[i] = args[i].eval(env);
				}
			}else
				arg=new KVariant[0];
			return  func.funcCall(arg,KEnvironment.DEFAULT);

		}
		KVariant func = super.Child.eval(env);
		KVariant[] arg;
		if(args!=null) {
			arg= new KVariant[args.length];
			for (int i = 0; i < args.length; i++) {
				arg[i] = args[i].eval(env);
			}
		}else
			arg=new KVariant[0];
		KObject obj = func.asObject();
		return obj.funcCallByName(null, arg, null,KEnvironment.DEFAULT);
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
		if(args!=null)
			for (int i = 0; i < args.length; i++) {
				pardesc += args[i];
				if (i != args.length - 1) {
					pardesc += ",";
				}
			}
		return super.Child + "(" + pardesc + ")";
	}

	@Override
	public void Visit(VisitContext context) throws KSException {
		super.Visit(context);
		if(args!=null)
			for (CodeNode cn : args) {
				Visitable.Visit(cn, context);
			}
	}


}
