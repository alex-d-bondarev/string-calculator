package org.practice.app;

import org.practice.app.expression.ExpressionInitializer;
import org.practice.app.expression.ExpressionValidator;
import org.practice.app.parser.ExpressionParser;
import org.practice.app.parser.InputParser;
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
        ExpressionValidator validator = initializeExpressionForValidator(expression);

        if (validator.hasExtraSymbols()) {
            return new CalculationResult(String.format(HAS_UNSUPPORTED_SYMBOLS, expression));
        } else if (validator.hasUnbalancedParentheses()) {
            return new CalculationResult(String.format(HAS_UNBALANCED_BRACKETS, expression));
        }

        // This is temporary stub
        return null;
    }

    private ExpressionValidator initializeExpressionForValidator(String expression) {
        return new ExpressionInitializer(expression).
                removeExtraSpaces().
                replaceBracketsWithParenthesis().
                getValidator();
    }
}
