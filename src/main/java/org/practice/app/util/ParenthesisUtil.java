package org.practice.app.util;


import org.practice.app.model.CharStackParenthesisModel;

import java.util.*;

public class ParenthesisUtil {

    private static final List<Character> openingBrackets = Arrays.asList('{', '[', '(');
    private static final Map<Character, Character> bracketPairs = new HashMap<Character, Character>(){{
        put('}', '{');
        put(']', '[');
        put(')', '(');
    }};

    private static final CharStackParenthesisModel PARENTHESIS_TO_THE_LEFT =
            CharStackParenthesisModel.Builder.aModel().
                    withOpeningParenthesis(')').
                    withClosingParenthesis('(').
                    build();

    private static final CharStackParenthesisModel PARENTHESIS_TO_THE_RIGHT =
            CharStackParenthesisModel.Builder.aModel().
                    withOpeningParenthesis('(').
                    withClosingParenthesis(')').
                    build();

    private static final String PRIORITY_OPERANDS = "*/";

    public static int findStartIndexOfRightExpressionPart(String expression){
        return getOpeningParenthesisPosition(expression, PARENTHESIS_TO_THE_RIGHT);
    }

    public static String addMissedParenthesis(String expression) {
        while (needsParenthesis(expression)) {
            expression = addParenthesisAround(expression, positionToAddParenthesisAround(expression));
        }
        return expression;
    }

    public static boolean needsParenthesis(String expression) {
        boolean needsParenthesis = false;
        for (int i = 0; i < expression.length(); i++) {
            if ((PRIORITY_OPERANDS.indexOf(expression.charAt(i)) >= 0 ) &&
                    needsLeftParenthesis(expression.substring(0, i)) &&
                    needsRightParenthesis(expression.substring(i, expression.length() - 1))) {
                needsParenthesis = true;
            }
        }
        return needsParenthesis;
    }



    public static boolean bracketsAreBalanced(String line){
        boolean balanced = true;
        Stack<Character> charStack = new Stack<>();

        for (int i = 0; i < line.length() && balanced; i++) {
            char nextChar = line.charAt(i);

            if(openingBrackets.contains(nextChar)){
                charStack.push(nextChar);
            } else if (!charStack.isEmpty() && charStack.peek() == bracketPairs.get(nextChar)){
                charStack.pop();
            } else {
                balanced = false;
            }
        }

        return charStack.isEmpty() && balanced;
    }

    private static int positionToAddParenthesisAround(String expression) {
        int nowhere = -1;

        for (int i = 0; i < expression.length(); i++) {
            if (PRIORITY_OPERANDS.indexOf(expression.charAt(i)) >= 0 &&
                    needsLeftParenthesis(expression.substring(0, i)) &&
                    needsRightParenthesis(expression.substring(i))) {
                return i;
            }
        }
        return nowhere;
    }

    private static String addParenthesisAround(String expression, int position) {
        String leftPart = expression.substring(0, position);
        char middlePart = expression.charAt(position);
        String rightPart = expression.substring(position + 1);

        leftPart = new StringBuilder(leftPart).reverse().toString();
        leftPart = addParenthesisPerModel(leftPart, PARENTHESIS_TO_THE_LEFT);
        leftPart = new StringBuilder(leftPart).reverse().toString();

        rightPart = addParenthesisPerModel(rightPart, PARENTHESIS_TO_THE_RIGHT);

        return leftPart + middlePart + rightPart;
    }

    private static boolean needsLeftParenthesis(String expression) {
        String reversed = new StringBuilder(expression).reverse().toString();
        return needParenthesisPerModel(reversed, PARENTHESIS_TO_THE_LEFT);
    }

    private static boolean needsRightParenthesis(String expression) {
        return needParenthesisPerModel(expression, PARENTHESIS_TO_THE_RIGHT);
    }

    private static boolean needParenthesisPerModel(String expression, CharStackParenthesisModel parenthesisModel) {
        int positionForParenthesis = getClosingParenthesisPosition(expression, parenthesisModel);
        return positionForParenthesis  >= 0 &&
                expression.charAt(positionForParenthesis) != parenthesisModel.getClosingParenthesis();
    }

    private static String addParenthesisPerModel(String expression, CharStackParenthesisModel parenthesisModel) {
        int positionForParenthesis = getClosingParenthesisPosition(expression, parenthesisModel);

        if(positionForParenthesis < 0){
            return expression + parenthesisModel.getClosingParenthesis();
        } else {
            return expression.substring(0, positionForParenthesis) +
                    parenthesisModel.getClosingParenthesis() +
                    expression.substring(positionForParenthesis);
        }
    }

    /**
     * @param expression       string of expression to search from left to right
     * @param parenthesisModel model that contains description of expected parenthesises
     * @return the index of closing parenthesis or -1 if none was found
     */
    private static int getClosingParenthesisPosition(String expression, CharStackParenthesisModel parenthesisModel) {
        Stack<Character> charStack = new Stack<>();
        char nextChar;
        int positionNotFound = -1;

        for (int i = 0; i < expression.length(); i++) {
            nextChar = expression.charAt(i);

            if (nextChar == parenthesisModel.getOpeningParenthesis()) {
                charStack.push(nextChar);
            } else if (!charStack.isEmpty() && nextChar == parenthesisModel.getClosingParenthesis()) {
                charStack.pop();
            } else if (charStack.isEmpty() && !Character.isDigit(nextChar)) {
                return i;
            }
        }
        return positionNotFound;
    }

    /**
     * Similar as above but reversed.
     *
     * @param expression       string of expression to search from right to left
     * @param parenthesisModel model that contains description of expected parenthesises
     * @return the index of opening parenthesis or -1 if none was found
     */
    private static int getOpeningParenthesisPosition(String expression, CharStackParenthesisModel parenthesisModel) {
        Stack<Character> charStack = new Stack<>();
        char nextChar;
        int positionNotFound = -1;

        for (int i = expression.length() - 1; i >= 0; i--) {
            nextChar = expression.charAt(i);

            if (nextChar == parenthesisModel.getClosingParenthesis()) {
                charStack.push(nextChar);
            } else if (!charStack.isEmpty() && nextChar == parenthesisModel.getOpeningParenthesis()) {
                charStack.pop();
            } else if (charStack.isEmpty() && !Character.isDigit(nextChar)) {
                return i;
            }
        }
        return positionNotFound;
    }
}
