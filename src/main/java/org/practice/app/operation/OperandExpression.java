package org.practice.app.operation;

public abstract class OperandExpression implements Expression {
    protected Expression left;
    protected Expression right;

    public OperandExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }
}