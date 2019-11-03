package org.practice.app.parser;

import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestParenthesisParser {

    @Test
    public void noParenthesisCauseNoChanges(){
        List<Operation> operations = new ArrayList<>();
        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('+'));
        operations.add(new NumberOperation(2d));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisParser parser = new ParenthesisParser(operationsGroup);

        parser = parser.parseParenthesis();

        assertTrue(getOperationFromParserByIndex(parser, 0) instanceof NumberOperation);
        assertTrue(getOperationFromParserByIndex(parser, 1) instanceof UndefinedOperation);
        assertTrue(getOperationFromParserByIndex(parser, 2) instanceof NumberOperation);
    }

    @Test
    public void parenthesisThatSurroundSingleNumbersAreRemoved(){
        int expectedOperationsAmount = 1, actualOperationsAmount;
        List<Operation> operations = new ArrayList<>();
        operations.add(new SingleUndefinedOperation('('));
        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation(')'));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisParser parser = new ParenthesisParser(operationsGroup);

        parser = parser.parseParenthesis();

        actualOperationsAmount = parser.getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedOperationsAmount, actualOperationsAmount);
    }

    @Test
    public void parenthesisThatSurroundMultipleNumbersBecomeUndefinedOperationsGroup(){
        List<Operation> operations = new ArrayList<>();
        operations.add(new SingleUndefinedOperation('('));
        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('+'));
        operations.add(new NumberOperation(2d));
        operations.add(new SingleUndefinedOperation(')'));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisParser parser = new ParenthesisParser(operationsGroup);

        parser = parser.parseParenthesis();

        assertTrue(getOperationFromParserByIndex(parser, 0) instanceof UndefinedOperationGroup);
    }

    @Test
    public void newParenthesisSubGroupRemainsTheSame(){
        List<Operation> operations = new ArrayList<>();
        operations.add(new SingleUndefinedOperation('('));
        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('+'));
        operations.add(new NumberOperation(2d));
        operations.add(new SingleUndefinedOperation(')'));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisParser parser = new ParenthesisParser(operationsGroup).parseParenthesis();

        operations = ((UndefinedOperationGroup) parser.getUndefinedOperationGroup().getOperations().get(0)).getOperations();

        assertTrue(operations.get(0) instanceof NumberOperation);
        assertTrue(operations.get(1) instanceof UndefinedOperation);
        assertTrue(operations.get(2) instanceof NumberOperation);
    }

    @Test
    public void multipleLevelsOfParenthesisAreParsed(){
        String actualParsingResult;
        String expectedParsingResult = "[[[1, +, 2], +, [3, +, 4, +, [5, +, 6]]]]";
        String expression = "((1+2)+(3+4+(5+6)))";

        ParenthesisParser parser = generateParenthesisParserFromExpression(expression);
        actualParsingResult = parser.parseParenthesis().getUndefinedOperationGroup().toString();

        assertFalse("All opening parenthesis should be removed", actualParsingResult.contains("("));
        assertFalse("All closing parenthesis should be removed", actualParsingResult.contains(")"));
        assertEquals(expectedParsingResult, actualParsingResult);
    }

    private ParenthesisParser generateParenthesisParserFromExpression(String expression){
        UndefinedOperationGroup operationsGroup =
                new CharsParser(expression).convertCharsToUndefinedOperations().getUndefinedOperationGroup();

        return new ParenthesisParser(operationsGroup);
    }

    private Operation getOperationFromParserByIndex(ParenthesisParser parser, int i) {
        return parser.getUndefinedOperationGroup().getOperations().get(i);
    }
}
