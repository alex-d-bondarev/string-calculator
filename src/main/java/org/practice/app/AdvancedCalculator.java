package org.practice.app;

import org.practice.app.expression.ExpressionInitializer;
import org.practice.app.expression.ExpressionValidator;
import org.practice.app.parser.CharsParser;

public class AdvancedCalculator {

    public CalculationResult evaluate(String expression) {
        expression = new ExpressionInitializer(expression).prepareExpression();
        ExpressionValidator validator = new ExpressionValidator(expression);

        if (validator.hasUnsupportedSymbols()) {
            return generateUnsupportedSymbolsMessage(expression);
        } else if (validator.hasUnbalancedParentheses()) {
            return generateUnbalancedBracketsMessage(expression);
        } else {
            return parseAndEvaluateExpression(expression);
        }
    }

    private CalculationResult parseAndEvaluateExpression(String expression){
        double result =
                new CharsParser(expression).convertCharsToUndefinedOperations().
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
