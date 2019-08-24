package com.khjxiaogu.KhjScriptEngine.KJSCore.treeTest;

import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.BasicParser;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.CodeDialog;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Lexer;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ParseException;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Token;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.ASTree;


public class ParserRunner {
    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        BasicParser bp = new BasicParser();
        while (l.peek(0) != Token.EOF) {
            ASTree ast = bp.parse(l);
            System.out.println("=> " + ast.toString());
        }
    }
}
