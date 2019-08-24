package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers;
import static com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Parser.rule;

import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.*;

public class TypedParser extends FuncParser {
    Parser typeTag = rule(TypeTag.class).sep(":").identifier(reserved);
    Parser variable = rule(VarStmnt.class)
                          .sep("var").identifier(reserved).maybe(typeTag)
                          .sep("=").ast(expr);
    public TypedParser() {
        reserved.add(":");
        param.maybe(typeTag);
        def.reset().sep("function").identifier(reserved).ast(paramList)
                   .maybe(typeTag).ast(block);
        statement.insertChoice(variable);
    }
}
