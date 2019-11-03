package org.practice.app.parser;

import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestParenthesisForPriorityOperandsParser {

    @Test
    public void priorityOperandsParserCanBeCreated(){
        List<Operation> operations = new ArrayList<>();
        operations.add(new NumberOperation(1d));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisForPriorityOperandsParser parser = new ParenthesisForPriorityOperandsParser(operationsGroup);
    }

    @Test
    public void priorityOperandsParserDoesNotSurroundSimpleNumber(){
        int expectedOperationsAmount = 1, actualOperationsAmount;
        List<Operation> operations = new ArrayList<>();

        operations.add(new NumberOperation(1d));
        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisForPriorityOperandsParser parser = new ParenthesisForPriorityOperandsParser(operationsGroup);

        parser = parser.parsePriorityOperands();
        actualOperationsAmount = parser.getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedOperationsAmount, actualOperationsAmount);
    }

    @Test
    public void priorityOperandsParserDoesNotSurroundSum(){
        int expectedOperationsAmount = 3, actualOperationsAmount;
        List<Operation> operations = new ArrayList<>();

        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('+'));
        operations.add(new NumberOperation(1d));
        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisForPriorityOperandsParser parser = new ParenthesisForPriorityOperandsParser(operationsGroup);

        parser = parser.parsePriorityOperands();
        actualOperationsAmount = parser.getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedOperationsAmount, actualOperationsAmount);
    }

    @Test
    public void priorityOperandsParserDoesNotSurroundDifference(){
        int expectedOperationsAmount = 3, actualOperationsAmount;
        List<Operation> operations = new ArrayList<>();

        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('-'));
        operations.add(new NumberOperation(1d));
        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisForPriorityOperandsParser parser = new ParenthesisForPriorityOperandsParser(operationsGroup);

        parser = parser.parsePriorityOperands();
        actualOperationsAmount = parser.getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedOperationsAmount, actualOperationsAmount);
    }

    @Test
    public void priorityOperandsParserAddsOperationsToMultiplication(){
        int expectedOperationsAmount = 5, actualOperationsAmount;
        List<Operation> operations = new ArrayList<>();

        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('*'));
        operations.add(new NumberOperation(1d));
        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisForPriorityOperandsParser parser = new ParenthesisForPriorityOperandsParser(operationsGroup);

        parser = parser.parsePriorityOperands();
        actualOperationsAmount = parser.getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedOperationsAmount, actualOperationsAmount);

        Operation firstOperation = getOperationFromParserByIndex(parser, 0);
        Operation lastOperation = getOperationFromParserByIndex(parser, 4);

        assertTrue(firstOperation instanceof SingleUndefinedOperation);
        assertTrue(lastOperation instanceof SingleUndefinedOperation);

        assertEquals(((SingleUndefinedOperation) firstOperation).getValue(), '(');
        assertEquals(((SingleUndefinedOperation) lastOperation).getValue(), ')');
    }

    @Test
    public void priorityOperandsParserAddsOperationsToDivision(){
        int expectedOperationsAmount = 5, actualOperationsAmount;
        List<Operation> operations = new ArrayList<>();

        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('/'));
        operations.add(new NumberOperation(1d));
        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisForPriorityOperandsParser parser = new ParenthesisForPriorityOperandsParser(operationsGroup);

        parser = parser.parsePriorityOperands();
        actualOperationsAmount = parser.getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedOperationsAmount, actualOperationsAmount);
    }

    @Test
    public void priorityOperandsGetAddedToComplexExpression(){
        int expectedOperationsAmount = 15, actualOperationsAmount;
        List<Operation> operations = new ArrayList<>();

        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('+'));
        operations.add(new NumberOperation(2d));
        operations.add(new SingleUndefinedOperation('*'));
        operations.add(new SingleUndefinedOperation('('));
        operations.add(new NumberOperation(3d));
        operations.add(new SingleUndefinedOperation('+'));
        operations.add(new NumberOperation(4d));
        operations.add(new SingleUndefinedOperation('/'));
        operations.add(new NumberOperation(5d));
        operations.add(new SingleUndefinedOperation(')'));


        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisForPriorityOperandsParser parser = new ParenthesisForPriorityOperandsParser(operationsGroup);

        parser = parser.parsePriorityOperands();
        actualOperationsAmount = parser.getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedOperationsAmount, actualOperationsAmount);

        Operation firstOpeningParen = getOperationFromParserByIndex(parser, 2);
        Operation nextAddedOpeningParen = getOperationFromParserByIndex(parser, 8);

        assertTrue(firstOpeningParen instanceof SingleUndefinedOperation);
        assertTrue(nextAddedOpeningParen instanceof SingleUndefinedOperation);

        assertEquals(((SingleUndefinedOperation) firstOpeningParen).getValue(), '(');
        assertEquals(((SingleUndefinedOperation) nextAddedOpeningParen).getValue(), '(');
    }

    private Operation getOperationFromParserByIndex(ParenthesisForPriorityOperandsParser parser, int i) {
        return parser.getUndefinedOperationGroup().getOperations().get(i);
    }
}
