package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;

public class OpeningParenthesisAppender extends Appender {

    @Override
    protected boolean newParenthesisPositionNotFound() {
        return undefinedOperationsList.hasPrevious() && !foundPositionForNewParenthesis;
    }

    @Override
    protected void initializeSearchForNewParenthesisPosition(UndefinedOperationsList undefinedOperationsList) {
        super.initializeSearchForNewParenthesisPosition(undefinedOperationsList);

        appendedOperationPosition = undefinedOperationsList.getPosition() + 1;
        newParenthesisPosition = 0;
    }

    @Override
    protected void checkForNewParenthesisPosition() {
        Operation operation = undefinedOperationsList.previous();

        if (operation instanceof UndefinedOperation) {
            newParenthesisPosition = undefinedOperationsList.getPosition() + 1;
            foundPositionForNewParenthesis = true;
        }
    }

    @Override
    protected void addParenthesis() {
        undefinedOperationsList.add(newParenthesisPosition, new SingleUndefinedOperation(Parenthesis.OPENING));
        undefinedOperationsList.setPosition(appendedOperationPosition);
    }
}
