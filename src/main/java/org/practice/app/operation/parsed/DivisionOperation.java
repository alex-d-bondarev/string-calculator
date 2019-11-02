package org.practice.app.operation.parsed;

public class DivisionOperation extends OperandOperation {
    public DivisionOperation(DefinedOperation left, DefinedOperation right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return left.evaluate() / right.evaluate();
    }
}
