package org.practice.app.expression;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExpressionValidatorTest {

    private ExpressionValidator validation;

    @Test
    public void testExpressionWithValidChars(){
        validation = new ExpressionValidator("1+2*(4-3/5)");
        assertFalse(validation.hasUnsupportedSymbols());
    }

    @Test
    public void testExpressionWithInvalidChars(){
        validation = new ExpressionValidator("abc&^%$1+2*(4-3/5)");
        assertTrue(validation.hasUnsupportedSymbols());
    }

    @Test
    public void testExpressionWithBalancedParenthesis(){
        validation = new ExpressionValidator("(1+(2+3))");
        assertFalse(validation.hasUnbalancedParentheses());
    }

    @Test
    public void testExpressionWithUnbalancedParenthesis(){
        validation = new ExpressionValidator("(1+2+3))");
        assertTrue(validation.hasUnbalancedParentheses());
    }
}
