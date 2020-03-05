package com.khjxiaogu.scriptengine.core.syntax.operator.p01;

import java.util.ArrayList;
import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.LiteralNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.SingleOperator;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.Equal;

public class ArgumentNode implements ASTParser,Visitable, CodeNode {
	private List<CodeNode> subnodes=new ArrayList<>(); 
	char first;
	public ArgumentNode(char first) {
		this.first=first;
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser parser = new StatementParser();
		while (true) {
			if (!reader.has()) {
				break;
			}
			char c = reader.read();
			//
			while (Character.isWhitespace(c)) {
				c = reader.eat();
			}
			if (!reader.has()) {
				break;
			}
			subnodes.add(parser.parseUntil(reader,',',';',')'));
			
			if(reader.read()==',') {
				reader.eat();
				continue;
			}
			if(reader.read()==';')
				if(first==';') {
					break;
				}else
					throw new SyntaxError("错误的;");
			if(reader.read()==')') {
				if(first=='(') {
					reader.eat();
				}
					break;
			}
		}
		return this;
	}
	
	@Override
	public String toString() {
		String ret="(";
		ret+=subnodes.get(0).toString();
		if(subnodes.size()>1)
		for(int i=1;i<subnodes.size();i++) {
			ret+=',';
			ret+=subnodes.get(i).toString();
		}
		return ret+")";
	}


	@Override
	public void Visit(List<String> parentMap) throws KSException {
		for(int i=0;i<subnodes.size();i++)
			Visitable.Visit(subnodes.get(i),parentMap);
	}

	public void VisitAsVar(List<String> parentMap) throws KSException {
		for(int i=0;i<subnodes.size();i++) {
			CodeNode cur=subnodes.get(i);
			//System.out.println(cur.getClass().getSimpleName());
			if(cur instanceof Equal) {
				LiteralNode tok=((Equal)cur).getAssignToken();
				if(tok!=null) {
					Visitable.Visit(((Equal)cur).getAssignExpression(),parentMap);
					parentMap.add(tok.getToken());
					Visitable.Visit(tok,parentMap);
				}
			}else if(cur instanceof LiteralNode){
				parentMap.add(((LiteralNode) cur).getToken());
				Visitable.Visit(cur, parentMap);
			}

			
		}
	}
	
	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		KVariant res=null;
		for(int i=0;i<subnodes.size();i++)
			res=subnodes.get(i).eval(env);
		return res;
	}

}
