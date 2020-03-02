package com.khjxiaogu.scriptengine.core.syntax;

import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:LiteralNode.java
 */
public class LiteralNode implements CodeNode, ASTParser, Assignable, MemberOperator {

	/**
	 * 
	 */
	String token;

	public LiteralNode() {
		// TODO Auto-generated constructor stub
	}

	public LiteralNode(String string) {
		// TODO Auto-generated constructor stub
		token = string;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return env.getMemberByName(token);
	}

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return token;
	}

	@Override
	public CodeNode parse(ParseReader reader) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public KVariant assign(KEnvironment env, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		return env.setMemberByNameEnsure(getToken(), val);
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return (KObject) env.getMemberByNameEnsure(getToken()).toType("KObject");
	}
	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		return env.DoOperatonByName(op, getToken(), val);
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		return env;
	}

	@Override
	public KVariant getPointing(KEnvironment env) {
		return new KVariant(getToken());
	}

}
