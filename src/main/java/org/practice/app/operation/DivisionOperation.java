package org.practice.app.operation;

public class DivisionOperation extends OperandOperation {
    public DivisionOperation(Operation left, Operation right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return left.evaluate() / right.evaluate();
    }
}
