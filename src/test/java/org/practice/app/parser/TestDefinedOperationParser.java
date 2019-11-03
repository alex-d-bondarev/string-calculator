package org.practice.app.parser;

import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.DefinedOperation;
import org.practice.app.operation.parsed.DifferenceOperation;
import org.practice.app.operation.parsed.MultiplicationOperation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.parsed.SumOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDefinedOperationParser {

    @Test
    public void definedOperationParserCanBeCreated(){
        List<Operation> operations = new ArrayList<>();
        operations.add(new NumberOperation(1d));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        DefinedOperationParser parser = new DefinedOperationParser(operationsGroup);
    }

    @Test
    public void parenthesisParserCreatesDefinedOperationParser(){
        List<Operation> operations = new ArrayList<>();
        operations.add(new NumberOperation(1d));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        ParenthesisParser parser = new ParenthesisParser(operationsGroup);
        DefinedOperationParser expectedParser = parser.getDefinedOperationParser();
    }

    @Test
    public void sumOperationIsCreated(){
        List<Operation> operations = new ArrayList<>();
        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('+'));
        operations.add(new NumberOperation(1d));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        DefinedOperationParser parser = new DefinedOperationParser(operationsGroup);

        DefinedOperation result = parser.parseToDefinedOperation();
        assertTrue(result instanceof SumOperation);
    }

    @Test
    public void differenceOperationIsCreated(){
        List<Operation> operations = new ArrayList<>();
        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('-'));
        operations.add(new NumberOperation(1d));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        DefinedOperationParser parser = new DefinedOperationParser(operationsGroup);

        DefinedOperation result = parser.parseToDefinedOperation();
        assertTrue(result instanceof DifferenceOperation);
    }

    @Test
    public void multiplicationOperationIsCreated(){
        List<Operation> operations = new ArrayList<>();
        operations.add(new NumberOperation(1d));
        operations.add(new SingleUndefinedOperation('*'));
        operations.add(new NumberOperation(1d));

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        DefinedOperationParser parser = new DefinedOperationParser(operationsGroup);

        DefinedOperation result = parser.parseToDefinedOperation();
        assertTrue(result instanceof MultiplicationOperation);
    }

    @Test
    public void complexGroupCanBeParsed(){
        String expectedStringOperations = "1.0+2.0/4.0-3.0";

        List<Operation> leftOperations = new ArrayList<>();
        leftOperations.add(new NumberOperation(1d));
        leftOperations.add(new SingleUndefinedOperation('+'));
        leftOperations.add(new NumberOperation(2d));
        UndefinedOperationGroup leftGroup = new UndefinedOperationGroup(leftOperations);

        List<Operation> rightOperations = new ArrayList<>();
        rightOperations.add(new NumberOperation(4d));
        rightOperations.add(new SingleUndefinedOperation('-'));
        rightOperations.add(new NumberOperation(3d));
        UndefinedOperationGroup rightGroup = new UndefinedOperationGroup(rightOperations);

        List<Operation> operations = new ArrayList<>();
        operations.add(leftGroup);
        operations.add(new SingleUndefinedOperation('/'));
        operations.add(rightGroup);
        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);

        DefinedOperationParser parser = new DefinedOperationParser(operationsGroup);
        String actualStringOperations = parser.parseToDefinedOperation().toString();

        assertEquals(expectedStringOperations, actualStringOperations);
    }
}
