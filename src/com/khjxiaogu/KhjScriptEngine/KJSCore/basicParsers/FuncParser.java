package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers;
import static com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Parser.rule;

import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.Arguments;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.ArrayLiteral;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.ArrayRef;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.DefStmnt;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.ParameterList;

public class FuncParser extends BasicParser {
    Parser param = rule().identifier(reserved);
    Parser params = rule(ParameterList.class)
                        .ast(param).repeat(rule().sep(",").ast(param));
    Parser paramList = rule().sep("(").maybe(params).sep(")");
    Parser def = rule(DefStmnt.class)
                     .sep("function").identifier(reserved).ast(paramList).ast(block);
    Parser args = rule(Arguments.class)
                      .ast(expr).repeat(rule().sep(",").ast(expr));
    Parser postfix = rule().sep("(").maybe(args).sep(")");
    Parser elements = rule(ArrayLiteral.class)
            .ast(expr).repeat(rule().sep(",").ast(expr));
    public FuncParser() {
        reserved.add(")");
        primary.repeat(postfix);
        simple.option(args);
        program.insertChoice(def);
        reserved.add("]");
        primary.insertChoice(rule().sep("[").maybe(elements).sep("]"));
        postfix.insertChoice(rule(ArrayRef.class).sep("[").ast(expr).sep("]"));
    }
    
}
