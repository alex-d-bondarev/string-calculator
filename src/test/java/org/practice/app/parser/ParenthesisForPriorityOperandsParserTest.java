package org.practice.app.parser;

import org.junit.Before;
import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;
import org.practice.app.parser.parenthesis.ParenthesisForPriorityOperandsParser;
import org.practice.app.parser.parenthesis.ParenthesisParser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParenthesisForPriorityOperandsParserTest {
    private List<Operation> operations;

    @Before
    public void setup() {
        operations = new ArrayList<>();
    }

    @Test
    public void priorityOperandsParserDoesNotSurroundSimpleNumber() {
        int expectedOperationsAmount = 1;
        addNumberOperation(1d);

        ParenthesisParser parser = parseOperations();

        assertOperationsAmount(expectedOperationsAmount, parser);
    }

    @Test
    public void priorityOperandsParserDoesNotSurroundSum() {
        int expectedOperationsAmount = 3;
        addNumberOperation(1d);
        addUndefinedOperation('+');
        addNumberOperation(1d);

        ParenthesisParser parser = parseOperations();

        assertOperationsAmount(expectedOperationsAmount, parser);
    }

    @Test
    public void priorityOperandsParserDoesNotSurroundDifference() {
        int expectedOperationsAmount = 3;
        addNumberOperation(1d);
        addUndefinedOperation('-');
        addNumberOperation(1d);

        ParenthesisParser parser = parseOperations();

        assertOperationsAmount(expectedOperationsAmount, parser);
    }

    @Test
    public void priorityOperandsParserAddsOperationsToMultiplication() {
        int expectedOperationsAmount = 5;
        String expectedParsingResult = "[(, 1.0, *, 1.0, )]";
        addNumberOperation(1d);
        addUndefinedOperation('*');
        addNumberOperation(1d);

        ParenthesisParser parser = parseOperations();

        assertOperationsAmount(expectedOperationsAmount, parser);
        assertOperationValues(expectedParsingResult, parser);
    }

    @Test
    public void priorityOperandsParserAddsOperationsToDivision() {
        int expectedOperationsAmount = 5;
        addNumberOperation(1d);
        addUndefinedOperation('/');
        addNumberOperation(1d);

        ParenthesisParser parser = parseOperations();

        assertOperationsAmount(expectedOperationsAmount, parser);
    }

    @Test
    public void priorityOperandsGetAddedToComplexExpression() {
        int expectedOperationsAmount = 15;
        String expectedParsingResult = "[1.0, +, (, 2.0, *, (, 3.0, +, (, 4.0, /, 5.0, ), ), )]";
        addNumberOperation(1d);
        addUndefinedOperation('+');
        addNumberOperation(2d);
        addUndefinedOperation('*');
        addUndefinedOperation('(');
        addNumberOperation(3d);
        addUndefinedOperation('+');
        addNumberOperation(4d);
        addUndefinedOperation('/');
        addNumberOperation(5d);
        addUndefinedOperation(')');

        ParenthesisParser parser = parseOperations();

        assertOperationsAmount(expectedOperationsAmount, parser);
        assertOperationValues(expectedParsingResult, parser);
    }

    private void addNumberOperation(double number) {
        operations.add(new NumberOperation(number));
    }

    private void addUndefinedOperation(char c) {
        operations.add(new SingleUndefinedOperation(c));
    }

    private void assertOperationsAmount(int expectedOperationsAmount, ParenthesisParser parser) {
        int actualOperationsAmount = parser.getUndefinedOperationGroup().size();
        assertEquals(expectedOperationsAmount, actualOperationsAmount);
    }

    private void assertOperationValues(String expectedParsingResult, ParenthesisParser parser) {
        String actualParsingResult = parser.getUndefinedOperationGroup().toString();
        assertEquals(expectedParsingResult, actualParsingResult);
    }

    private ParenthesisParser parseOperations() {
        UndefinedOperationsList operationsGroup = new UndefinedOperationsList(operations);
        return new ParenthesisForPriorityOperandsParser(operationsGroup).parsePriorityOperands();
    }
}
