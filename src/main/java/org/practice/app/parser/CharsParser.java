package org.practice.app.parser;

import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CharsParser {
    private final String expression;

    public CharsParser(String expression) {
        this.expression = expression;
    }

    public NumberParser convertCharsToUndefinedOperations() {
        UndefinedOperationGroup undefinedOperationGroup =
                new UndefinedOperationGroup(
                        expression.
                                chars().
                                mapToObj(ch -> new SingleUndefinedOperation((char) ch)).
                                collect(Collectors.toCollection(ArrayList::new)));

        return new NumberParser(undefinedOperationGroup);
    }
}
