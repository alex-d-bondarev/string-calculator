package org.practice.app.input;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestInputValidation {

    private InputValidation validation;

    @Test
    public void testExpressionWithValidChars(){
        validation = new InputValidation("1+2*(4-3/5)");
        assertFalse(validation.hasExtraSymbols());
    }

    @Test
    public void testExpressionWithInvalidChars(){
        validation = new InputValidation("abc&^%$1+2*(4-3/5)");
        assertTrue(validation.hasExtraSymbols());
    }
}
