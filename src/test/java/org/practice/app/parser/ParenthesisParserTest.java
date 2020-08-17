package org.practice.app.parser;

import org.junit.Before;
import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;
import org.practice.app.parser.parenthesis.ParenthesisParser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParenthesisParserTest {

    private List<Operation> operations;

    @Before
    public void setup() {
        operations = new ArrayList<>();
    }

    @Test
    public void noParenthesisCauseNoChanges() {
        addNumberOperation(1d);
        addUndefinedOperation('+');
        addNumberOperation(2d);

        DefinedOperationParser parser = parseOperations();

        assertTrue(getOperationFromParserByIndex(parser, 0) instanceof NumberOperation);
        assertTrue(getOperationFromParserByIndex(parser, 1) instanceof UndefinedOperation);
        assertTrue(getOperationFromParserByIndex(parser, 2) instanceof NumberOperation);
    }

    @Test
    public void parenthesisThatSurroundSingleNumbersAreRemoved() {
        int expectedOperationsAmount = 1;
        addUndefinedOperation('(');
        addNumberOperation(1d);
        addUndefinedOperation(')');

        int actualOperationsAmount = parseOperations().getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedOperationsAmount, actualOperationsAmount);
    }

    @Test
    public void parenthesisThatSurroundMultipleNumbersResultInSubGroup() {
        addUndefinedOperation('(');
        addNumberOperation(1d);
        addUndefinedOperation('+');
        addNumberOperation(2d);
        addUndefinedOperation(')');

        DefinedOperationParser parser = parseOperations();
        assertTrue(getOperationFromParserByIndex(parser, 0) instanceof UndefinedOperationGroup);

        List<Operation> subGroup = ((UndefinedOperationGroup) getOperationFromParserByIndex(parser, 0)).getOperations();
        assertTrue(subGroup.get(0) instanceof NumberOperation);
        assertTrue(subGroup.get(1) instanceof UndefinedOperation);
        assertTrue(subGroup.get(2) instanceof NumberOperation);
    }

    @Test
    public void multipleLevelsOfParenthesisAreParsed() {
        String expression = "((1+2)+(3+4+(5+6)))";
        String expectedParsingResult = "[[[1, +, 2], +, [3, +, 4, +, [5, +, 6]]]]";

        String actualParsingResult = parseExpression(expression).getUndefinedOperationGroup().toString();

        assertFalse("All opening parenthesis should be removed", actualParsingResult.contains("("));
        assertFalse("All closing parenthesis should be removed", actualParsingResult.contains(")"));
        assertEquals(expectedParsingResult, actualParsingResult);
    }

    private void addUndefinedOperation(char value) {
        operations.add(new SingleUndefinedOperation(value));
    }

    private void addNumberOperation(double number) {
        operations.add(new NumberOperation(number));
    }

    private DefinedOperationParser parseOperations() {
        return new ParenthesisParser(new UndefinedOperationGroup(operations)).parseParenthesis();
    }

    private DefinedOperationParser parseExpression(String expression) {
        return new ParenthesisParser(
                new ExpressionMapper(expression).
                        mapToUndefinedOperations().
                        getUndefinedOperationGroup()).
                parseParenthesis();
    }

    private Operation getOperationFromParserByIndex(DefinedOperationParser parser, int i) {
        return parser.getUndefinedOperationGroup().getOperations().get(i);
    }
}
