package org.practice.app;

import org.junit.Test;
import org.practice.app.Expression;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExpressionTest {

    private Expression expression;

    @Test
    public void allExtraSpacesAreRemoved() {
        String testExpression = "2 + 2";
        String expectedExpression = "2+2";
        expression = new Expression(testExpression);

        assertEquals(expectedExpression, expression.getExpression());
    }

    @Test
    public void allExtraBracketsAreReplaced() {
        String testExpression = "{2 + 2}";
        String expectedExpression = "(2+2)";
        expression = new Expression(testExpression);

        assertEquals(expectedExpression, expression.getExpression());
    }

    @Test
    public void testExpressionWithValidChars() {
        expression = new Expression("1+2*(4-3/5)");
        assertFalse(expression.hasUnsupportedSymbols());
    }

    @Test
    public void testExpressionWithInvalidChars() {
        expression = new Expression("abc&^%$1+2*(4-3/5)");
        assertTrue(expression.hasUnsupportedSymbols());
    }

    @Test
    public void testExpressionWithBalancedParenthesis() {
        expression = new Expression("(1+(2+3))");
        assertFalse(expression.hasUnbalancedParentheses());
    }

    @Test
    public void testExpressionWithUnbalancedParenthesis() {
        expression = new Expression("(1+2+3))");
        assertTrue(expression.hasUnbalancedParentheses());
    }
}
