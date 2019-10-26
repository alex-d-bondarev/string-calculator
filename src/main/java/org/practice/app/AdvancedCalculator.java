package org.practice.app;

import org.practice.app.parser.ExpressionParser;
import org.practice.app.parser.InputParser;
import org.practice.app.parser.ProcessorException;
import org.practice.app.util.ParenthesisUtil;

import static org.practice.app.parser.InputParser.removeExtraSpaces;
import static org.practice.app.parser.InputParser.replaceBracketsWithParenthesis;
import static org.practice.app.validation.InputValidation.hasExtraSymbols;

public class AdvancedCalculator {

    private static final String HAS_UNSUPPORTED_SYMBOLS = "Given expression '%s' contains unexpected symbols.";

    @Deprecated
    public double calculate(String expression) throws ProcessorException {


        expression = replaceBracketsWithParenthesis(removeExtraSpaces(expression));

        if(!InputParser.isParenthesesBalanced(expression)){
            throw new ProcessorException("Processed operation '" + expression + "' has unbalanced brackets");
        } else if (!InputParser.hasNoExtraSymbols(expression)) {
            throw new ProcessorException("Processed operation '" + expression + "' contains unexpected symbols");
        }

        expression = ParenthesisUtil.addMissedParenthesis(expression);

        return ExpressionParser.parseExpression(expression).evaluate();
    }

    public CalculationResult evaluate(String expression) {
        if(hasExtraSymbols(expression)){
            return new CalculationResult(String.format(HAS_UNSUPPORTED_SYMBOLS, expression));
        }

        // This is temporary stub
        return null;
    }
}
