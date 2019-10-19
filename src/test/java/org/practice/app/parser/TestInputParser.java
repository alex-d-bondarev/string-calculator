package org.practice.app.parser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestInputParser {

    @Test
    public void testValidBracketsWithoutAdditionalChars(){
        String testInput = "(())";
        assertTrue(InputParser.isParenthesesBalanced(testInput));
    }

    @Test
    public void testInvalidBracketsWithoutAdditionalChars(){
        String testInput = "(())(";
        assertFalse(InputParser.isParenthesesBalanced(testInput));
    }

    @Test
    public void testValidBracketsWithAdditionalChars(){
        String testInput = "(({{[abc_123+_*&]}}))";
        assertTrue(InputParser.isParenthesesBalanced(testInput));
    }

    @Test
    public void testExpressionWithValidChars(){
        String testInput = "1+2*(4-3/5)";
        assertTrue(InputParser.hasNoExtraSymbols(testInput));
    }

    @Test
    public void testExpressionWithInvalidChars(){
        String testInput = "abc&^%$1+2*(4-3/5)";
        assertFalse(InputParser.hasNoExtraSymbols(testInput));
    }

    @Test
    public void allBracketsAreParenthesis(){
        String testExpression = "{[()]}";
        String expectedExpression = "((()))";

        assertEquals(
                expectedExpression,
                InputParser.replaceBracketsWithParenthesis(testExpression));
    }

    @Test
    public void allExtraSpacesRemoved(){
        String testExpression = " 2 + 2* 2";
        String expectedExpression = "2+2*2";

        assertEquals(
                expectedExpression,
                InputParser.removeExtraSpaces(testExpression));
    }
}
