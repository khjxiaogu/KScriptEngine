package com.khjxiaogu.scriptengine.core.syntax.operator.p14;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.VisitContext;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.statement.FuncCall;

public class New extends FuncCall {
	CodeNode ncls=null;
	public New() {
	}
	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		KVariant objv = ncls.eval(env);
		KVariant[] arg;
		if(args!=null) {
			arg= new KVariant[args.length];
			for (int i = 0; i < args.length; i++) {
				arg[i] = args[i].eval(env);
			}
		}else
			arg=new KVariant[0];
		KObject obj = objv.asObject();
		KObject ni = obj.newInstance();
		ni.callConstructor(arg, ni);
		return KVariant.valueOf(ni);
	}
	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser parser=new StatementParser();
		CodeNode cn=parser.parseUntil(reader,'(');
		reader.eat();
		if(!(cn instanceof Assignable)) {
			throw new SyntaxError("错误的new语句",reader);
		}
		//reader.eatAllSpace();
		ncls= cn;
		super.parse(reader);
		return this;
	}
	@Override
	public void Visit(VisitContext context) throws KSException {
		super.Visit(context);
		Visitable.Visit(ncls,context);
		
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
		return "new "+ncls+ "(" + pardesc + ")";
	}
}
