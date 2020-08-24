package org.practice.app.parser.number;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;
import org.practice.app.parser.parenthesis.ParenthesisForPriorityOperandsParser;

public class PositiveNumberParser extends AbstractNumberParser {
    private UndefinedOperationsList undefinedOperationsList;

    public PositiveNumberParser(UndefinedOperationsList undefinedOperationsList) {
        this.undefinedOperationsList = undefinedOperationsList;
    }

    public UndefinedOperationsList getUndefinedOperationGroup() {
        return undefinedOperationsList;
    }

    public ParenthesisForPriorityOperandsParser parsePositiveNumbers() {
        undefinedOperationsList.toStart();

        while (undefinedOperationsList.hasNext()) {
            ifOperationIsNumber_thenMakeItNumberOperation(undefinedOperationsList.next());
        }

        return new ParenthesisForPriorityOperandsParser(undefinedOperationsList);
    }

    private void ifOperationIsNumber_thenMakeItNumberOperation(Operation currentOperation) {
        if (currentOperation instanceof UndefinedOperation) {
            if (isDigit(currentOperation)) {
                undefinedOperationsList = replaceCurrentUndefinedOperationInGroup_WithNumberOperation(undefinedOperationsList);
            }
        }
    }

    private boolean isDigit(Operation currentOperation) {
        return Character.isDigit(((UndefinedOperation) currentOperation).getValue());
    }
}
