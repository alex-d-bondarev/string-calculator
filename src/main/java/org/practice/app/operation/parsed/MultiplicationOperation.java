package org.practice.app.operation.parsed;

public class MultiplicationOperation extends OperandOperation {
    public MultiplicationOperation(Operation left, Operation right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return left.evaluate() * right.evaluate();
    }
}
