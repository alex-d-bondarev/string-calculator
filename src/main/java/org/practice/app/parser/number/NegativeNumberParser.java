package org.practice.app.parser.number;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;

public class NegativeNumberParser extends AbstractNumberParser {
    private static final char DASH_CHAR = '-';
    private UndefinedOperationsList undefinedOperationsList;

    public NegativeNumberParser(UndefinedOperationsList undefinedOperationsList) {
        this.undefinedOperationsList = undefinedOperationsList;
    }

    public UndefinedOperationsList getUndefinedOperationGroup() {
        return undefinedOperationsList;
    }

    public PositiveNumberParser parseNegativeNumbers() {
        undefinedOperationsList.toStart();

        while (undefinedOperationsList.hasNext()) {
            ifOperationIsNegativeNumber_thenMakeItNumberOperation(undefinedOperationsList.next());
        }

        return new PositiveNumberParser(undefinedOperationsList);
    }

    private void ifOperationIsNegativeNumber_thenMakeItNumberOperation(Operation currentOperation) {
        if (currentOperation instanceof UndefinedOperation) {
            if (negativeNumberStartsFrom(currentOperation)) {
                undefinedOperationsList = replaceCurrentUndefinedOperationInGroup_WithNumberOperation(undefinedOperationsList);
            }
        }
    }

    private boolean negativeNumberStartsFrom(Operation currentUndefinedOperation) {
        return (((UndefinedOperation) currentUndefinedOperation).getValue() == DASH_CHAR
                && previousOperationIsNotDigit(undefinedOperationsList)
                && nextOperationIsDigit(undefinedOperationsList));
    }

    private boolean previousOperationIsNotDigit(UndefinedOperationsList group) {
        if (group.hasPrevious() && group.getPrevious() instanceof UndefinedOperation) {
            return !operationIsDigit((UndefinedOperation) group.getPrevious());
        }
        return true;
    }

    private boolean nextOperationIsDigit(UndefinedOperationsList group) {
        return group.hasNext()
                && group.getNext() instanceof UndefinedOperation
                && operationIsDigit((UndefinedOperation) group.getNext());

    }

    private boolean operationIsDigit(UndefinedOperation operation) {
        return Character.isDigit(operation.getValue());
    }
}
