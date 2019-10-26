package org.practice.app.expression;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestExpressionInitializer {

    @Test
    public void returnsSelfAfterSpacesAreRemoved(){
        String testExpression = "2 + 2";
        ExpressionInitializer initializer = new ExpressionInitializer(testExpression);

        assertThat(initializer.removeExtraSpaces(), instanceOf(ExpressionInitializer.class));
    }

    @Test
    public void allExtraSpacesAreRemoved(){
        String testExpression = "2 + 2";
        String expectedExpression = "2+2";
        ExpressionInitializer initializer = new ExpressionInitializer(testExpression);

        assertEquals(expectedExpression, initializer.removeExtraSpaces().getExpression());
    }

    @Test
    public void returnsSelfAfterBracketsAreReplaced(){
        String testExpression = "2 + 2";
        ExpressionInitializer initializer = new ExpressionInitializer(testExpression);

        assertThat(initializer.replaceBracketsWithParenthesis(), instanceOf(ExpressionInitializer.class));
    }

    @Test
    public void allExtraBracketsAreReplaced(){
        String testExpression = "{2 + 2}";
        String expectedExpression = "(2 + 2)";
        ExpressionInitializer initializer = new ExpressionInitializer(testExpression);

        assertEquals(expectedExpression, initializer.replaceBracketsWithParenthesis().getExpression());
    }
}
