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

public class PositiveNumberParserTest {

    @Test
    public void positiveNumberIsConvertedToNumberOperation() {
        ParenthesisForPriorityOperandsParser parser = getParserWithParsedPositiveNumbersFromChars('1');
        Operation result = getFirstOperationFromParser(parser);
        assertTrue("Positive numbers should be parsed at this step", result instanceof NumberOperation);
    }

    @Test
    public void differenceOperationContainsPositiveNumbers() {
        ParenthesisForPriorityOperandsParser parser = getParserWithParsedPositiveNumbersFromChars('2', '-', '1');

        assertTrue(getOperationFromParserByIndex(parser, 0) instanceof NumberOperation);
        assertTrue(getOperationFromParserByIndex(parser, 1) instanceof UndefinedOperation);
        assertTrue(getOperationFromParserByIndex(parser, 2) instanceof NumberOperation);
    }

    @Test
    public void longPositiveNumberInTheMiddleOfExpressionIsParsed() {
        int expectedSizeAfterParsing = 5;

        ParenthesisForPriorityOperandsParser parser = getParserWithParsedPositiveNumbersFromChars('3', '*', '9', '8', '7', '6', '*', '2');
        int actualSizeAfterParsing = getAmountOfOperationsFrom(parser);

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void positiveNumberCanBeParsedAfterNegativeWasParsed() {
        int expectedSizeAfterParsing = 5;

        ParenthesisForPriorityOperandsParser parser =
                getParserWithParsedNegativeNumbersFromChars('3', '*', '-', '9', '8', '7', '*', '2').
                        parsePositiveNumbers();
        int actualSizeAfterParsing = getAmountOfOperationsFrom(parser);

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void parenthesisAllowParsingPositiveNumbers() {
        int expectedSizeAfterParsing = 7;

        ParenthesisForPriorityOperandsParser parser = getParserWithParsedPositiveNumbersFromChars('2', '+', '(', '2', '-', '1', ')');
        int actualSizeAfterParsing = getAmountOfOperationsFrom(parser);

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    private PositiveNumberParser getParserWithParsedNegativeNumbersFromChars(char... values) {
        return new NegativeNumberParser(generateOperationGroupFromChars(values)).
                parseNegativeNumbers();
    }

    private ParenthesisForPriorityOperandsParser getParserWithParsedPositiveNumbersFromChars(char... values) {
        return new PositiveNumberParser(generateOperationGroupFromChars(values)).
                parsePositiveNumbers();
    }

    private UndefinedOperationGroup generateOperationGroupFromChars(char... values) {
        List<Operation> operationsList = new LinkedList<>();
        for (char value : values) {
            operationsList.add(new SingleUndefinedOperation(value));
        }

        return new UndefinedOperationGroup(operationsList);
    }

    private Operation getFirstOperationFromParser(ParenthesisForPriorityOperandsParser parser) {
        return getOperationFromParserByIndex(parser, 0);
    }

    private Operation getOperationFromParserByIndex(ParenthesisForPriorityOperandsParser parser, int i) {
        return parser.getUndefinedOperationGroup().getOperations().get(i);
    }

    private int getAmountOfOperationsFrom(ParenthesisForPriorityOperandsParser parser) {
        return parser.getUndefinedOperationGroup().getOperations().size();
    }
}
