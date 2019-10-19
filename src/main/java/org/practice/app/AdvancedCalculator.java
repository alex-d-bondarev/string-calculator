package org.practice.app;

import org.practice.app.parser.ExpressionParser;
import org.practice.app.parser.InputParser;
import org.practice.app.parser.ProcessorException;
import org.practice.app.util.ParenthesisUtil;

import static org.practice.app.parser.InputParser.removeExtraSpaces;
import static org.practice.app.parser.InputParser.replaceBracketsWithParenthesis;

public class AdvancedCalculator {

    public double calculate(String expression) throws ProcessorException {
        expression = replaceBracketsWithParenthesis(removeExtraSpaces(expression));

        if(!InputParser.isParenthesesBalanced(expression)){
            throw new ProcessorException("Processed expression '" + expression + "' has unbalanced brackets");
        } else if (!InputParser.hasNoExtraSymbols(expression)) {
            throw new ProcessorException("Processed expression '" + expression + "' contains unexpected symbols");
        }

        expression = ParenthesisUtil.addMissedParenthesis(expression);

        return ExpressionParser.parseExpression(expression).evaluate();
    }
}
