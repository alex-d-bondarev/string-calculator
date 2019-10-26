package org.practice.app.validation;

public class InputValidation {

    private final String expression;

    public InputValidation(String expression){
        this.expression = expression;
    }

    public boolean hasExtraSymbols(){
        return !this.expression.matches("[()+\\-*/\\d]+");
    }

}
