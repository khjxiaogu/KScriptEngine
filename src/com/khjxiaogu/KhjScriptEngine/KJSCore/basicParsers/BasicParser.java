package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers;
import static com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Parser.rule;

import java.util.HashSet;

import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Parser.Operators;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.*;

public class BasicParser {
    HashSet<String> reserved = new HashSet<String>();
    Operators operators = new Operators();
    Parser expr0 = rule();
    Parser primary = rule(PrimaryExpr.class)
        .or(rule().sep("(").ast(expr0).sep(")"),
            rule().number(NumberLiteral.class),
            rule().identifier(Name.class, reserved),
            rule().string(StringLiteral.class));
    Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary),
                              primary);                               
    Parser expr = expr0.expression(BinaryExpr.class, factor, operators);

    Parser statement0 = rule();
    Parser block = rule(BlockStmnt.class)
        .sep("{").option(statement0)
        .repeat(rule().sep(";").option(statement0))
        .sep("}");
    Parser simple = rule(PrimaryExpr.class).ast(expr);
    Parser statement = statement0.or(
            rule(IfStmnt.class).sep("if").sep("(").ast(expr).sep(")").ast(block)
                               .option(rule().sep("else").ast(block)),
            rule(WhileStmnt.class).sep("while").sep("(").ast(expr).sep(")").ast(block),
            simple);

    Parser program = rule().or(statement, rule(NullStmnt.class))
                           .sep(";");

    public BasicParser() {
        reserved.add(";");
        reserved.add("}");
        reserved.add(Token.EOL);
        //优先级最低的赋值运算符
        operators.add("=", 1, Operators.RIGHT);
        operators.add("&=", 1, Operators.RIGHT);
        operators.add("|=", 1, Operators.RIGHT);
        operators.add("+=", 1, Operators.RIGHT);
        operators.add("%=", 1, Operators.RIGHT);
        operators.add("/=", 1, Operators.RIGHT);
        operators.add("\\=", 1, Operators.RIGHT);
        operators.add("*=", 1, Operators.RIGHT);
        operators.add("||=", 1, Operators.RIGHT);
        operators.add("&&=", 1, Operators.RIGHT);
        operators.add(">>=", 1, Operators.RIGHT);
        operators.add("<<=", 1, Operators.RIGHT);
        operators.add(">>>=", 1, Operators.RIGHT);
        //逻辑运算
        operators.add("||", 2, Operators.LEFT);
        operators.add("&&", 2, Operators.LEFT);
        //位运算
        operators.add("&", 3, Operators.LEFT);
        operators.add("|", 3, Operators.LEFT);
        operators.add("^", 3, Operators.LEFT);
        //等价
        operators.add("==", 4, Operators.LEFT);
        operators.add("!=", 4, Operators.LEFT);
        //关系
        operators.add(">", 5, Operators.LEFT);
        operators.add(">=", 5, Operators.LEFT);
        operators.add("<", 5, Operators.LEFT);
        operators.add("<=", 5, Operators.LEFT);
        operators.add("instanceof", 5, Operators.LEFT);
        //移位
        operators.add(">>", 6, Operators.LEFT);
        operators.add("<<", 6, Operators.LEFT);
        operators.add(">>>", 6, Operators.LEFT);
        //加减
        operators.add("+", 7, Operators.LEFT);
        operators.add("-", 7, Operators.LEFT);
        //乘除整除求余
        operators.add("*", 8, Operators.LEFT);
        operators.add("/", 8, Operators.LEFT);
        operators.add("\\", 8, Operators.LEFT);
        operators.add("%", 8, Operators.LEFT);
        //类型转换?
        operators.add("int", 9, Operators.RIGHT);
        operators.add("string", 9, Operators.RIGHT);
        operators.add("real", 9, Operators.RIGHT);
        //单目
        operators.add("++", 10, Operators.LEFT);
        operators.add("--", 10, Operators.LEFT);
        operators.add("!", 10, Operators.RIGHT);
        operators.add("~", 10, Operators.RIGHT);
        //其他
        operators.add("invalidate", 11, Operators.RIGHT);
        operators.add("delete", 11, Operators.RIGHT);
        operators.add("typeof", 11, Operators.RIGHT);
        operators.add("isvalid", 11, Operators.LEFT);
        
      //还有其他保留字
        operators.add("incontextof", 12, Operators.LEFT);
        
        
    }
    public ASTree parse(Lexer lexer) throws ParseException {
        return program.parse(lexer);
    }
}
