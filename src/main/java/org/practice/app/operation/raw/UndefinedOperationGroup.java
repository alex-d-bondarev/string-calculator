package org.practice.app.operation.raw;

import org.practice.app.operation.Operation;

import java.util.List;

public class UndefinedOperationGroup implements UndefinedOperation {
    private final char VALUE = '_';
    private List<Operation> operations;
    private int position = 0;

    public UndefinedOperationGroup(List<Operation> operations){
        this.operations = operations;
    }

    public char getValue() {
        return VALUE;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public UndefinedOperationGroup toStart(){
        position = 0;
        return this;
    }

    public boolean hasNext(){
        return position < operations.size() - 1;
    }

    public boolean hasPrevious(){
        return position > 0;
    }

    public Operation current(){
        return operations.get(position);
    }

    public Operation next(){
        position++;
        return current();
    }

    public Operation getPrevious(){
        return operations.get(position - 1);
    }

    public int getPosition(){
        return position;
    }

    public UndefinedOperation replaceBetween(Operation newOperation, int start, int end){
        operations.subList(start, end).clear();
        operations.add(start, newOperation);
        return this;
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
