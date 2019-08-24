package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers;
import static com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Parser.rule;

import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.Fun;

public class ClosureParser extends FuncParser {
    public ClosureParser() {
        primary.insertChoice(rule(Fun.class)
                                 .sep("function").ast(paramList).ast(block));
    }
}
