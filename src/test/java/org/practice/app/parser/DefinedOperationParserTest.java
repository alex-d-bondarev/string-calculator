package org.practice.app.parser;

import org.junit.Before;
import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.DefinedOperation;
import org.practice.app.operation.parsed.DifferenceOperation;
import org.practice.app.operation.parsed.MultiplicationOperation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.parsed.SumOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;
import org.practice.app.parser.operations.DefinedOperationParser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefinedOperationParserTest {
    private List<Operation> operations;

    @Before
    public void setup() {
        operations = new ArrayList<>();
    }

    @Test
    public void sumOperationIsCreated() {
        addNumberOneOperation();
        addUndefinedOperation('+');
        addNumberOneOperation();

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        DefinedOperationParser parser = new DefinedOperationParser(operationsGroup);

        DefinedOperation result = parser.parseToDefinedOperation();
        assertTrue(result instanceof SumOperation);
    }

    @Test
    public void differenceOperationIsCreated() {
        addNumberOneOperation();
        addUndefinedOperation('-');
        addNumberOneOperation();

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        DefinedOperationParser parser = new DefinedOperationParser(operationsGroup);

        DefinedOperation result = parser.parseToDefinedOperation();
        assertTrue(result instanceof DifferenceOperation);
    }

    @Test
    public void multiplicationOperationIsCreated() {
        String expectedStringOperations = "1.0*1.0";
        addNumberOneOperation();
        addUndefinedOperation('*');
        addNumberOneOperation();

        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);
        DefinedOperationParser parser = new DefinedOperationParser(operationsGroup);

        DefinedOperation result = parser.parseToDefinedOperation();
        assertTrue(result instanceof MultiplicationOperation);

        String actualStringOperations = result.toString();

        assertEquals(expectedStringOperations, actualStringOperations);
    }

    @Test
    public void complexGroupCanBeParsed() {
        String expectedStringOperations = "1.0+2.0/4.0-3.0";

        UndefinedOperationGroup leftGroup = createOperationsGroup(1d, '+', 2d);
        UndefinedOperationGroup rightGroup = createOperationsGroup(4d, '-', 3d);

        operations.add(leftGroup);
        addUndefinedOperation('/');
        operations.add(rightGroup);
        UndefinedOperationGroup operationsGroup = new UndefinedOperationGroup(operations);

        DefinedOperationParser parser = new DefinedOperationParser(operationsGroup);
        String actualStringOperations = parser.parseToDefinedOperation().toString();

        assertEquals(expectedStringOperations, actualStringOperations);
    }

    private UndefinedOperationGroup createOperationsGroup(double v, char c, double v2) {
        List<Operation> group = new ArrayList<>();
        group.add(new NumberOperation(v));
        group.add(new SingleUndefinedOperation(c));
        group.add(new NumberOperation(v2));
        return new UndefinedOperationGroup(group);
    }

    private void addNumberOneOperation() {
        operations.add(new NumberOperation(1d));
    }

    private void addUndefinedOperation(char operation) {
        operations.add(new SingleUndefinedOperation(operation));
    }
}
