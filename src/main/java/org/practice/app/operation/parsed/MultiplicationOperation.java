package org.practice.app.operation.parsed;

public class MultiplicationOperation extends OperandOperation {
    public MultiplicationOperation(DefinedOperation left, DefinedOperation right) {
        super(left, right);
    }

    @Override
    public double evaluate() {
        return left.evaluate() * right.evaluate();
    }

    @Override
    public String toString() {
        return left + "*" + right;
    }
}
