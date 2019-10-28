package org.practice.app.operation.parsed;

public class DifferenceOperation extends OperandOperation {
    public DifferenceOperation(Operation left, Operation right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return left.evaluate() - right.evaluate();
    }
}
