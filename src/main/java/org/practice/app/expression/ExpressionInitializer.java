package org.practice.app.expression;

public class ExpressionInitializer {
    private final String expression;

    public ExpressionInitializer(String expression) {
        this.expression = expression;
    }

    public String prepareExpression() {
        return removeExtraSpaces().replaceBracketsWithParenthesis().getExpression();
    }

    private ExpressionInitializer removeExtraSpaces() {
        return new ExpressionInitializer(removeAllSpacesFrom(expression));
    }

    private String removeAllSpacesFrom(String expression) {
        return expression.replaceAll(" ", "");
    }

    private ExpressionInitializer replaceBracketsWithParenthesis() {
        return new ExpressionInitializer(replaceBracketsWithParenthesisFrom(expression));
    }

    private String replaceBracketsWithParenthesisFrom(String expression) {
        return expression.
                replaceAll("[{\\[]", "(").
                replaceAll("[]}]", ")");
    }

    private String getExpression() {
        return expression;
    }
}
