package org.practice.app;

import org.practice.app.parser.parenthesis.Parenthesis;

import java.util.Stack;

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
        return !parenthesisAreBalanced();
    }

    public boolean parenthesisAreBalanced() {
        String parenthesisOnly = expression.replaceAll("[^()]", "");
        boolean balanced = true;
        Stack<Character> charStack = new Stack<>();

        for (int i = 0; i < parenthesisOnly.length() && balanced; i++) {
            char nextChar = parenthesisOnly.charAt(i);

            if (nextChar == Parenthesis.OPENING) {
                charStack.push(nextChar);
            } else if (!charStack.isEmpty() && nextChar == Parenthesis.CLOSING) {
                charStack.pop();
            } else {
                balanced = false;
            }
        }

        return charStack.isEmpty() && balanced;
    }

    public String getExpression(){
        return expression;
    }
}
