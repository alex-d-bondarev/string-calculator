package org.practice.app.operation.raw;


import java.util.List;

public class UndefinedOperationGroup implements UndefinedOperation {
    private final char VALUE = '_';
    private List<SingleUndefinedOperation> undefinedOperations;

    public UndefinedOperationGroup(List<SingleUndefinedOperation> operations){
        undefinedOperations = operations;
    }

    public char getValue() {
        return VALUE;
    }

    public List<SingleUndefinedOperation> getUndefinedOperations() {
        return undefinedOperations;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UndefinedOperationGroup)) {
            return false;
        }

        UndefinedOperationGroup other = (UndefinedOperationGroup) o;

        return undefinedOperations.equals(other.getUndefinedOperations());
    }
}
