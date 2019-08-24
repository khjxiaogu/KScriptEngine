package com.khjxiaogu.KhjScriptEngine.KJSCore.lexerTest;

import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.*;

public class LexerRunner {
    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        for (Token t; (t = l.read()) != Token.EOF; )
            System.out.println("=> " + t.getText());
    }
}
