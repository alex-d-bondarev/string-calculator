package org.practice.app.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestParenthesisUtil {

    @Test
    public void sumDoesNotNeedParenthesis(){
        String testExpression = "2+2+2";
        assertFalse(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void differenceDoesNotNeedParenthesis(){
        String testExpression = "2-2-2";
        assertFalse(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void parenthesisNotNeededForShortMultiplication(){
        String testExpression = "2*2";
        assertFalse(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void parenthesisNotNeededForShortDivision(){
        String testExpression = "2/2";
        assertFalse(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void longExpressionWithPriorityNeedsParenthesis(){
        String testExpression = "2*2+2*2+2/2";
        assertTrue(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void longExpressionWithPriorityAlreadyHasParenthesis(){
        String testExpression = "(2*2)+(2*2)+(2/2)";
        assertFalse(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void longExpressionLostSomeParenthesisInTheEnd(){
        String testExpression = "(2*2)+(2*2)+2/2";
        assertTrue(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void longExpressionLostSomeParenthesisInTheBeginning(){
        String testExpression = "(2*2)+2*2+(2/2)";
        assertTrue(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void longExpressionLostSomeParenthesisInTheMiddle(){
        String testExpression = "(2*2)+2*2+(2/2)";
        assertTrue(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void complexExpressionNeedsParenthesisForSequence(){
        String testExpression = "(2+2*(2/2))";
        assertTrue(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void sequenceHasAllNeededParenthesis(){
        String testExpression = "(2+(2*(2/2)))";
        assertFalse(ParenthesisUtil.needsParenthesis(testExpression));
    }

    @Test
    public void addParenthesisToSimpleExpressionForSequence(){
        String testExpression = "1+2*3";
        String expectedExpression = "1+(2*3)";
        assertEquals(expectedExpression, ParenthesisUtil.addMissedParenthesis(testExpression));
    }

    @Test
    public void addMultipleParenthesisToSimpleExpressionForSequence(){
        String testExpression = "1+2*3+4*5";
        String expectedExpression = "1+(2*3)+(4*5)";
        assertEquals(expectedExpression, ParenthesisUtil.addMissedParenthesis(testExpression));
    }

    @Test
    public void noExtraParenthesisNeededForComplexShortExpression(){
        String testExpression = "(1+2)*3";
        String expectedExpression = "(1+2)*3";
        assertEquals(expectedExpression, ParenthesisUtil.addMissedParenthesis(testExpression));
    }

    @Test
    public void additionalParenthesisNeededForComplexExpression(){
        String testExpression = "(1+2)*3+4*5";
        String expectedExpression = "(1+2)*3+(4*5)";
        assertEquals(expectedExpression, ParenthesisUtil.addMissedParenthesis(testExpression));
    }

    @Test
    public void differentExpressionOrderRequiresDifferentParenthesis(){
        String testExpression = "(1+2)+3*4*5";
        String expectedExpression = "(1+2)+((3*4)*5)";
        assertEquals(expectedExpression, ParenthesisUtil.addMissedParenthesis(testExpression));
    }
}
