package org.practice.app;

import org.practice.app.parser.ExpressionMapper;

public class AdvancedCalculator {

    private Expression expression;

    public CalculationResult evaluate(String expression) {
        this.expression = new Expression(expression);
        return getExpressionEvaluation();
    }

    private CalculationResult getExpressionEvaluation() {
        if (expression.hasUnsupportedSymbols()) {
            return getResultForUnsupportedSymbols(expression.getExpression());
        } else if (expression.hasUnbalancedParentheses()) {
            return getResultForUnbalancedBrackets(expression.getExpression());
        } else {
            return parseAndEvaluate(expression.getExpression());
        }
    }

    private CalculationResult getResultForUnsupportedSymbols(String expression) {
        String message = "Given expression '%s' contains unexpected symbols.";
        return new CalculationResult(String.format(message, expression));
    }

    private CalculationResult getResultForUnbalancedBrackets(String expression) {
        String message = "Given expression '%s' has unbalanced brackets.";
        return new CalculationResult(String.format(message, expression));
    }

    private CalculationResult parseAndEvaluate(String expression) {
        double result =
                new ExpressionMapper(expression)
                        .mapToUndefinedOperations()
                        .parseNegativeNumbers()
                        .parsePositiveNumbers()
                        .parsePriorityOperands()
                        .parseParenthesis()
                        .parseToDefinedOperation()
                        .evaluate();

        return new CalculationResult(Double.toString(result));
    }
}
