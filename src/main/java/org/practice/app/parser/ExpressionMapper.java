package org.practice.app.parser;

import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;
import org.practice.app.parser.number.NegativeNumberParser;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ExpressionMapper {
    private final String expression;

    public ExpressionMapper(String expression) {
        this.expression = expression;
    }

    public NegativeNumberParser mapToUndefinedOperations() {
        UndefinedOperationGroup undefinedOperationGroup =
                new UndefinedOperationGroup(
                        expression
                                .chars()
                                .mapToObj(ch -> new SingleUndefinedOperation((char) ch))
                                .collect(Collectors.toCollection(ArrayList::new)));

        return new NegativeNumberParser(undefinedOperationGroup);
    }
}
