package org.practice.app.parser.number;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;
import org.practice.app.parser.parenthesis.ParenthesisForPriorityOperandsParser;

public class PositiveNumberParser extends AbstractNumberParser {
    private UndefinedOperationGroup undefinedOperationGroup;

    public PositiveNumberParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }

    public ParenthesisForPriorityOperandsParser getPriorityOperandsParser() {
        return new ParenthesisForPriorityOperandsParser(undefinedOperationGroup);
    }

    public ParenthesisForPriorityOperandsParser parsePositiveNumbers() {
        undefinedOperationGroup.toStart();

        while (undefinedOperationGroup.hasNext()) {
            ifOperationIsNumber_thenMakeItNumberOperation(undefinedOperationGroup.next());
        }

        return new ParenthesisForPriorityOperandsParser(undefinedOperationGroup);
    }

    private void ifOperationIsNumber_thenMakeItNumberOperation(Operation currentOperation) {
        if (currentOperation instanceof UndefinedOperation) {
            if (isDigit(currentOperation)) {
                undefinedOperationGroup = replaceCurrentUndefinedOperationInGroup_WithNumberOperation(undefinedOperationGroup);
            }
        }
    }

    private boolean isDigit(Operation currentOperation) {
        return Character.isDigit(((UndefinedOperation) currentOperation).getValue());
    }
}
