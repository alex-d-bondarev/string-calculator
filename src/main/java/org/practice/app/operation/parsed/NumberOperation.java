package org.practice.app.operation.parsed;

public class NumberOperation implements DefinedOperation {

    private double number;

    public NumberOperation(double number){
        this.number = number;
    }

    @Override
    public double evaluate() {
        return number;
    }
}
