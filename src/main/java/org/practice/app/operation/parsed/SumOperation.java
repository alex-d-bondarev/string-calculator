package org.practice.app.operation.parsed;

public class SumOperation extends OperandOperation {

    public SumOperation(Operation left, Operation right){
        super(left, right);
    }

    @Override
    public double evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
