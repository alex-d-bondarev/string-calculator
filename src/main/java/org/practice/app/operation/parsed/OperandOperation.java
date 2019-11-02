package org.practice.app.operation.parsed;

public abstract class OperandOperation implements DefinedOperation {
    protected DefinedOperation left;
    protected DefinedOperation right;

    public OperandOperation(DefinedOperation left, DefinedOperation right){
        this.left = left;
        this.right = right;
    }
}
