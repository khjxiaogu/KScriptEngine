package com.khjxiaogu.KhjScriptEngine.KJSCore.lexerTest;
import java.io.FileNotFoundException;

import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.*;

public class FileLexerRunner {
    public static void main(String[] args) throws ParseException {
        try {
            Lexer l = new Lexer(CodeDialog.file());
            for (Token t; (t = l.read()) != Token.EOF; )
                System.out.println("=> " + t.getText());
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
