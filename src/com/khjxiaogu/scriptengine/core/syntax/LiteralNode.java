package com.khjxiaogu.scriptengine.core.syntax;

import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.AssemblyException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
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
			return env.getMemberByNum(itoken);
		return env.getMemberByNameEnsure(token);
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
			return env.setMemberByNum(itoken, val);
		return env.setMemberByNameEnsure(token, val);
	}

	@Override
	public KObject getObject(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		if (isLocal())
			return (KObject) env.getMemberByNum(itoken).toType("KObject");
		return (KObject) env.getMemberByNameEnsure(token).toType("KObject");
	}

	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		if (isLocal())
			return env.DoOperatonByNum(op, itoken, val);
		return env.DoOperatonByName(op, token, val);
	}

	@Override
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException {
		return env;
	}

	@Override
	public KVariant getPointing(KEnvironment env) {
		return new KVariant(getToken());
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
