package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.MemberOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.p01.ArgumentNode;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.Equal;

/**
 * @author khjxiaogu
 * @time 2020年3月2日
 *       project:khjScriptEngine
 */
public class VarStatement extends SingleOperator implements Visitable,ASTParser {

	/**
	 *
	 */
	public VarStatement() {
	}

	int itoken = -1;
	// List<String> tokens;
	String token;


	@Override
	public void setChildren(CodeNode... codeNodes) throws KSException {
		super.setChildren(codeNodes);
		if (super.Child != null && !(super.Child instanceof LiteralNode)&&!(super.Child instanceof ArgumentNode)&&!(super.Child instanceof Equal))
			throw new SyntaxError(super.Child.getClass().getSimpleName());
	}

	@Override
	public String toString() {
		if (itoken != -1)
			return "def"+super.Child.toString();
		return "var " + super.Child.toString();
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		/*if (itoken != -1) {
			return env.setMemberByNum(itoken, new KVariant());
		}
		return env.setMemberByName(token, new KVariant());*/
		return super.Child.eval(env);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public Associative getAssociative() {
		return Associative.LEFT;
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		if (Child instanceof LiteralNode) {
			parentMap.add(((LiteralNode) super.Child).getToken());
			itoken = parentMap.size() - 1;
		}else if(Child instanceof ArgumentNode) {
			((ArgumentNode) Child).VisitAsVar(parentMap);
		}else if(Child instanceof Equal) {
			LiteralNode cur=((Equal) super.Child).getAssignToken();
			Visitable.Visit(((Equal) super.Child).getAssignExpression(), parentMap);
			if(cur==null)
				throw new SyntaxError("错误的var语句");
			parentMap.add(cur.getToken());
			Visitable.Visit(cur, parentMap);
			itoken = parentMap.size() - 1;
		}
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		super.Child=new ArgumentNode(';').parse(reader);
		return this;
	}

}
