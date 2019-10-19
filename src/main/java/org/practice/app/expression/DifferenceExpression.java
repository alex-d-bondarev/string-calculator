package org.practice.app.expression;

public class DifferenceExpression extends OperandExpression {
    public DifferenceExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return left.evaluate() - right.evaluate();
    }
}
