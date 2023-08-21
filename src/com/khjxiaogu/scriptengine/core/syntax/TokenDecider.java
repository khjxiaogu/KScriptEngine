package com.khjxiaogu.scriptengine.core.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidCharacterException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.object.GlobalEnvironment;
import com.khjxiaogu.scriptengine.core.object.KOctet;
import com.khjxiaogu.scriptengine.core.object.internal.ArrayNode;
import com.khjxiaogu.scriptengine.core.object.internal.DictionaryNode;
import com.khjxiaogu.scriptengine.core.object.internal.ObjectArray;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.block.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.Operator;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Break;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Case;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Continue;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.If;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Return;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Throw;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.ARSHEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.AddEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.ByteAndEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.ByteOrEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.ByteXorEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.DivideEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.Equal;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.Exchange;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.FloorDivideEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.LSHEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.LogicalAndEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.LogicalOrEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.MinusEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.ModEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.MultiplyEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p02.RSHEqual;
import com.khjxiaogu.scriptengine.core.syntax.operator.p03.Condition;
import com.khjxiaogu.scriptengine.core.syntax.operator.p04.LogicalOr;
import com.khjxiaogu.scriptengine.core.syntax.operator.p05.LogicalAnd;
import com.khjxiaogu.scriptengine.core.syntax.operator.p06.ByteOr;
import com.khjxiaogu.scriptengine.core.syntax.operator.p07.ByteXor;
import com.khjxiaogu.scriptengine.core.syntax.operator.p08.ByteAnd;
import com.khjxiaogu.scriptengine.core.syntax.operator.p09.Equals;
import com.khjxiaogu.scriptengine.core.syntax.operator.p09.ExactEquals;
import com.khjxiaogu.scriptengine.core.syntax.operator.p09.NotEquals;
import com.khjxiaogu.scriptengine.core.syntax.operator.p09.NotExactEquals;
import com.khjxiaogu.scriptengine.core.syntax.operator.p10.GreaterOrEqualThan;
import com.khjxiaogu.scriptengine.core.syntax.operator.p10.GreaterThan;
import com.khjxiaogu.scriptengine.core.syntax.operator.p10.LessOrEqualThan;
import com.khjxiaogu.scriptengine.core.syntax.operator.p10.LessThan;
import com.khjxiaogu.scriptengine.core.syntax.operator.p11.AlgebraicRightShift;
import com.khjxiaogu.scriptengine.core.syntax.operator.p11.LeftShift;
import com.khjxiaogu.scriptengine.core.syntax.operator.p11.RightShift;
import com.khjxiaogu.scriptengine.core.syntax.operator.p12.Add;
import com.khjxiaogu.scriptengine.core.syntax.operator.p12.Minus;
import com.khjxiaogu.scriptengine.core.syntax.operator.p13.Divide;
import com.khjxiaogu.scriptengine.core.syntax.operator.p13.FloorDivide;
import com.khjxiaogu.scriptengine.core.syntax.operator.p13.Mod;
import com.khjxiaogu.scriptengine.core.syntax.operator.p13.Multiply;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.ByteInvert;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.DecodeChar;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.DeleteMember;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.EncodeChar;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.InstanceOf;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.Invalidate;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.IsValid;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.Negative;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.New;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.Not;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.Positive;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.SelfDecrementLeft;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.SelfIncrementLeft;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.SetProperty;
import com.khjxiaogu.scriptengine.core.syntax.operator.p14.TypeOf;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.EvalString;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.GetMember;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.InContextOf;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.Member;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.SelfDecrementRight;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.SelfIncrementRight;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.TypeConversion;
import com.khjxiaogu.scriptengine.core.syntax.statement.ArgumentNode;
import com.khjxiaogu.scriptengine.core.syntax.statement.DoWhileStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.ForStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.FuncCall;
import com.khjxiaogu.scriptengine.core.syntax.statement.FunctionStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.IfStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.Parentness;
import com.khjxiaogu.scriptengine.core.syntax.statement.SuperStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.SwitchStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.ThisStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.TryStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.VarStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.WhileStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.WithMember;
import com.khjxiaogu.scriptengine.core.syntax.statement.WithStatement;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:TokenDecider.java
 */
public class TokenDecider implements ASTParser {

	/**
	 *
	 */
	private CodeNode last = null;
	private static Map<String, LiteralFactory> identifiers = new HashMap<>();
	public TokenDecider() {
		// TODO Auto-generated constructor stub
	}

	public void reset() {
		last = null;
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		char c = reader.eatAllSpace();
		if (c < '!')
			throw new InvalidCharacterException(c);
		else if (c < '0') {
			if (c == '"' || c == '\'')
				return last = new StringNode().parse(reader);
			return last = parseOperator(reader);
		} else if (c <= '9')
			return last = new NumberNode().parse(reader);
		else if (c < '@')
			return last = parseOperator(reader);
		else if (c <= 'Z')
			return last = parseLiteral(reader);
		else if (c <= '^')
			return last = parseOperator(reader);
		else if (c < '{')
			return last = parseLiteral(reader);
		else if (c <= '~')
			return last = parseOperator(reader);
		else
			return last = parseLiteral(reader);
	}
	
	public CodeNode parseOperator(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		Associative infer = Associative.RIGHT;
		char first = reader.read();
		char next = reader.eat();
		if (last == null ||(last instanceof Operator&&(((Operator)last).getOperandCount()==2||((Operator)last).getAssociative()==Associative.LEFT))) {
			infer = Associative.LEFT;
		}

		// System.out.println(first);
		// ! # $ % & ( ) * + , - . / : ; < = > ? [ \ ] ^ { | } ~
		switch (first) {
		case '!':
			if (infer == Associative.LEFT)
				return new Not();
			else if (next == '=') {
				if (reader.eat('=')) {
					reader.eat();
					return new NotExactEquals();
				}
				return new NotEquals();
			} else
				return new EvalString();
		case '#':
			return new DecodeChar();
		case '$':
			return new EncodeChar();
		case '%':
			if (next == '=') {
				reader.eat();
				return new ModEqual();
			} else if (next == '[') {// for dictionary parsing
				reader.eat();
				return parseDictionary(reader);
			} else
				return new Mod();
		case '&':
			if (next == '&') {
				if (reader.eat('='))
					return new LogicalAndEqual();
				else
					return new LogicalAnd();
			} else if (next == '=') {
				reader.eat();
				return new ByteAndEqual();
			} else
				return new ByteAnd();
		case '(':
			if (last instanceof ObjectOperator)
				return new FuncCall().parse(reader);
			return new Parentness().parse(reader);
		case '*':
			if (infer == Associative.LEFT)
				return new SetProperty();
			else if (next == '=') {
				reader.eat();
				return new MultiplyEqual();
			} else
				return new Multiply();
		case '+':
			if (next == '+') {
				reader.eat();
				if (infer == Associative.LEFT)
					return new SelfIncrementLeft();
				else
					return new SelfIncrementRight();
			} else if (next == '=') {
				reader.eat();
				return new AddEqual();
			} else if (infer == Associative.LEFT)
				return new Positive();
			else
				return new Add();
		case ',':
			return new ArgumentNode(';').parse(reader);
		case '-':
			if (next == '-') {
				reader.eat();
				if (infer == Associative.LEFT)
					return new SelfDecrementLeft();
				else
					return new SelfDecrementRight();
			} else if (next == '=') {
				reader.eat();
				return new MinusEqual();
			} else if (infer == Associative.LEFT)
				return new Negative();
			else
				return new Minus();
		case '.':
			if ('0' <= next && next <= '9') {
				reader.rewind('.');
				return new NumberNode().parse(reader);
			} else if (last instanceof ObjectOperator)
				return new Member();
			else
				return new WithMember();
		case '/':
			if(next=='/') {
				reader.eatLine();
				return null;
			}
			if(next=='*') {
				reader.eat();
				SkipComment(reader);
				return null;
			}
			if(last==null) {
				return parseRegEx(reader);
			}
			if (next == '=') {
				reader.eat();
				return new DivideEqual();
			}
			return new Divide();
		case ':':
			return new Equal();
		case '<':
			if (next == '<') {
				if (reader.eat('=')) {
					reader.eat();
					return new LSHEqual();
				} else if (reader.eat('%'))
					return parseOctet(reader);
				else
					return new LeftShift();
			} else if (next == '=') {
				reader.eat();
				return new LessOrEqualThan();
			} else if (next == '-' && reader.read(1) == '>') {
				reader.eat(2);
				return new Exchange();
			} else
				return new LessThan();
		case '=':
			if (next == '=') {
				char ch = reader.eat();
				if (ch == '=') {
					reader.eat();
					return new ExactEquals();
				} else
					return new Equals();
			} else if (next == '>') {
				reader.eat();
				return new Equal();
			} else
				return new Equal();
		case '>':
			if (next == '>') {
				char ch = reader.eat();
				if (ch == '=') {
					reader.eat();
					return new RSHEqual();
				} else if (ch == '>') {
					char ch2 = reader.eat();
					if (ch2 == '=') {
						reader.eat();
						return new ARSHEqual();
					} else
						return new AlgebraicRightShift();
				} else
					return new RightShift();
			} else if (next == '=') {
				reader.eat();
				return new GreaterOrEqualThan();
			} else
				return new GreaterThan();
		case '?':
			return new Condition().parse(reader);
		case '[':
			if (infer == Associative.LEFT)
				return parseArray(reader);
			return new GetMember().parse(reader);
		case ']':return null;
		case '\\':
			if (next == '=') {
				reader.eat();
				return new FloorDivideEqual();
			} else if (infer == Associative.LEFT)
				return parseRegEx(reader);
			else
				return new FloorDivide();
		case '^':
			if (next == '=') {
				reader.eat();
				return new ByteXorEqual();
			} else
				return new ByteXor();
		case '{':
			return new CodeBlock(CodeBlockAttribute.NORMAL).parse(reader);
		case '|':
			if (next == '|') {
				if (reader.eat() == '=')
					return new LogicalOrEqual();
				else
					return new LogicalOr();
			} else if (next == '=') {
				reader.eat();
				return new ByteOrEqual();
			} else
				return new ByteOr();
		case '~':
			return new ByteInvert();
		default:
			// reader.rewind(first);
			// return null;
			throw new SyntaxError("unexpected" + first,reader);
		}
		// return null;
	}

	static {
		TokenDecider.identifiers.put("break", (reader, last) -> {
			return new Break();
		});
		TokenDecider.identifiers.put("continue", (reader, last) -> {
			return new Continue();
		});
		TokenDecider.identifiers.put("delete", (reader, last) -> {
			return new DeleteMember();
		});
		TokenDecider.identifiers.put("false", (reader, last) -> {
			return new NumberNode(0);
		});
		TokenDecider.identifiers.put("incontextof", (reader, last) -> {
			return new InContextOf();
		});
		TokenDecider.identifiers.put("Infinity", (reader, last) -> {
			return new NumberNode(Double.POSITIVE_INFINITY);
		});
		TokenDecider.identifiers.put("invalidate", (reader, last) -> {
			return new Invalidate();
		});
		TokenDecider.identifiers.put("instanceof", (reader, last) -> {
			return new InstanceOf();
		});
		TokenDecider.identifiers.put("isvalid", (reader, last) -> {
			return new IsValid();
		});
		TokenDecider.identifiers.put("int", (reader, last) -> {
			return new TypeConversion("Integer");
		});
		TokenDecider.identifiers.put("function", (reader, last) -> {
			return new FunctionStatement().parse(reader);
		});
		TokenDecider.identifiers.put("if", (reader, last) -> {
			if (last == null)
				return new IfStatement().parse(reader);
			return new If();
		});
		TokenDecider.identifiers.put("case", (reader, last) -> {
			return new Case().parse(reader);
		});
		TokenDecider.identifiers.put("for", (reader, last) -> {
			return new ForStatement().parse(reader);
		});
		TokenDecider.identifiers.put("switch", (reader, last) -> {
			return new SwitchStatement().parse(reader);
		});
		TokenDecider.identifiers.put("while", (reader, last) -> {
			return new WhileStatement().parse(reader);
		});
		TokenDecider.identifiers.put("NaN", (reader, last) -> {
			return new NumberNode(Double.NaN);
		});
		TokenDecider.identifiers.put("null", (reader, last) -> {
			return new LiteralNode(null);
		});
		TokenDecider.identifiers.put("return", (reader, last) -> {
			return new Return();
		});
		TokenDecider.identifiers.put("global", (reader, last) -> {
			return new ConstantNode(new KVariant(GlobalEnvironment.getGlobal()));
		});
		TokenDecider.identifiers.put("real", (reader, last) -> {
			return new TypeConversion("Real");
		});
		TokenDecider.identifiers.put("string", (reader, last) -> {
			return new TypeConversion("String");
		});
		TokenDecider.identifiers.put("typeof", (reader, last) -> {
			return new TypeOf();
		});
		TokenDecider.identifiers.put("throw", (reader, last) -> {
			return new Throw();
		});
		TokenDecider.identifiers.put("true", (reader, last) -> {
			return new NumberNode(1);
		});
		TokenDecider.identifiers.put("void", (reader, last) -> {
			return new NumberNode();
		});
		TokenDecider.identifiers.put("var", (reader, last) -> {
			return new VarStatement().parse(reader);
		});
		TokenDecider.identifiers.put("with", (reader, last) -> {
			return new WithStatement().parse(reader);
		});
		TokenDecider.identifiers.put("do", (reader, last) -> {
			return new DoWhileStatement().parse(reader);
		});
		TokenDecider.identifiers.put("this", (reader, last) -> {
			return new ThisStatement();
		});
		TokenDecider.identifiers.put("super", (reader, last) -> {
			return new SuperStatement();
		});
		TokenDecider.identifiers.put("new", (reader, last) -> {
			return new New().parse(reader);
		});
		TokenDecider.identifiers.put("try", (reader, last) -> {
			return new TryStatement().parse(reader);
		});
	}

	public CodeNode parseLiteral(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		/*
		 * TODO:
		 * const catch class
		 * debugger extends export
		 * enum finally
		 * getter goto
		 * import in
		 * new protected property
		 * private public synchronized
		 * static setter throw
		 */
		StringBuilder sb = new StringBuilder();
		char ch = reader.read();
		do {
			sb.append(ch);
		} while (Character.isJavaIdentifierPart(ch = reader.eat()) && ch != '$' && ch != 0);
		String lite = sb.toString();
		LiteralFactory cn = TokenDecider.identifiers.get(lite);
		if (cn != null)
			return cn.create(reader, last);
		return new LiteralNode(lite);
	}

	public CodeNode parseArray(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		return new ArrayNode().parse(reader);
	}

	public CodeNode parseDictionary(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		return new DictionaryNode().parse(reader);
	}
	public void SkipComment(ParseReader reader) throws KSException {
		char lst=0;
		while(true) {
			char cur=reader.eat();
			if(lst=='*'&&cur=='/')
				break;
			lst=cur;
		}
		reader.eat();
		return;
	}
	/**
	 * parse octet defination as <% HH HH HH%>
	 * @param reader
	 * @return
	 * @throws KSException
	 */
	public CodeNode parseOctet(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		char[] chs=new char[2];
		ArrayList<Byte> bytes=new ArrayList<>(20);
		while((chs[0] =reader.eat())!='%') {
			while(Character.isSpace(chs[0])) 
				chs[0]=reader.eat();
			chs[1]=reader.eat();
			bytes.add(Byte.parseByte(new String(chs),16));
		}
		byte[] bytearr=new byte[bytes.size()];
		for(int i=0;i<bytearr.length;++i) {
			bytearr[i]=bytes.get(i);
		}
		return new ConstantNode(new KVariant(new KOctet(bytearr)));
	}

	public CodeNode parseRegEx(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		char first = reader.read();
	//	StringBuilder RegEx
		return null;
	}
}

@FunctionalInterface
interface LiteralFactory {
	public CodeNode create(ParseReader reader, CodeNode last) throws KSException;
}