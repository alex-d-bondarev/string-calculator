package org.practice.app.parser;

import org.practice.app.expression.*;
import org.practice.app.util.ParenthesisUtil;

/*
 * Credit for idea: https://www2.seas.gwu.edu/~simhaweb/cs1112/modules/module10/suppl/index.html
 * */
public class ExpressionParser {

    /**
     * We can parse from left to right. This will result into the following cases:
     * 1. expression like "10 - 2 + 4 - 5 + 6 = 13" will be calculated as "10 - (2 + (4 - (5 + 6))) = 15"
     * 2. expression like "10 - 2 - 4 + 5 - 6 = 3" will be calculated as "10 - (2 - (4 + (5 - 6))) = 11"
     *
     * There are 2 possible solutions:
     * 1. Update parsed results afterwards and invert all operands to the left of each difference operand
     * 2. Parse from right to left.
     *
     * Second option is easier and avoids spaghetti code. The parsed expressions will look like:
     * 1. expression like "10 - 2 + 4 - 5 + 6 = 13" will be calculated as "((((10 - 2) + 4) - 5) + 6) = 13"
     * 2. expression like "10 - 2 - 4 + 5 - 6 = 3" will be calculated as "((((10 - 2) - 4) + 5) - 6) = 3"
     *
     * @param expression string to parse
     * @return Expression tree that is ready for calculation
     */
    public static Expression parseExpression(String expression) {

        if (!expression.isEmpty()) {
            if (isSurroundedWithParenthesis(expression)) {
                expression = expression.substring(1, expression.length() - 1);
            }
        }

        if (isNumber(expression)) {
            return new NumberExpression(Double.parseDouble(expression));
        } else {
            int leftExpressionEnd = ParenthesisUtil.findStartIndexOfRightExpressionPart(expression);
            String leftExpression = expression.substring(0, leftExpressionEnd);
            char operand = expression.charAt(leftExpressionEnd);
            String rightExpression = expression.substring(leftExpressionEnd + 1);

            return getOperandExpression(operand, leftExpression, rightExpression);
        }
    }

    private static Expression getOperandExpression(char operand, String left, String right) {
        Expression leftExpression = parseExpression(left);
        Expression rightExpression = parseExpression(right);

        switch (operand) {
            case '+':
                return new SumExpression(leftExpression, rightExpression);
            case '-':
                return new DifferenceExpression(leftExpression, rightExpression);
            case '*':
                return new MultiplicationExpression(leftExpression, rightExpression);
            case '/':
                return new DivisionExpression(leftExpression, rightExpression);
            default:
                throw new RuntimeException("Unsupported operand " + operand);
        }
    }

    private static boolean isNumber(String expression) {
        return expression.matches("-?\\d+");
    }

    private static boolean isSurroundedWithParenthesis(String expression){
        return expression.charAt(0) == '(' &&
                expression.charAt(expression.length() - 1) == ')' &&
                InputParser.isParenthesesBalanced(expression.substring(1, expression.length() -1));
    }
}
