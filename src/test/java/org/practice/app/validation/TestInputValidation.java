package org.practice.app.validation;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestInputValidation {

    @Test
    public void testExpressionWithValidChars(){
        String testInput = "1+2*(4-3/5)";
        assertFalse(InputValidation.hasExtraSymbols(testInput));
    }

    @Test
    public void testExpressionWithInvalidChars(){
        String testInput = "abc&^%$1+2*(4-3/5)";
        assertTrue(InputValidation.hasExtraSymbols(testInput));
    }
}
