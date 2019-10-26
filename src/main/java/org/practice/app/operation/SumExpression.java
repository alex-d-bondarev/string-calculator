package org.practice.app.operation;

public class SumExpression extends OperandExpression {

    public SumExpression(Expression left, Expression right){
        super(left, right);
    }

    @Override
    public double evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
