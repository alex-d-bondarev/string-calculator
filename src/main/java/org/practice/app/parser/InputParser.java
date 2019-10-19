package org.practice.app.parser;

import org.practice.app.util.ParenthesisUtil;

public class InputParser {

    public static boolean hasNoExtraSymbols(String expression){
        return expression.matches("[()+\\-*/\\d]+");
    }

    public static boolean isParenthesesBalanced(String expression){
        return ParenthesisUtil.bracketsAreBalanced(expression.replaceAll("[^()]", ""));
    }

    public static String replaceBracketsWithParenthesis(String expression) {
        return expression.
                replaceAll("[{\\[]", "(").
                replaceAll("[]}]", ")");
    }

    public static String removeExtraSpaces(String expression) {
        return expression.replaceAll(" ", "");
    }
}
