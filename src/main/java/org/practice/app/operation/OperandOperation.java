package org.practice.app.operation;

public abstract class OperandOperation implements Operation {
    protected Operation left;
    protected Operation right;

    public OperandOperation(Operation left, Operation right){
        this.left = left;
        this.right = right;
    }
}
