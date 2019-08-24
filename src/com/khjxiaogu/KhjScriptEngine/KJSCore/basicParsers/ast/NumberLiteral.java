package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Token;

public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) { super(t); }
    public int value() { return token().getNumber(); }
}
