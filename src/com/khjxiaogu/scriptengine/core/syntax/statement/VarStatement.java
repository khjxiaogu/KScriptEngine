package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.Closure;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.ObjectOperator;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

/**
 * @author khjxiaogu
 * @time 2020年3月2日
 *       project:khjScriptEngine
 */
public class VarStatement implements Visitable, ASTParser, ObjectOperator, CodeNode {

	/**
	 *
	 */
	//private ArgumentNode Child;
	private List<LiteralNode> nod=new ArrayList<>();
	private List<CodeNode> assignment=new ArrayList<>();
	public VarStatement() {
	}

	int itoken = -1;
	// List<String> tokens;
	String token;

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		
		for(int i=0;i<nod.size();i++) {
			sb.append(nod.get(i).toString());
			CodeNode cn=assignment.get(i);
			if(cn!=null) {
				sb.append("=");
				sb.append(cn.toString());
			}
			if(i<nod.size()-1)
				sb.append(",");
		}
		return "var " + sb.toString();
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		/*
		 * if (itoken != -1) {
		 * return env.setMemberByNum(itoken, new KVariant());
		 * }
		 * return env.setMemberByName(token, new KVariant());
		 */
		//if (env instanceof Closure)
		KVariant lr = null;
		for(int i=0;i<nod.size();i++) {
			LiteralNode ln=nod.get(i);
			CodeNode cn=assignment.get(i);
			if(cn!=null) {
				ln.assignAsVar(env,lr=cn.eval(env));
			}else
				ln.assignAsVar(env,lr=KVariant.valueOf());
		}
		return lr;
		//return Child.eval(env);
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		for(LiteralNode ln:nod) {
			parentMap.add(ln.toString());
			ln.Visit(parentMap);
		}
		for(CodeNode cn:assignment) {
			Visitable.Visit(cn, parentMap);
		}
	}
	private void putSub(ParseReader reader,CodeNode ln,CodeNode as) throws SyntaxError {
		if(ln instanceof LiteralNode)nod.add((LiteralNode) ln);else throw new SyntaxError("错误的var语句",reader);
		assignment.add(as);
	}
	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		//Child = (ArgumentNode) new ArgumentNode(';').parse(reader);
		StatementParser sp=new StatementParser();
		outer:
			while(true) {
				CodeNode ln=sp.parseUntil(reader,',','=',';');
				char c=reader.read();
				reader.eat();
				switch(c) {
				case ',':putSub(reader,ln,null);break;
				case ';':putSub(reader,ln,null);break outer;
				case '=':putSub(reader,ln,sp.parseUntil(reader,',',';'));if(reader.read()==';')break outer;break;
				default:throw new SyntaxError("错误的var语句，错误出现的"+c,reader);
				}
			}
		return this;
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public KVariant getPointing(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public void VisitAsChild(List<String> parentMap) throws KSException {
		for(LiteralNode ln:nod) {
			ln.VisitAsChild(parentMap);
		}
		for(CodeNode cn:assignment) {
			Visitable.Visit(cn, parentMap);
		}
	}

}
