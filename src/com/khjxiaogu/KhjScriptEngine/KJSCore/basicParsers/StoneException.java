package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.ASTree;

public class StoneException extends RuntimeException {
    public StoneException(String m) { super(m); }
    public StoneException(String m, ASTree t) {
        super(m + " " + t.location());
    }
}
