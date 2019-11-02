package org.practice.app.parser;

import org.junit.Test;
import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

import java.util.ArrayList;
import java.util.List;

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

    private Operation getOperationFromParserByIndex(ParenthesisParser parser, int i) {
        return parser.getUndefinedOperationGroup().getOperations().get(i);
    }
}
