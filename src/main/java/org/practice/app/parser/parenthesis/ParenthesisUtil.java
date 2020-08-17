package org.practice.app.parser.parenthesis;

import java.util.*;

public class ParenthesisUtil {

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

            if (nextChar == Parenthesis.OPENING) {
                charStack.push(nextChar);
            } else if (!charStack.isEmpty() && nextChar == Parenthesis.CLOSING) {
                charStack.pop();
            } else {
                balanced = false;
            }
        }

        return charStack.isEmpty() && balanced;
    }
}
