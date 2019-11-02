package org.practice.app;

import org.practice.app.expression.ExpressionInitializer;
import org.practice.app.expression.ExpressionValidator;
import org.practice.app.parser.ExpressionParser;
import org.practice.app.parser.InputParser;
import org.practice.app.parser.ParenthesisParser;
import org.practice.app.parser.ProcessorException;
import org.practice.app.util.ParenthesisUtil;

import static org.practice.app.parser.InputParser.removeExtraSpaces;
import static org.practice.app.parser.InputParser.replaceBracketsWithParenthesis;

public class AdvancedCalculator {

    private static final String HAS_UNSUPPORTED_SYMBOLS = "Given expression '%s' contains unexpected symbols.";
    private static final String HAS_UNBALANCED_BRACKETS = "Given expression '%s' has unbalanced brackets.";

    @Deprecated
    public double calculate(String expression) throws ProcessorException {


        expression = replaceBracketsWithParenthesis(removeExtraSpaces(expression));

        if (!InputParser.isParenthesesBalanced(expression)) {
            throw new ProcessorException("Processed operation '" + expression + "' has unbalanced brackets");
        } else if (!InputParser.hasNoExtraSymbols(expression)) {
            throw new ProcessorException("Processed operation '" + expression + "' contains unexpected symbols");
        }

        expression = ParenthesisUtil.addMissedParenthesis(expression);

        return ExpressionParser.parseExpression(expression).evaluate();
    }

    public CalculationResult evaluate(String expression) {
        ExpressionInitializer initializer = new ExpressionInitializer(expression);
        ExpressionValidator validator = initializer.initializeValidator();

        if (validator.hasUnsupportedSymbols()) {
            return getUnsupportedSymbolsCalculationResultFrom(expression);
        } else if (validator.hasUnbalancedParentheses()) {
            return getUnbalancedBracketsCalculationResultFrom(expression);
        } else {

            ParenthesisParser parser =
                    initializer.getRawExpressionParser().
                            parseToOperations().parseNegativeNumbers().parsePositiveNumbers().
                            getPriorityOperandsParser().parsePriorityOperands().
                            getParenthesisParser().parseParenthesis();
            // This is a temporary stub
            return null;
        }
    }

    private CalculationResult getUnsupportedSymbolsCalculationResultFrom(String expression) {
        return new CalculationResult(String.format(HAS_UNSUPPORTED_SYMBOLS, expression));
    }

    private CalculationResult getUnbalancedBracketsCalculationResultFrom(String expression) {
        return new CalculationResult(String.format(HAS_UNBALANCED_BRACKETS, expression));
    }
}
