package org.practice.app;

import org.practice.app.expression.ExpressionInitializer;
import org.practice.app.expression.ExpressionValidator;

public class AdvancedCalculator {

    public CalculationResult evaluate(String expression) {
        ExpressionInitializer initializer = new ExpressionInitializer(expression);
        ExpressionValidator validator = initializer.initializeValidator();

        if (validator.hasUnsupportedSymbols()) {
            return generateUnsupportedSymbolsMessage(expression);
        } else if (validator.hasUnbalancedParentheses()) {
            return generateUnbalancedBracketsMessage(expression);
        } else {
            return parseAndEvaluateExpression(initializer);
        }
    }

    private CalculationResult parseAndEvaluateExpression(ExpressionInitializer initializer){
        double result =
                initializer.convertCharsToUndefinedOperations().
                        parseNegativeNumbers().parsePositiveNumbers().
                        getPriorityOperandsParser().parsePriorityOperands().
                        getParenthesisParser().parseParenthesis().
                        getDefinedOperationParser().parseToDefinedOperation().
                        evaluate();

        return new CalculationResult(Double.toString(result));
    }

    private CalculationResult generateUnsupportedSymbolsMessage(String expression) {
        String message = "Given expression '%s' contains unexpected symbols.";
        return new CalculationResult(String.format(message, expression));
    }

    private CalculationResult generateUnbalancedBracketsMessage(String expression) {
        String message = "Given expression '%s' has unbalanced brackets.";
        return new CalculationResult(String.format(message, expression));
    }
}
