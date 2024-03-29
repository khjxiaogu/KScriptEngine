package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.KVariantReference;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.AssemblyException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KEnvironmentReference;

/**
 * @author khjxiaogu
 * @time 2020年2月16日 file:LiteralNode.java
 */
public class LiteralNode implements CodeNode, ASTParser, Assignable, Visitable {

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
		return env.getMemberByName(token, KEnvironment.MUSTEXIST, null);
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
	public KVariant assignAsVar(KEnvironment env, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		if (isLocal())
			return env.setMemberByNum(itoken, val, KEnvironment.DEFAULT);
		return env.setMemberByName(token, val, KEnvironment.DEFAULT);
	}
	@Override
	public KVariant assignOperation(KEnvironment env, KVariant val, AssignOperation op) throws KSException {
		if (isLocal())
			return env.doOperationByNum(op, itoken, val);
		return env.doOperationByName(op, token, val);
	}

	public boolean isLocal() {
		return itoken != -1;
	}

	@Override
	public void Visit(VisitContext context) {
		itoken = context.findLocal(token);
	}

	@Override
	public KVariantReference evalAsRef(KEnvironment env) throws KSException {
		// TODO Auto-generated method stub
		return new KEnvironmentReference(env,token,itoken);
	}


}
