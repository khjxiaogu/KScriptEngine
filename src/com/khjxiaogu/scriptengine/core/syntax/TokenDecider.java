package com.khjxiaogu.scriptengine.core.syntax;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidCharacterException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.syntax.operator.Operator;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Break;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Continue;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.If;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Return;
import com.khjxiaogu.scriptengine.core.syntax.operator.p00.Throw;
import com.khjxiaogu.scriptengine.core.syntax.operator.p01.Order;
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
import com.khjxiaogu.scriptengine.core.syntax.operator.p03.Var;
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
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.Parentness;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.SelfDecrementRight;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.SelfIncrementRight;
import com.khjxiaogu.scriptengine.core.syntax.operator.p15.TypeConvertion;
import com.khjxiaogu.scriptengine.core.syntax.statement.IfStatement;
import com.khjxiaogu.scriptengine.core.syntax.statement.WhileStatement;

/**
 * @author khjxiaogu
 * @time 2020年2月19日 file:TokenDecider.java
 */
public class TokenDecider implements ASTParser {

	/**
	 * 
	 */
	private CodeNode last = null;

	public TokenDecider() {
		// TODO Auto-generated constructor stub
	}

	public void reset() {
		last = null;
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		char c = reader.read();
		while (Character.isWhitespace(c)) {
			c = reader.eat();
		}
		if (c < '!') {
			throw new InvalidCharacterException(c);
		} else if (c < '0') {
			if (c == '"' || c == '\'') {
				return last = new StringNode().parse(reader);
			}
			return last = parseOperator(reader);
		} else if (c <= '9') {
			return last = new NumberNode().parse(reader);
		} else if (c < '@') {
			return last = parseOperator(reader);
		} else if (c <= 'Z') {
			return last = parseLiteral(reader);
		} else if (c <= '^') {
			return last = parseOperator(reader);
		} else {
			return last = parseLiteral(reader);
		}
	}

	public CodeNode parseOperator(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		Associative infer = Associative.RIGHT;
		char first = reader.read();
		char next = reader.eat();
		if (last == null || last instanceof Operator) {
			infer = Associative.LEFT;
		}
		// ! # $ % & ( ) * + , - . / : ; < = > ? [ \ ] ^ { | } ~
		switch (first) {
		case '!':
			if (infer == Associative.LEFT) {
				return new Not();
			} else if (next == '=') {
				if (reader.eat() == '=') {
					reader.eat();
					return new NotExactEquals();
				} else {
					return new NotEquals();
				}
			} else {
				return new EvalString();
			}
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
			} else {
				return new Mod();
			}
		case '&':
			if (next == '&') {
				if (reader.eat() == '=') {
					return new LogicalAndEqual();
				} else {
					return new LogicalAnd();
				}
			} else if (next == '=') {
				reader.eat();
				return new ByteAndEqual();
			} else {
				return new ByteAnd();
			}
		case '(':
			return new Parentness().parse(reader);
		case '*':
			if (infer == Associative.LEFT) {
				return new SetProperty();
			} else if (next == '=') {
				reader.eat();
				return new MultiplyEqual();
			} else {
				return new Multiply();
			}
		case '+':
			if (next == '+') {
				reader.eat();
				if (infer == Associative.LEFT) {
					return new SelfIncrementLeft();
				} else {
					return new SelfIncrementRight();
				}
			} else if (next == '=') {
				reader.eat();
				return new AddEqual();
			} else if (infer == Associative.LEFT) {
				return new Positive();
			} else {
				return new Add();
			}
		case ',':
			return new Order();
		case '-':
			if (next == '-') {
				reader.eat();
				if (infer == Associative.LEFT) {
					return new SelfDecrementLeft();
				} else {
					return new SelfDecrementRight();
				}
			} else if (next == '=') {
				reader.eat();
				return new MinusEqual();
			} else if (infer == Associative.LEFT) {
				return new Negative();
			} else {
				return new Minus();
			}
		case '.':
			if ('0' <= next && next <= '9') {
				reader.rewind('.');
				return new NumberNode().parse(reader);
			} else {
				return new Member();
			}
		case '/':
			if (next == '=') {
				reader.eat();
				return new DivideEqual();
			} else {
				return new Divide();
			}
		case ':':
			return new Equal();
		case '<':
			if (next == '<') {
				char ch = reader.eat();
				if (ch == '=') {
					reader.eat();
					return new LSHEqual();
				} else if (ch == '%') {
					return parseOctet(reader);
				} else {
					return new LeftShift();
				}
			} else if (next == '=') {
				reader.eat();
				return new LessOrEqualThan();
			} else if (next == '-' && reader.read(1) == '>') {
				reader.eat(2);
				return new Exchange();
			} else {
				return new LessThan();
			}
		case '=':
			if (next == '=') {
				char ch = reader.eat();
				if (ch == '=') {
					reader.eat();
					return new ExactEquals();
				} else {
					return new Equals();
				}
			} else if (next == '>') {
				reader.eat();
				return new Equal();
			} else {
				return new Equal();
			}
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
					} else {
						return new AlgebraicRightShift();
					}
				} else {
					return new RightShift();
				}
			} else if (next == '=') {
				reader.eat();
				return new GreaterOrEqualThan();
			} else {
				return new GreaterThan();
			}
		case '?':
			return new Condition().parse(reader);
		case '[':
			if (infer == Associative.LEFT) {
				return parseArray(reader);
			} else {
				return new GetMember();
			}
		case '\\':
			if (next == '=') {
				reader.eat();
				return new FloorDivideEqual();
			} else if (infer == Associative.LEFT) {
				return parseRegEx(reader);
			} else {
				return new FloorDivide();
			}
		case '^':
			if (next == '=') {
				reader.eat();
				return new ByteXorEqual();
			} else {
				return new ByteXor();
			}
		case '{':
			return new CodeBlock(CodeBlockAttribute.NORMAL).parse(reader);
		case '|':
			if (next == '|') {
				if (reader.eat() == '=') {
					return new LogicalOrEqual();
				} else {
					return new LogicalOr();
				}
			} else if (next == '=') {
				reader.eat();
				return new ByteOrEqual();
			} else {
				return new ByteOr();
			}
		case '~':
			return new ByteInvert();
		default:
			throw new SyntaxError("unexpected" + first);
		}
	}

	public CodeNode parseLiteral(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		char ch = reader.read();
		/*
		 **break *continue const catch class case
		 * 
		 * debugger default *delete do extends export
		 * 
		 * enum *else function finally *false for
		 * 
		 * *global getter goto *incontextof *Infinity
		 * 
		 * *invalidate *instanceof *isvalid import *int in
		 * 
		 * *if *NaN *null new *octet protected property
		 * 
		 * private public *return *real synchronized switch
		 * 
		 * static setter *string +super *typeof throw
		 * 
		 * +this *true try *void *var *while with
		 */
		do {
			sb.append(ch);
		} while (Character.isJavaIdentifierPart(ch = reader.eat()) && ch != '$' && ch != 0);
		String lite = sb.toString();
		if (lite.equals("break")) {
			return new Break();
		} else if (lite.equals("continue")) {
			return new Continue();
		} else if (lite.equals("delete")) {
			return new DeleteMember();
		} else if (lite.equals("false")) {
			return new NumberNode(0);
		} else if (lite.equals("incontextof")) {
			return new InContextOf();
		} else if (lite.equals("Infinity")) {
			return new NumberNode(Double.POSITIVE_INFINITY);
		} else if (lite.equals("invalidate")) {
			return new Invalidate();
		} else if (lite.equals("instanceof")) {
			return new InstanceOf();
		} else if (lite.equals("isvalid")) {
			return new IsValid();
		} else if (lite.equals("int")) {
			return new TypeConvertion("Integer");
		} else if (lite.equals("if")) {
			if(last==null)
				return new IfStatement().parse(reader);
			return new If();
		} else if (lite.equals("while")) {
			return new WhileStatement().parse(reader);
		} else if (lite.equals("NaN")) {
			return new NumberNode(Double.NaN);
		} else if (lite.equals("null")) {
			return new LiteralNode(null);
		} else if (lite.equals("return")) {
			return new Return();
		} else if (lite.equals("real")) {
			return new TypeConvertion("real");
		} else if (lite.equals("string")) {
			return new TypeConvertion("String");
		} else if (lite.equals("typeof")) {
			return new TypeOf();
		} else if (lite.equals("throw")) {
			return new Throw();
		} else if (lite.equals("true")) {
			return new NumberNode(1);
		} else if (lite.equals("void")) {
			return new NumberNode();
		} else if (lite.equals("var")) {
			return new Var();
		}
		return new LiteralNode(lite);
	}

	public CodeNode parseArray(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		char first = reader.read();
		return null;
	}

	public CodeNode parseDictionary(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		char first = reader.read();
		return null;
	}

	public CodeNode parseOctet(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		char first = reader.read();
		return null;
	}


	public CodeNode parseRegEx(ParseReader reader) throws KSException {
		// TODO Auto-generated method stub
		char first = reader.read();
		return null;
	}
}
