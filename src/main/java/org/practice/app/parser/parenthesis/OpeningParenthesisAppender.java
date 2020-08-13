package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class OpeningParenthesisAppender extends Appender {
    private int closingParenthesisAmount;

    @Override
    protected boolean newParenthesisPositionNotFound() {
        return undefinedOperationGroup.hasPrevious() && !foundPositionForNewParenthesis;
    }

    @Override
    protected void initializeSearchForNewParenthesisPosition(UndefinedOperationGroup undefinedOperationGroup) {
        super.initializeSearchForNewParenthesisPosition(undefinedOperationGroup);

        appendedOperationPosition = undefinedOperationGroup.getPosition() + 1;
        newParenthesisPosition = 0;
        closingParenthesisAmount = 0;
    }

    @Override
    protected void checkForNewParenthesisPosition() {
        Operation operation = undefinedOperationGroup.previous();

        if (operation instanceof UndefinedOperation) {
            UndefinedOperation undefinedOperation = (UndefinedOperation) operation;

            if (undefinedOperation.getValue() == CLOSING_PARENTHESIS) {
                closingParenthesisAmount++;
            } else if (closingParenthesisAmount > 0 && undefinedOperation.getValue() == OPENING_PARENTHESIS) {
                closingParenthesisAmount--;
            } else {
                newParenthesisPosition = undefinedOperationGroup.getPosition() + 1;
                foundPositionForNewParenthesis = true;
            }
        }
    }

    @Override
    protected void addParenthesis() {
        undefinedOperationGroup.addOperationTo(new SingleUndefinedOperation(OPENING_PARENTHESIS), newParenthesisPosition);
        undefinedOperationGroup.setPosition(appendedOperationPosition);
    }
}
