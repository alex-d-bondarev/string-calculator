package org.practice.app;

import org.practice.app.expression.ExpressionInitializer;
import org.practice.app.expression.ExpressionValidator;
import org.practice.app.parser.ExpressionMapper;

public class AdvancedCalculator {

    public CalculationResult evaluate(String expression) {
        String preparedExpression = new ExpressionInitializer(expression).prepareExpression();
        ExpressionValidator validator = new ExpressionValidator(preparedExpression);

        return getExpressionResult(preparedExpression, validator);
    }

    private CalculationResult getExpressionResult(String preparedExpression, ExpressionValidator validator) {
        if (validator.hasUnsupportedSymbols()) {
            return generateUnsupportedSymbolsMessage(preparedExpression);
        } else if (validator.hasUnbalancedParentheses()) {
            return generateUnbalancedBracketsMessage(preparedExpression);
        } else {
            return parseAndEvaluate(preparedExpression);
        }
    }

    private CalculationResult generateUnsupportedSymbolsMessage(String expression) {
        String message = "Given expression '%s' contains unexpected symbols.";
        return new CalculationResult(String.format(message, expression));
    }

    private CalculationResult generateUnbalancedBracketsMessage(String expression) {
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
                        .
                        parseToDefinedOperation().
                        evaluate();

        return new CalculationResult(Double.toString(result));
    }
}
