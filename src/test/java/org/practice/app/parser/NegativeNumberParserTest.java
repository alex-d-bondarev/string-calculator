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

public class NegativeNumberParserTest {

    @Test
    public void operationParserParsesNegativeNumbersOnly() {
        PositiveNumberParser nextParser = getParserWithParsedNegativeNumbersFromChars('1');
        Operation result = getFirstOperationFromParser(nextParser);

        assertTrue("Positive numbers should be ignored at this step", result instanceof UndefinedOperation);
    }

    @Test
    public void operationParserParsesNegativeNumbersSuccessfully() {
        PositiveNumberParser nextParser = getParserWithParsedNegativeNumbersFromChars('-', '1');
        Operation result = getFirstOperationFromParser(nextParser);

        assertTrue(result instanceof NumberOperation);
    }

    @Test
    public void differenceOperationIsNotNegativeNumber() {
        int expectedSizeAfterParsing = 3;

        PositiveNumberParser nextParser = getParserWithParsedNegativeNumbersFromChars('2', '-', '1');
        int actualSizeAfterParsing = getAmountOfOperationsFrom(nextParser);

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void negativeNumberInTheMiddleOfExpressionIsParsed() {
        int expectedSizeAfterParsing = 5;
        PositiveNumberParser nextParser = getParserWithParsedNegativeNumbersFromChars('3', '*', '-', '1', '*', '2');
        int actualSizeAfterParsing = getAmountOfOperationsFrom(nextParser);

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void longNegativeNumberInTheMiddleOfExpressionIsParsed() {
        int expectedSizeAfterParsing = 5;

        PositiveNumberParser nextParser = getParserWithParsedNegativeNumbersFromChars('3', '*', '-', '9', '8', '7', '*', '2');
        int actualSizeAfterParsing = getAmountOfOperationsFrom(nextParser);

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void negativeNumberInTheEndOfExpressionIsParsed() {
        int expectedSizeAfterParsing = 5;

        PositiveNumberParser nextParser = getParserWithParsedNegativeNumbersFromChars('3', '*', '2', '*', '-', '1');
        int actualSizeAfterParsing = getAmountOfOperationsFrom(nextParser);

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void parenthesisAllowParsingNegativeNumbers() {
        int expectedSizeAfterParsing = 5;

        PositiveNumberParser nextParser = getParserWithParsedNegativeNumbersFromChars('2', '+', '(', '-', '1', ')');
        int actualSizeAfterParsing = getAmountOfOperationsFrom(nextParser);

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    private PositiveNumberParser getParserWithParsedNegativeNumbersFromChars(char... values) {
        return new NegativeNumberParser(generateOperationGroupFromChars(values)).
                parseNegativeNumbers();
    }

    private UndefinedOperationGroup generateOperationGroupFromChars(char... values) {
        List<Operation> operationsList = new LinkedList<>();
        for (char value : values) {
            operationsList.add(new SingleUndefinedOperation(value));
        }

        return new UndefinedOperationGroup(operationsList);
    }

    private Operation getFirstOperationFromParser(PositiveNumberParser parser) {
        return parser.getUndefinedOperationGroup().getOperations().get(0);
    }

    private int getAmountOfOperationsFrom(PositiveNumberParser parser) {
        return parser.getUndefinedOperationGroup().getOperations().size();
    }
}
