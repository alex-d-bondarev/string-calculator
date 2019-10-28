package org.practice.app.parser;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestRawExpressionParser {

    @Test
    public void testNumberToListOf1UndefinedOperation() {
        String testExpression = "2";
        int expectedOperationsSize = 1;

        UndefinedOperationGroup actualGroup =
                new RawExpressionParser(testExpression).
                        parseToUndefinedOperations().
                        getUndefinedOperationGroup();

        assertThat(
                actualGroup.getUndefinedOperations().size(),
                is(expectedOperationsSize));
    }

    @Test
    public void testNumberToListOf3UndefinedOperations() {
        String testExpression = "2+2";
        int expectedOperationsSize = 3;

        UndefinedOperationGroup actualGroup =
                new RawExpressionParser(testExpression).
                        parseToUndefinedOperations().
                        getUndefinedOperationGroup();

        assertThat(
                actualGroup.getUndefinedOperations().size(),
                is(expectedOperationsSize));
    }

    @Test
    public void testUndefinedOperationsOrderIsKept() {
        String testExpression = "1+2*3";
        UndefinedOperationGroup expectedGroup = getTestUndefinedOperationGroup();

        UndefinedOperationGroup actualGroup =
                new RawExpressionParser(testExpression).
                        parseToUndefinedOperations().
                        getUndefinedOperationGroup();

        assertEquals(expectedGroup, actualGroup);
    }

    private UndefinedOperationGroup getTestUndefinedOperationGroup() {
        List<SingleUndefinedOperation> orderedList = new LinkedList<>();

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
