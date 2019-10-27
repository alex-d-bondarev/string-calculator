package org.practice.app.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RawExpressionParser {
    private String expression;
    private List<UndefinedOperationParser> undefinedOperationParsers;

    public RawExpressionParser(String expression) {
        this.expression = expression;
    }

    public RawExpressionParser parseToUndefinedOperations() {
        undefinedOperationParsers =
                expression.
                        chars().
                        mapToObj(ch -> new UndefinedOperationParser((char) ch)).
                        collect(Collectors.toCollection(LinkedList::new));

        return this;
    }

    public List<UndefinedOperationParser> getUndefinedOperationParsers(){
        return undefinedOperationParsers;
    }
}
