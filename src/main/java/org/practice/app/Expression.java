package org.practice.app;

import org.practice.app.parser.parenthesis.ParenthesisUtil;

public class Expression {
    private String expression;

    public Expression(String expression) {
        this.expression = expression;
        removeAllSpacesFrom();
        replaceBracketsWithParenthesisFrom();
    }

    private void removeAllSpacesFrom() {
        expression = expression.replaceAll(" ", "");
    }

    private void replaceBracketsWithParenthesisFrom() {
        expression = expression.
                replaceAll("[{\\[]", "(").
                replaceAll("[]}]", ")");
    }

    public boolean hasUnsupportedSymbols() {
        return !expression.matches("[()+\\-*/\\d]+");
    }

    public boolean hasUnbalancedParentheses() {
        return !ParenthesisUtil.parenthesisAreBalanced(
                expression.replaceAll("[^()]", ""));
    }

    public String getExpression(){
        return expression;
    }
}
