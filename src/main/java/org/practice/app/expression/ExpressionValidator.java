package org.practice.app.expression;

import org.practice.app.util.ParenthesisUtil;

public class ExpressionValidator {

    private final String expression;

    public ExpressionValidator(String expression) {
        this.expression = expression;
    }

    public boolean hasUnsupportedSymbols() {
        return !expression.matches("[()+\\-*/\\d]+");
    }

    public boolean hasUnbalancedParentheses() {
        return !ParenthesisUtil.bracketsAreBalanced(
                expression.replaceAll("[^()]", ""));
    }
}
