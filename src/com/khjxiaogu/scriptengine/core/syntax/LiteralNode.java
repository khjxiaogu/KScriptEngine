package com.khjxiaogu.scriptengine.core.syntax;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.AssemblyException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:LiteralNode.java
 */
public class LiteralNode implements CodeNode, ASTParser, Assignable, MemberOperator, Visitable {

	/**
	 *
	 */
	String token;
	int itoken = -1;

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
		if (isLocal())
			return env.getMemberByNum(itoken, KEnvironment.MUSTEXIST);
		return env.getMemberByName(token, KEnvironment.MUSTEXIST);
	}

	public String getToken() {
		return token;
	}

	public int getLocalToken() throws AssemblyException {
		if (isLocal())
			return itoken;
		else
			throw new AssemblyException("使用了未定义的寄存器");
	}

	@Override
	public String toString() {
		if (itoken != -1)
			return "%" + itoken;
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
		if (isLocal())
			return env.setMemberByNum(itoken, val, KEnvironment.MUSTEXIST);
		return env.setMemberByName(token, val, KEnvironment.MUSTEXIST);
	}
	public KVariant assignAsVar(KEnvironment env, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		if (isLocal())
			return env.setMemberByNum(itoken, val, KEnvironment.DEFAULT);
		return env.setMemberByName(token, val, KEnvironment.DEFAULT);
	}
	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (env instanceof KObject)
			return (KObject) env;
		else
			return null;
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		if (isLocal())
			return env.doOperationByNum(op, itoken, val);
		return env.doOperationByName(op, token, val);
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		return env;
	}

	@Override
	public KVariant getPointing(KEnvironment env) {
		if (itoken != -1)
			return new KVariant(getToken());
		else
			return new KVariant(itoken);
	}

	public boolean isLocal() {
		return itoken != -1;
	}

	@Override
	public void Visit(List<String> parentMap) {
		itoken = parentMap.lastIndexOf(token);
	}

	@Override
	public void VisitAsChild(List<String> parentMap) {
		Visit(parentMap);
	}

}
