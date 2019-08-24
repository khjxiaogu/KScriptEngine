package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast;
import java.util.List;

public class ArrayLiteral extends ASTList {
    public ArrayLiteral(List<ASTree> list) { super(list); }
    public int size() { return numChildren(); }
}
