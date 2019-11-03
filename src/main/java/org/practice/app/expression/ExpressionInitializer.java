package org.practice.app.expression;

import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;
import org.practice.app.parser.NumberParser;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ExpressionInitializer {
    private String expression;

    public ExpressionInitializer(String expression){
        this.expression = expression;
    }

    public ExpressionInitializer removeExtraSpaces(){
        expression = expression.replaceAll(" ", "");
        return this;
    }

    public ExpressionInitializer replaceBracketsWithParenthesis() {
        expression = expression.
                replaceAll("[{\\[]", "(").
                replaceAll("[]}]", ")");
        return this;
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

    public String getExpression() {
        return expression;
    }

    public ExpressionValidator getValidator(){
        return new ExpressionValidator(expression);
    }

    public ExpressionValidator initializeValidator(){
        return this.removeExtraSpaces().
                replaceBracketsWithParenthesis().
                getValidator();
    }
}
