package org.practice.app.input;

public class InputValidation {

    private final String expression;

    public InputValidation(String expression){
        this.expression = expression;
    }

    public boolean hasExtraSymbols(){
        return !this.expression.matches("[()+\\-*/\\d]+");
    }

}
