package org.practice.app.parser;

import org.junit.Test;
import org.practice.app.parser.parenthesis.ParenthesisUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParenthesisUtilTest {

    @Test
    public void shouldReceiveBracketsOnly() {
        String testExpression = "2+2+2";
        assertFalse(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }

    @Test
    public void onlyOpeningBracketsIsUnBalanced() {
        String testExpression = "(";
        assertFalse(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }

    @Test
    public void onlyClosingBracketsIsUnBalanced() {
        String testExpression = ")";
        assertFalse(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }

    @Test
    public void noSymbolsMeansBalanced() {
        String testExpression = "";
        assertTrue(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }

    @Test
    public void balancedBracketsIdentifiedCorrectly() {
        String testExpression = "()";
        assertTrue(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }

    @Test
    public void multipleLevelsOfBalancedBracketsIdentifiedCorrectly() {
        String testExpression = "((((()))))";
        assertTrue(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }

    @Test
    public void combinationOfBalancedBracketsIdentifiedCorrectly() {
        String testExpression = "(()()(()))";
        assertTrue(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }

    @Test
    public void lessOpeningBracketsThanClosedIsUnBalanced() {
        String testExpression = "(((()))))";
        assertFalse(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }

    @Test
    public void lessClosingBracketsThanOpeningIsUnBalanced() {
        String testExpression = "((((())))";
        assertFalse(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }

    @Test
    public void differentParenthesisOrderIsUnbalanced() {
        String testExpression = ")(";
        assertFalse(ParenthesisUtil.parenthesisAreBalanced(testExpression));
    }
}
