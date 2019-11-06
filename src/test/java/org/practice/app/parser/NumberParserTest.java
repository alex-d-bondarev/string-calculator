package org.practice.app.parser;

import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumberParserTest {

    @Test
    public void operationParserParsesNegativeNumbersOnly() {
        NumberParser parser = getParserWithParsedNegativeNumbersFromChars('1');

        Operation result = getFirstOperationFromParser(parser);
        assertTrue("Positive numbers should be ignored at this step", result instanceof UndefinedOperation);
    }

    @Test
    public void operationParserParsesNegativeNumbersSuccessfully() {
        NumberParser parser = getParserWithParsedNegativeNumbersFromChars('-', '1');

        Operation result = getFirstOperationFromParser(parser);
        assertTrue(result instanceof NumberOperation);
    }

    @Test
    public void differenceOperationIsNotNegativeNumber() {
        NumberParser parser = getParserWithParsedNegativeNumbersFromChars('2', '-', '1');
        int expectedSizeAfterParsing = 3;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void negativeNumberInTheMiddleOfExpressionIsParsed() {
        NumberParser parser = getParserWithParsedNegativeNumbersFromChars('3', '*', '-', '1', '*', '2');
        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void longNegativeNumberInTheMiddleOfExpressionIsParsed() {
        NumberParser parser = getParserWithParsedNegativeNumbersFromChars('3', '*', '-', '9', '8', '7', '*', '2');
        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void negativeNumberInTheEndOfExpressionIsParsed() {
        NumberParser parser = getParserWithParsedNegativeNumbersFromChars('3', '*', '2', '*', '-', '1');
        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void parenthesisAllowParsingNegativeNumbers() {
        NumberParser parser = getParserWithParsedNegativeNumbersFromChars('2', '+', '(', '-', '1', ')');
        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void positiveNumberIsConvertedToNumberOperation() {
        NumberParser parser = getParserWithParsedPositiveNumbersFromChars('1');

        Operation result = getFirstOperationFromParser(parser);
        assertTrue("Positive numbers should be parsed at this step", result instanceof NumberOperation);
    }

    @Test
    public void differenceOperationContainsPositiveNumbers() {
        NumberParser parser = getParserWithParsedPositiveNumbersFromChars('2', '-', '1');

        assertTrue(getOperationFromParserByIndex(parser, 0) instanceof NumberOperation);
        assertTrue(getOperationFromParserByIndex(parser, 1) instanceof UndefinedOperation);
        assertTrue(getOperationFromParserByIndex(parser, 2) instanceof NumberOperation);
    }

    @Test
    public void longPositiveNumberInTheMiddleOfExpressionIsParsed() {
        NumberParser parser = getParserWithParsedPositiveNumbersFromChars('3', '*', '9', '8', '7', '6', '*', '2');
        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void positiveNumberCanBeParsedAfterNegativeWasParsed() {
        NumberParser parser =
                getParserWithParsedNegativeNumbersFromChars('3', '*', '-', '9', '8', '7', '*', '2').
                        parsePositiveNumbers();

        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void parenthesisAllowParsingPositiveNumbers() {
        NumberParser parser = getParserWithParsedPositiveNumbersFromChars('2', '+', '(', '2', '-', '1', ')');
        int expectedSizeAfterParsing = 7;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    private NumberParser getParserWithParsedNegativeNumbersFromChars(char... values) {
        return new NumberParser(generateOperationGroupFromChars(values)).
                parseNegativeNumbers();
    }

    private NumberParser getParserWithParsedPositiveNumbersFromChars(char... values) {
        return new NumberParser(generateOperationGroupFromChars(values)).
                parsePositiveNumbers();
    }

    private UndefinedOperationGroup generateOperationGroupFromChars(char... values) {
        List<Operation> operationsList = new LinkedList<>();
        for (char value : values) {
            operationsList.add(new SingleUndefinedOperation(value));
        }

        return new UndefinedOperationGroup(operationsList);
    }

    private Operation getFirstOperationFromParser(NumberParser parser) {
        return getOperationFromParserByIndex(parser, 0);
    }

    private Operation getOperationFromParserByIndex(NumberParser parser, int i) {
        return parser.getUndefinedOperationGroup().getOperations().get(i);
    }

    private int getOperationGroupSizeFromParser(NumberParser parser) {
        return parser.getUndefinedOperationGroup().getOperations().size();
    }
}
