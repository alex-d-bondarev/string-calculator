package org.practice.app;

import org.practice.app.parser.ExpressionMapper;

public class StringCalculator {

    private Expression expression;

    public String evaluate(String expression) {
        this.expression = new Expression(expression);
        return getExpressionEvaluation();
    }

    private String getExpressionEvaluation() {
        if (expression.hasUnsupportedSymbols()) {
            return getResultForUnsupportedSymbols(expression.getExpression());
        } else if (expression.hasUnbalancedParentheses()) {
            return getResultForUnbalancedBrackets(expression.getExpression());
        } else {
            return parseAndEvaluate(expression.getExpression());
        }
    }

    private String getResultForUnsupportedSymbols(String expression) {
        String message = "Given expression '%s' contains unexpected symbols.";
        return String.format(message, expression);
    }

    private String getResultForUnbalancedBrackets(String expression) {
        String message = "Given expression '%s' has unbalanced parenthesis.";
        return String.format(message, expression);
    }

    private String parseAndEvaluate(String expression) {
        double result =
                new ExpressionMapper(expression)
                        .mapToUndefinedOperations()
                        .parseNegativeNumbers()
                        .parsePositiveNumbers()
                        .parsePriorityOperands()
                        .parseParenthesis()
                        .parseToDefinedOperation()
                        .evaluate();

        return Double.toString(result);
    }
}
