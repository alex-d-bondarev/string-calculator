package org.practice.app.operation.raw;


import org.practice.app.operation.Operation;

import java.util.List;

public class UndefinedOperationGroup implements UndefinedOperation {
    private final char VALUE = '_';
    private List<Operation> operations;

    public UndefinedOperationGroup(List<Operation> operations){
        this.operations = operations;
    }

    public char getValue() {
        return VALUE;
    }

    public List<Operation> getOperations() {
        return operations;
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

        return operations.equals(other.getOperations());
    }
}
