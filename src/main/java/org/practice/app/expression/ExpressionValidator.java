package org.practice.app.expression;

import org.practice.app.parser.parenthesis.ParenthesisUtil;

public class ExpressionValidator {

    private final String expression;

    public ExpressionValidator(String expression) {
        this.expression = expression;
    }

    public boolean hasUnsupportedSymbols() {
        return !expression.matches("[()+\\-*/\\d]+");
    }

    public boolean hasUnbalancedParentheses() {
        return !ParenthesisUtil.parenthesisAreBalanced(
                expression.replaceAll("[^()]", ""));
    }
}
