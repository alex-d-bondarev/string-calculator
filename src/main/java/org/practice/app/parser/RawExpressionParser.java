package org.practice.app.parser;

import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RawExpressionParser {
    private String expression;
    private UndefinedOperationGroup undefinedOperationGroup;

    public RawExpressionParser(String expression) {
        this.expression = expression;
    }

    public OperationParser parseToOperations() {
        undefinedOperationGroup =
                new UndefinedOperationGroup(
                        expression.
                                chars().
                                mapToObj(ch -> new SingleUndefinedOperation((char) ch)).
                                collect(Collectors.toCollection(ArrayList::new)));

        return new OperationParser(undefinedOperationGroup);
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }
}
