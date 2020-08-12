package org.practice.app.parser;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class NegativeNumberParser extends AbstractNumberParser {
    private static final char DASH_CHAR = '-';
    private UndefinedOperationGroup undefinedOperationGroup;

    public NegativeNumberParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }

    public PositiveNumberParser parseNegativeNumbers() {
        undefinedOperationGroup.toStart();

        while (undefinedOperationGroup.hasNext()) {
            ifOperationIsNegativeNumber_thenMakeItNumberOperation(undefinedOperationGroup.next());
        }

        return new PositiveNumberParser(undefinedOperationGroup);
    }

    private void ifOperationIsNegativeNumber_thenMakeItNumberOperation(Operation currentOperation) {
        if (currentOperation instanceof UndefinedOperation) {
            if (negativeNumberStartsFrom(currentOperation)) {
                undefinedOperationGroup = replaceCurrentUndefinedOperationInGroup_WithNumberOperation(undefinedOperationGroup);
            }
        }
    }

    private boolean negativeNumberStartsFrom(Operation currentUndefinedOperation) {
        return (((UndefinedOperation) currentUndefinedOperation).getValue() == DASH_CHAR
                && previousOperationIsNotDigit(undefinedOperationGroup)
                && nextOperationIsDigit(undefinedOperationGroup));
    }

    private boolean previousOperationIsNotDigit(UndefinedOperationGroup group) {
        if (group.hasPrevious() && group.getPrevious() instanceof UndefinedOperation) {
            return !operationIsDigit((UndefinedOperation) group.getPrevious());
        }
        return true;
    }

    private boolean nextOperationIsDigit(UndefinedOperationGroup group) {
        if (group.hasNext() && group.getNext() instanceof UndefinedOperation) {
            return operationIsDigit((UndefinedOperation) group.getNext());
        }
        return false;
    }

    private boolean operationIsDigit(UndefinedOperation operation) {
        return Character.isDigit(operation.getValue());
    }
}
