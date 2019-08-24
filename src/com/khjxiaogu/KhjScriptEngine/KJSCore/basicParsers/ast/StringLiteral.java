package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Token;

public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token t) { super(t); }
    public String value() { return token().getText(); }
}
