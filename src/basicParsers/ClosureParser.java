package basicParsers;
import static basicParsers.Parser.rule;

import basicParsers.ast.Fun;

public class ClosureParser extends FuncParser {
    public ClosureParser() {
        primary.insertChoice(rule(Fun.class)
                                 .sep("function").ast(paramList).ast(block));
    }
}
