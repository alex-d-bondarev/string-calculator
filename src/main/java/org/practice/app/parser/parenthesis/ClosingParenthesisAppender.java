package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class ClosingParenthesisAppender extends Appender {
    private int openingParenthesisAmount;

    @Override
    protected boolean newParenthesisPositionNotFound() {
        return undefinedOperationGroup.hasNext() && !foundPositionForNewParenthesis;
    }

    @Override
    protected void initializeSearchForNewParenthesisPosition(UndefinedOperationGroup undefinedOperationGroup) {
        super.initializeSearchForNewParenthesisPosition(undefinedOperationGroup);

        appendedOperationPosition = undefinedOperationGroup.getPosition();
        newParenthesisPosition = undefinedOperationGroup.size();
        openingParenthesisAmount = 0;
    }

    @Override
    protected void checkForNewParenthesisPosition() {
        Operation operation = undefinedOperationGroup.next();

        if (operation instanceof UndefinedOperation) {
            UndefinedOperation undefinedOperation = (UndefinedOperation) operation;

            if (undefinedOperation.getValue() == Parenthesis.OPENING) {
                openingParenthesisAmount++;
            } else if (openingParenthesisAmount > 0 && undefinedOperation.getValue() == Parenthesis.CLOSING) {
                openingParenthesisAmount--;
            } else if (openingParenthesisAmount == 0) {
                newParenthesisPosition = undefinedOperationGroup.getPosition();
                foundPositionForNewParenthesis = true;
            }
        }
    }

    @Override
    protected void addParenthesis() {
        undefinedOperationGroup.addOperationTo(new SingleUndefinedOperation(Parenthesis.CLOSING), newParenthesisPosition);
        undefinedOperationGroup.setPosition(appendedOperationPosition);
    }
}
