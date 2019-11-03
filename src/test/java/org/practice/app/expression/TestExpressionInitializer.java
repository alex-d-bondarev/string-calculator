package org.practice.app.expression;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

import java.util.LinkedList;
import java.util.List;

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

    @Test
    public void testNumberToListOf1UndefinedOperation() {
        String testExpression = "2";
        int expectedOperationsSize = 1;

        UndefinedOperationGroup actualGroup =
                new ExpressionInitializer(testExpression).
                        convertCharsToUndefinedOperations().
                        getUndefinedOperationGroup();

        assertThat(
                actualGroup.getOperations().size(),
                is(expectedOperationsSize));
    }

    @Test
    public void testNumberToListOf3UndefinedOperations() {
        String testExpression = "2+2";
        int expectedOperationsSize = 3;

        UndefinedOperationGroup actualGroup =
                new ExpressionInitializer(testExpression).
                        convertCharsToUndefinedOperations().
                        getUndefinedOperationGroup();

        assertThat(
                actualGroup.getOperations().size(),
                is(expectedOperationsSize));
    }

    @Test
    public void testUndefinedOperationsOrderIsKept() {
        String testExpression = "1+2*3";
        UndefinedOperationGroup expectedGroup = getTestUndefinedOperationGroup();

        UndefinedOperationGroup actualGroup =
                new ExpressionInitializer(testExpression).
                        convertCharsToUndefinedOperations().
                        getUndefinedOperationGroup();

        assertEquals(expectedGroup, actualGroup);
    }

    private UndefinedOperationGroup getTestUndefinedOperationGroup() {
        List<Operation> orderedList = new LinkedList<>();

        orderedList.add(getUndefinedOperationParser('1'));
        orderedList.add(getUndefinedOperationParser('+'));
        orderedList.add(getUndefinedOperationParser('2'));
        orderedList.add(getUndefinedOperationParser('*'));
        orderedList.add(getUndefinedOperationParser('3'));

        return new UndefinedOperationGroup(orderedList);
    }

    private SingleUndefinedOperation getUndefinedOperationParser(char ch) {
        return new SingleUndefinedOperation(ch);
    }
}
