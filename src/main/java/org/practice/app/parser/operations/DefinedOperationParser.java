package org.practice.app.parser.operations;

import org.practice.app.operation.Operation;
import org.practice.app.operation.OperationFactory;
import org.practice.app.operation.parsed.DefinedOperation;
import org.practice.app.operation.parsed.OperandOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;

public class DefinedOperationParser {
    private static final OperationFactory OPERATION_FACTORY = new OperationFactory();
    private boolean undefinedOperandWasNotFound;
    private final Operation operation;
    private Operation operationNow;
    private DefinedOperation leftOperation;
    private DefinedOperation rightOperation;
    private UndefinedOperationsList undefinedOperationsList;

    public DefinedOperationParser(Operation operation) {
        this.operation = operation;
        undefinedOperandWasNotFound = true;
    }

    public DefinedOperation parseToDefinedOperation() {
        return parseOperation(operation);
    }

    private DefinedOperation parseOperation(Operation operationToParse) {
        if (operationIsAlreadyDefined(operationToParse)) {
            return (DefinedOperation) operationToParse;
        } else {
            undefinedOperationsList = (UndefinedOperationsList) operationToParse;
            return parseUndefinedOperation();
        }
    }

    private boolean operationIsAlreadyDefined(Operation operationToParse) {
        return operationToParse instanceof DefinedOperation;
    }

    private DefinedOperation parseUndefinedOperation() {
        if (hasNoSiblingsButHasChildren()) {
            return parseOperation(undefinedOperationsList.get(0));
        } else {
            searchForUndefinedOperation();
        }
        return convertOperationIntoOperand();
    }

    private boolean hasNoSiblingsButHasChildren() {
        return undefinedOperationsList.size() == 1;
    }

    private void searchForUndefinedOperation() {
        undefinedOperationsList.toEnd();
        while (canSearchForPreviousOperation()) {
            checkIfPreviousOperationIsUndefined();
        }
    }

    private boolean canSearchForPreviousOperation() {
        return undefinedOperationsList.hasPrevious() && undefinedOperandWasNotFound;
    }

    private void checkIfPreviousOperationIsUndefined() {
        operationNow = undefinedOperationsList.previous();
        if (operationNow instanceof SingleUndefinedOperation) {
            undefinedOperandWasNotFound = false;
        }
    }

    private OperandOperation convertOperationIntoOperand() {
        undefinedOperandWasNotFound = false;
        createLeftAndRightOperations();
        return OPERATION_FACTORY.getOperandOperation(
                (SingleUndefinedOperation) operationNow,
                leftOperation,
                rightOperation);
    }

    private void createLeftAndRightOperations() {
        leftOperation = new DefinedOperationParser(undefinedOperationsList.getLeftSubList())
                .parseToDefinedOperation();
        rightOperation = new DefinedOperationParser(undefinedOperationsList.getRightSubList())
                .parseToDefinedOperation();
    }

    public UndefinedOperationsList getUndefinedOperationGroup() {
        return (UndefinedOperationsList) operation;
    }
}
