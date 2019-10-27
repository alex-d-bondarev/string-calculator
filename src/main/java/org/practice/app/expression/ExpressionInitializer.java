package org.practice.app.expression;

public class ExpressionInitializer {
    private String expression;

    public ExpressionInitializer(String expression){
        this.expression = expression;
    }

    public ExpressionInitializer removeExtraSpaces(){
        expression = expression.replaceAll(" ", "");
        return this;
    }

    public ExpressionInitializer replaceBracketsWithParenthesis() {
        expression = expression.
                replaceAll("[{\\[]", "(").
                replaceAll("[]}]", ")");
        return this;
    }

    public String getExpression() {
        return expression;
    }

    public ExpressionValidator getValidator(){
        return new ExpressionValidator(expression);
    }

    public ExpressionValidator initializeValidator(){
        return this.removeExtraSpaces().
                replaceBracketsWithParenthesis().
                getValidator();
    }
}
