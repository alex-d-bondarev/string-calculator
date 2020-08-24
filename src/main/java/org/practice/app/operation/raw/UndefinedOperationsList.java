package org.practice.app.operation.raw;

import org.practice.app.operation.Operation;

import java.util.ArrayList;
import java.util.List;

public class UndefinedOperationsList implements UndefinedOperation {
    private static final int START_POSITION = -1;
    private static final char VALUE = '_';
    private final List<Operation> operations;
    private int position = START_POSITION;

    public UndefinedOperationsList(List<Operation> operations){
        this.operations = operations;
    }

    public char getValue() {
        return VALUE;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<Operation> subList(int start, int end){
        return operations.subList(start, end);
    }

    public void toStart(){
        position = START_POSITION;
    }

    public void toEnd(){
        position = operations.size();
    }

    public int size(){
        return operations.size();
    }

    public boolean hasNext(){
        return position < operations.size() - 1;
    }

    public boolean hasPrevious(){
        return position > 0;
    }

    public Operation getCurrentOperation(){
        return operations.get(position);
    }

    public Operation get(int index){
        return operations.get(index);
    }

    public Operation next(){
        return operations.get(++position);
    }

    public Operation previous(){
        return operations.get(--position);
    }

    public Operation getPrevious(){
        return operations.get(position - 1);
    }

    public Operation getNext(){
        return operations.get(position + 1);
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int newPosition) {
        position = newPosition;
    }

    public UndefinedOperationsList replace(Operation newOperation, int from, int to){
        operations.subList(from, to).clear();
        operations.add(from, newOperation);
        position = from;
        return this;
    }

    public void add(int index, Operation newOperation){
        operations.add(index, newOperation);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UndefinedOperationsList)) {
            return false;
        }

        UndefinedOperationsList other = (UndefinedOperationsList) o;
        return operations.equals(other.getOperations());
    }

    @Override
    public String toString() {
        return operations.toString();
    }

    public UndefinedOperationsList getLeftSubList() {
        return new UndefinedOperationsList(new ArrayList<>(operations.subList(0, position)));
    }

    public UndefinedOperationsList getRightSubList() {
        return new UndefinedOperationsList(new ArrayList<>(operations.subList(position+1, operations.size())));
    }
}
