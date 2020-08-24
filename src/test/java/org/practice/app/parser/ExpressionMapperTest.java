package org.practice.app.parser;

import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ExpressionMapperTest {

    @Test
    public void testNumberToListOf1UndefinedOperation() {
        String expression = "2";
        int expectedOperationsSize = 1;

        UndefinedOperationsList actualGroup = getUndefinedOperationGroupFromExpression(expression);

        assertThat(actualGroup.size(), is(expectedOperationsSize));
    }

    @Test
    public void testNumberToListOf3UndefinedOperations() {
        String expression = "2+2";
        int expectedOperationsSize = 3;

        UndefinedOperationsList actualGroup = getUndefinedOperationGroupFromExpression(expression);

        assertThat(actualGroup.size(), is(expectedOperationsSize));
    }

    @Test
    public void testUndefinedOperationsOrderIsKept() {
        String testExpression = "1+2*3";

        UndefinedOperationsList expectedGroup = getTestUndefinedOperationGroup();
        UndefinedOperationsList actualGroup = getUndefinedOperationGroupFromExpression(testExpression);

        assertEquals(expectedGroup, actualGroup);
    }

    private UndefinedOperationsList getUndefinedOperationGroupFromExpression(String expression) {
        return new ExpressionMapper(expression).mapToUndefinedOperations().getUndefinedOperationGroup();
    }

    private UndefinedOperationsList getTestUndefinedOperationGroup() {
        List<Operation> orderedList = new LinkedList<>();

        orderedList.add(getUndefinedOperationParser('1'));
        orderedList.add(getUndefinedOperationParser('+'));
        orderedList.add(getUndefinedOperationParser('2'));
        orderedList.add(getUndefinedOperationParser('*'));
        orderedList.add(getUndefinedOperationParser('3'));

        return new UndefinedOperationsList(orderedList);
    }

    private SingleUndefinedOperation getUndefinedOperationParser(char ch) {
        return new SingleUndefinedOperation(ch);
    }
}