package org.practice.app.parser;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestRawExpressionParser {

    @Test
    public void testNumberToListOf1UndefinedOperation() {
        String testExpression = "2";
        int expectedOperationsSize = 1;

        List<UndefinedOperationParser> operations =
                new RawExpressionParser(testExpression).
                        parseToUndefinedOperations().
                        getUndefinedOperationParsers();

        assertThat(operations.size(), is(expectedOperationsSize));
    }

    @Test
    public void testNumberToListOf3UndefinedOperations() {
        String testExpression = "2+2";
        int expectedOperationsSize = 3;

        List<UndefinedOperationParser> operations =
                new RawExpressionParser(testExpression).
                        parseToUndefinedOperations().
                        getUndefinedOperationParsers();

        assertThat(operations.size(), is(expectedOperationsSize));
    }

    @Test
    public void testUndefinedOperationsOrderIsKept() {
        String testExpression = "1+2*3";
        List<UndefinedOperationParser> expectedList = getOrderedListForTes();

        List<UndefinedOperationParser> operations =
                new RawExpressionParser(testExpression).
                        parseToUndefinedOperations().
                        getUndefinedOperationParsers();

        assertThat(operations, is(expectedList));
    }

    private List<UndefinedOperationParser> getOrderedListForTes() {
        List<UndefinedOperationParser> orderedList = new LinkedList<>();

        orderedList.add(getUndefinedOperationParser('1'));
        orderedList.add(getUndefinedOperationParser('+'));
        orderedList.add(getUndefinedOperationParser('2'));
        orderedList.add(getUndefinedOperationParser('*'));
        orderedList.add(getUndefinedOperationParser('3'));

        return orderedList;
    }

    private UndefinedOperationParser getUndefinedOperationParser(char ch) {
        return new UndefinedOperationParser(ch);
    }
}
