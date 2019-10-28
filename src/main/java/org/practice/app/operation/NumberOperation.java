package org.practice.app.operation;

public class NumberOperation implements Operation {

    private double number;

    public NumberOperation(double number){
        this.number = number;
    }

    @Override
    public double evaluate() {
        return number;
    }
}
