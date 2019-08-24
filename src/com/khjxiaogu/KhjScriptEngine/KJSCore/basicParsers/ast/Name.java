package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Token;

public class Name extends ASTLeaf {
    public Name(Token t) { super(t); }
    public String name() { return token().getText(); }
}
