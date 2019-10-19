package org.practice.app.expression;

public class SumExpression extends OperandExpression {

    public SumExpression(Expression left, Expression right){
        super(left, right);
    }

    @Override
    public double evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
