package org.practice.app.expression;

public class ExpressionInitializer {
    private final String expression;

    public ExpressionInitializer(String expression) {
        this.expression = expression;
    }

    public String prepareExpression() {
        String preparedExpression = removeAllSpacesFrom(expression);
        return replaceBracketsWithParenthesisFrom(preparedExpression);
    }

    private String removeAllSpacesFrom(String expression) {
        return expression.replaceAll(" ", "");
    }

    private String replaceBracketsWithParenthesisFrom(String expression) {
        return expression.
                replaceAll("[{\\[]", "(").
                replaceAll("[]}]", ")");
    }
}
