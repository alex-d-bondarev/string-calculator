package org.practice.app.util;

import java.util.*;

public class ParenthesisUtil {

    private static final char OPENING_PAREN = '(';
    private static final char CLOSING_PAREN = ')';

    /**
     * @param parenthesis string to verify
     * @return false if given parenthesis string contains symbols other than '(' and ')'
     *          also false if amount of closing and opening parenthesis is unequal
     *          also false if there is any closing parenthesis before the opening one
     *          else true
     */
    public static boolean parenthesisAreBalanced(String parenthesis) {
        boolean balanced = true;
        Stack<Character> charStack = new Stack<>();

        for (int i = 0; i < parenthesis.length() && balanced; i++) {
            char nextChar = parenthesis.charAt(i);

            if (nextChar == OPENING_PAREN) {
                charStack.push(nextChar);
            } else if (!charStack.isEmpty() && nextChar == CLOSING_PAREN) {
                charStack.pop();
            } else {
                balanced = false;
            }
        }

        return charStack.isEmpty() && balanced;
    }
}
