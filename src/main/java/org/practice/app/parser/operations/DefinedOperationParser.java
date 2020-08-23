package org.practice.app.parser.operations;

import org.practice.app.operation.Operation;
import org.practice.app.operation.OperationFactory;
import org.practice.app.operation.parsed.DefinedOperation;
import org.practice.app.operation.parsed.OperandOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class DefinedOperationParser {
    private static final OperationFactory OPERATION_FACTORY = new OperationFactory();
    private boolean undefinedOperandWasNotFound;
    private final Operation operation;
    private Operation operationNow;
    private DefinedOperation leftOperation;
    private DefinedOperation rightOperation;
    private UndefinedOperationGroup undefinedOperationGroup;

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
            return parseUndefinedOperation((UndefinedOperationGroup) operationToParse);
        }
    }

    private boolean operationIsAlreadyDefined(Operation operationToParse) {
        return operationToParse instanceof DefinedOperation;
    }

    private DefinedOperation parseUndefinedOperation(UndefinedOperationGroup operationToParse) {
        undefinedOperationGroup = operationToParse;
        if (hasNoSiblingsButHasChildren()) {
            return parseOperation(undefinedOperationGroup.get(0));
        } else {
            searchForUndefinedOperation();
        }
        return convertOperationIntoOperand();
    }

    private boolean hasNoSiblingsButHasChildren() {
        return undefinedOperationGroup.size() == 1;
    }

    private void searchForUndefinedOperation() {
        undefinedOperationGroup.toEnd();
        while (canSearchForPreviousOperation()) {
            checkIfPreviousOperationIsUndefined();
        }
        if(undefinedOperandWasNotFound) {
            throw new RuntimeException("Unexpected group of operations: " + undefinedOperationGroup);
        }
    }

    private boolean canSearchForPreviousOperation() {
        return undefinedOperationGroup.hasPrevious() && undefinedOperandWasNotFound;
    }

    private void checkIfPreviousOperationIsUndefined() {
        operationNow = undefinedOperationGroup.previous();
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
        leftOperation = new DefinedOperationParser(undefinedOperationGroup.getLeftSubGroup())
                .parseToDefinedOperation();
        rightOperation = new DefinedOperationParser(undefinedOperationGroup.getRightSubGroup())
                .parseToDefinedOperation();
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return (UndefinedOperationGroup) operation;
    }
}
