package org.practice.app.expression;

public class NumberExpression implements Expression {

    private double number;

    public NumberExpression(double number){
        this.number = number;
    }

    @Override
    public double evaluate() {
        return number;
    }
}
