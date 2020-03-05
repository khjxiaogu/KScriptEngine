package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.ScriptFunctionClosure;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.BlockClosure;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.Equal;

public class FunctionStatement implements BlockClosure,MemberOperator {
	String name;
	int itoken=-1;
	String[] argnames;
	CodeNode[] defargs;
	CodeNode body;
	int off;
	public FunctionStatement() {
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		if(itoken != -1) {
			env.setMemberByNum(itoken,new KVariant(new ScriptFunctionClosure(env, (CodeBlock) body,off, defargs)));
			return null;
		}else
		if(name != null) {
			env.setMemberByName(name,new KVariant(new ScriptFunctionClosure(env, (CodeBlock) body,off, defargs)));
			return null;
		}else {
			return new KVariant(new ScriptFunctionClosure(env, (CodeBlock) body,off, defargs));
		}
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser p=new StatementParser();
		char c=reader.eatAll();
		if(c!='(') {
			//reader.eat();
			CodeNode cn=p.parseUntil(reader,'(');
			if(!(cn instanceof LiteralNode))
				throw new SyntaxError("函数表达式有误");
			else
				name=((LiteralNode) cn).getToken();
		}
		reader.eat();
		ArgumentNode an=new ArgumentNode('(');
		an.parse(reader);
		List<CodeNode> cns=an.getAll();
		if(cns.size()!=0) {
			argnames=new String[cns.size()];
			defargs=new CodeNode[cns.size()];
			for(int i=0;i<cns.size();i++) {
				CodeNode cur=cns.get(i);
				if(cur instanceof LiteralNode) {
					argnames[i]=((LiteralNode) cur).getToken();
				}else if(cur instanceof Equal) {
					LiteralNode cr=((Equal) cur).getAssignToken();
					if(cr==null)throw new SyntaxError("函数表达式有误");
					argnames[i]=cr.getToken();
					defargs[i]=((Equal) cur).getAssignExpression();
				}
				if(argnames[i]==null)
					throw new SyntaxError("函数表达式有误");
			}
		}
		c=reader.eatAll();
		if(c=='{') {
			c = reader.eat();
			body=new CodeBlock(CodeBlockAttribute.RETURNABLE).parse(reader);
		}
		return this;
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		List<String>allnodes=new ArrayList<String>(parentMap);
		off=parentMap.size();
		allnodes.addAll(Arrays.asList(argnames));
		Visitable.Visit(body,allnodes);
		if(name!=null) {
			parentMap.add(name);
			itoken=parentMap.size()-1;
		}
	}

	@Override
	public String toString() {
		String pardesc="";
		for(int i=0;i<argnames.length;i++) {
			pardesc+=argnames[i]+"[%"+(i+off)+"]";
			if(defargs[i]!=null) {
				pardesc+="="+defargs[i].toString();
			}
			if(i!=argnames.length-1)
				pardesc+=",";
		}
		if(itoken==-1)
		return "function "+name+"("+pardesc+")"+body.toString();
		else
			return "function "+name+"[%"+itoken+"]"+"("+pardesc+")"+body.toString();
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}

	@Override
	public KVariant assign(KEnvironment env, KVariant val) throws KSException {
		return null;
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		return null;
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
	}

}
