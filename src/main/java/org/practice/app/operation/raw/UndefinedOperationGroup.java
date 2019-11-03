package org.practice.app.operation.raw;

import org.practice.app.operation.Operation;

import java.util.ArrayList;
import java.util.List;

public class UndefinedOperationGroup implements UndefinedOperation {
    private static final int START_POSITION = -1;
    private static final char VALUE = '_';
    private List<Operation> operations;
    private int position = START_POSITION;

    public UndefinedOperationGroup(List<Operation> operations){
        this.operations = operations;
    }

    public char getValue() {
        return VALUE;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void toStart(){
        position = START_POSITION;
    }

    public void toEnd(){
        position = operations.size();
    }

    public int getSize(){
        return operations.size();
    }

    public boolean hasNext(){
        return position < operations.size() - 1;
    }

    public boolean hasPrevious(){
        return position > 0;
    }

    public Operation next(){
        return operations.get(++position);
    }

    public Operation getPrevious(){
        return operations.get(position - 1);
    }

    public Operation getNext(){
        return operations.get(position + 1);
    }

    public Operation previous(){
        return operations.get(--position);
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int newPosition) {
        position = newPosition;
    }

    public UndefinedOperationGroup replaceBetween(Operation newOperation, int start, int end){
        operations.subList(start, end).clear();
        operations.add(start, newOperation);
        position = ++start;
        return this;
    }

    public UndefinedOperationGroup addOperationTo(Operation newOperation, int index){
        operations.add(index, newOperation);
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

    @Override
    public String toString() {
        return operations.toString();
    }

    public UndefinedOperationGroup getLeftSubGroup() {
        return new UndefinedOperationGroup(new ArrayList<>(operations.subList(0, position)));
    }

    public UndefinedOperationGroup getRightSubGroup() {
        return new UndefinedOperationGroup(new ArrayList<>(operations.subList(position+1, operations.size())));
    }
}
