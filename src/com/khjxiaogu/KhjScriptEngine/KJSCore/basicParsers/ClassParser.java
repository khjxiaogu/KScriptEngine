package com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers;
import static com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.Parser.rule;

import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.ClassBody;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.ClassStmnt;
import com.khjxiaogu.KhjScriptEngine.KJSCore.basicParsers.ast.Dot;

public class ClassParser extends ClosureParser {
    Parser member = rule().or(def, simple);
    Parser class_body = rule(ClassBody.class).sep("{").option(member)
                            .repeat(rule().sep(";").option(member))
                            .sep("}");
    Parser defclass = rule(ClassStmnt.class).sep("class").identifier(reserved)
                          .option(rule().sep("extends").identifier(reserved))
                          .ast(class_body);
    public ClassParser() {
        postfix.insertChoice(rule(Dot.class).sep(".").identifier(reserved));
        program.insertChoice(defclass);
    }
}
