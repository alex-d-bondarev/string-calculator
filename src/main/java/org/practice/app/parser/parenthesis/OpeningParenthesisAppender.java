package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class OpeningParenthesisAppender extends Appender{

    public void append(UndefinedOperationGroup undefinedOperationGroup) {
        int closingParenthesisAmount = 0;
        int newOpeningParenthesisPosition = 0;
        int operationIndex = undefinedOperationGroup.getPosition() + 1;
        Operation operation;

        while (undefinedOperationGroup.hasPrevious()) {
            operation = undefinedOperationGroup.previous();

            if (operation instanceof UndefinedOperation) {
                UndefinedOperation undefinedOperation = (UndefinedOperation) operation;

                if (undefinedOperation.getValue() == CLOSING_PARENTHESIS) {
                    closingParenthesisAmount++;
                } else if (closingParenthesisAmount > 0 && undefinedOperation.getValue() == OPENING_PARENTHESIS) {
                    closingParenthesisAmount--;
                } else {
                    newOpeningParenthesisPosition = undefinedOperationGroup.getPosition() + 1;
                    break;
                }
            }
        }

        undefinedOperationGroup.addOperationTo(new SingleUndefinedOperation(OPENING_PARENTHESIS), newOpeningParenthesisPosition);
        undefinedOperationGroup.setPosition(operationIndex);
    }
}
