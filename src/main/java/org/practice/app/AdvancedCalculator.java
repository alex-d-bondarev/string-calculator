package org.practice.app;

import org.practice.app.expression.ExpressionInitializer;
import org.practice.app.expression.ExpressionValidator;
import org.practice.app.operation.parsed.DefinedOperation;

public class AdvancedCalculator {

    private static final String HAS_UNSUPPORTED_SYMBOLS = "Given expression '%s' contains unexpected symbols.";
    private static final String HAS_UNBALANCED_BRACKETS = "Given expression '%s' has unbalanced brackets.";

    public CalculationResult evaluate(String expression) {
        ExpressionInitializer initializer = new ExpressionInitializer(expression);
        ExpressionValidator validator = initializer.initializeValidator();

        if (validator.hasUnsupportedSymbols()) {
            return getUnsupportedSymbolsCalculationResultFrom(expression);
        } else if (validator.hasUnbalancedParentheses()) {
            return getUnbalancedBracketsCalculationResultFrom(expression);
        } else {

            DefinedOperation definedOperation =
                    initializer.getRawExpressionParser().
                            parseToOperations().parseNegativeNumbers().parsePositiveNumbers().
                            getPriorityOperandsParser().parsePriorityOperands().
                            getParenthesisParser().parseParenthesis().
                            getDefinedOperationParser().parseToDefinedOperation();

            return new CalculationResult(Double.toString(definedOperation.evaluate()));
        }
    }

    private CalculationResult getUnsupportedSymbolsCalculationResultFrom(String expression) {
        return new CalculationResult(String.format(HAS_UNSUPPORTED_SYMBOLS, expression));
    }

    private CalculationResult getUnbalancedBracketsCalculationResultFrom(String expression) {
        return new CalculationResult(String.format(HAS_UNBALANCED_BRACKETS, expression));
    }
}
