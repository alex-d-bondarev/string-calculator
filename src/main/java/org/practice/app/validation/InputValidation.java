package org.practice.app.validation;

public class InputValidation {

    public static boolean hasExtraSymbols(String expression){
        return !expression.matches("[()+\\-*/\\d]+");
    }
}
