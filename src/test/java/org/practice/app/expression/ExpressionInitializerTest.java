package org.practice.app.expression;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionInitializerTest {

    @Test
    public void allExtraSpacesAreRemoved() {
        String testExpression = "2 + 2";
        String expectedExpression = "2+2";
        ExpressionInitializer initializer = new ExpressionInitializer(testExpression);

        assertEquals(expectedExpression, initializer.prepareExpression());
    }

    @Test
    public void allExtraBracketsAreReplaced() {
        String testExpression = "{2 + 2}";
        String expectedExpression = "(2+2)";
        ExpressionInitializer initializer = new ExpressionInitializer(testExpression);

        assertEquals(expectedExpression, initializer.prepareExpression());
    }
}
