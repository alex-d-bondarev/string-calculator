package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;

public class ClosingParenthesisAppender extends Appender {
    private int openingParenthesisAmount;

    @Override
    protected boolean newParenthesisPositionNotFound() {
        return undefinedOperationsList.hasNext() && !foundPositionForNewParenthesis;
    }

    @Override
    protected void initializeSearchForNewParenthesisPosition(UndefinedOperationsList undefinedOperationsList) {
        super.initializeSearchForNewParenthesisPosition(undefinedOperationsList);

        appendedOperationPosition = undefinedOperationsList.getPosition();
        newParenthesisPosition = undefinedOperationsList.size();
        openingParenthesisAmount = 0;
    }

    @Override
    protected void checkForNewParenthesisPosition() {
        Operation operation = undefinedOperationsList.next();

        if (operation instanceof UndefinedOperation) {
            UndefinedOperation undefinedOperation = (UndefinedOperation) operation;

            if (undefinedOperation.getValue() == Parenthesis.OPENING) {
                openingParenthesisAmount++;
            } else if (openingParenthesisAmount > 0 && undefinedOperation.getValue() == Parenthesis.CLOSING) {
                openingParenthesisAmount--;
            } else if (openingParenthesisAmount == 0) {
                newParenthesisPosition = undefinedOperationsList.getPosition();
                foundPositionForNewParenthesis = true;
            }
        }
    }

    @Override
    protected void addParenthesis() {
        undefinedOperationsList.add(newParenthesisPosition, new SingleUndefinedOperation(Parenthesis.CLOSING));
        undefinedOperationsList.setPosition(appendedOperationPosition);
    }
}
