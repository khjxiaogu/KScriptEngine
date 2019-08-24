package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.ASTree;

public class EvaluationException extends RuntimeException {
    public EvaluationException(String m) { super(m); }
    public EvaluationException(String m, ASTree t) {
        super(m + " " + t.location());
    }
}
