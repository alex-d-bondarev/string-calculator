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

public class TestOperationParser {

    @Test
    public void operationParserParsesNegativeNumbersOnly() {
        OperationParser parser = getParserWithParsedNegativeNumbersFromChars('1');

        Operation result = getFirstOperationFromParser(parser);
        assertTrue("Positive numbers should be ignored at this step", result instanceof UndefinedOperation);
    }

    @Test
    public void operationParserParsesNegativeNumbersSuccessfully() {
        OperationParser parser = getParserWithParsedNegativeNumbersFromChars('-', '1');

        Operation result = getFirstOperationFromParser(parser);
        assertTrue(result instanceof NumberOperation);
    }

    @Test
    public void differenceOperationIsNotNegativeNumber() {
        OperationParser parser = getParserWithParsedNegativeNumbersFromChars('2', '-', '1');
        int expectedSizeAfterParsing = 3;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void negativeNumberInTheMiddleOfExpressionIsParsed() {
        OperationParser parser = getParserWithParsedNegativeNumbersFromChars('3', '*', '-', '1', '*', '2');
        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void longNegativeNumberInTheMiddleOfExpressionIsParsed() {
        OperationParser parser = getParserWithParsedNegativeNumbersFromChars('3', '*', '-', '9', '8', '7', '*', '2');
        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void negativeNumberInTheEndOfExpressionIsParsed() {
        OperationParser parser = getParserWithParsedNegativeNumbersFromChars('3', '*', '2', '*', '-', '1');
        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void positiveNumberIsConvertedToNumberOperation() {
        OperationParser parser = getParserWithParsedPositiveNumbersFromChars('1');

        Operation result = getFirstOperationFromParser(parser);
        assertTrue("Positive numbers should be parsed at this step", result instanceof NumberOperation);
    }

    @Test
    public void differenceOperationContainsPositiveNumbers() {
        OperationParser parser = getParserWithParsedPositiveNumbersFromChars('2', '-', '1');

        assertTrue(getOperationFromParserByIndex(parser, 0) instanceof NumberOperation);
        assertTrue(getOperationFromParserByIndex(parser, 1) instanceof UndefinedOperation);
        assertTrue(getOperationFromParserByIndex(parser, 2) instanceof NumberOperation);
    }

    @Test
    public void longPositiveNumberInTheMiddleOfExpressionIsParsed() {
        OperationParser parser = getParserWithParsedPositiveNumbersFromChars('3', '*', '9', '8', '7', '6', '*', '2');
        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void positiveNumberCanBeParsedAfterNegativeWasParsed() {
        OperationParser parser =
                getParserWithParsedNegativeNumbersFromChars('3', '*', '-', '9', '8', '7', '*', '2').
                        parsePositiveNumbers();

        int expectedSizeAfterParsing = 5;

        int actualSizeAfterParsing = getOperationGroupSizeFromParser(parser);
        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    private OperationParser getParserWithParsedNegativeNumbersFromChars(char... values) {
        return new OperationParser(generateOperationGroupFromChars(values)).
                parseNegativeNumbers();
    }

    private OperationParser getParserWithParsedPositiveNumbersFromChars(char... values) {
        return new OperationParser(generateOperationGroupFromChars(values)).
                parsePositiveNumbers();
    }

    private UndefinedOperationGroup generateOperationGroupFromChars(char... values) {
        List<Operation> operationsList = new LinkedList<>();
        for (char value : values) {
            operationsList.add(new SingleUndefinedOperation(value));
        }

        return new UndefinedOperationGroup(operationsList);
    }

    private Operation getFirstOperationFromParser(OperationParser parser) {
        return getOperationFromParserByIndex(parser, 0);
    }

    private Operation getOperationFromParserByIndex(OperationParser parser, int i) {
        return parser.getUndefinedOperationGroup().getOperations().get(i);
    }

    private int getOperationGroupSizeFromParser(OperationParser parser) {
        return parser.getUndefinedOperationGroup().getOperations().size();
    }
}
