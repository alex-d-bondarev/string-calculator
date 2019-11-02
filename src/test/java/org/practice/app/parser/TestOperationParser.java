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
    public void operationParserParsesNegativeNumbersOnly(){
        UndefinedOperationGroup group = getOperationGroupFrom('1');
        OperationParser parser = new OperationParser(group).
                parseNegativeNumbers();

        Operation result = parser.getUndefinedOperationGroup().getOperations().get(0);

        assertTrue("Positive numbers are ignored at this step", result instanceof UndefinedOperation);
    }

    @Test
    public void operationParserParsesNegativeNumbersSuccessfully(){
        UndefinedOperationGroup group = getOperationGroupFrom('-','1');
        OperationParser parser = new OperationParser(group).
                parseNegativeNumbers();

        Operation result = parser.getUndefinedOperationGroup().getOperations().get(0);

        assertTrue(result instanceof NumberOperation);
    }

    @Test
    public void differenceOperationIsNotNegativeNumber(){
        UndefinedOperationGroup group = getOperationGroupFrom('2','-','1');
        int expectedSizeAfterParsing = group.getOperations().size();
        OperationParser parser = new OperationParser(group).
                parseNegativeNumbers();
        int actualSizeAfterParsing = parser.getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    @Test
    public void negativeNumberInTHeMiddleOfExpressionIsParsed(){
        UndefinedOperationGroup group = getOperationGroupFrom('3','*', '2','*','-','1');
        int expectedSizeAfterParsing = group.getOperations().size()-1;
        OperationParser parser = new OperationParser(group).
                parseNegativeNumbers();
        int actualSizeAfterParsing = parser.getUndefinedOperationGroup().getOperations().size();

        assertEquals(expectedSizeAfterParsing, actualSizeAfterParsing);
    }

    private UndefinedOperationGroup getOperationGroupFrom(char... values){
        List<Operation> operationsList = new LinkedList<>();
        for(char value : values){
            operationsList.add(new SingleUndefinedOperation(value));
        }

        return new UndefinedOperationGroup(operationsList);
    }
}
