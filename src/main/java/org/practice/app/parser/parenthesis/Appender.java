package org.practice.app.parser.parenthesis;

import org.practice.app.operation.raw.UndefinedOperationsList;

public abstract class Appender {
    protected boolean foundPositionForNewParenthesis;
    protected int appendedOperationPosition;
    protected int newParenthesisPosition;
    protected UndefinedOperationsList undefinedOperationsList;

    public void append(UndefinedOperationsList undefinedOperationsList) {
        initializeSearchForNewParenthesisPosition(undefinedOperationsList);

        while (newParenthesisPositionNotFound()) {
            checkForNewParenthesisPosition();
        }

        addParenthesis();
    }

    protected void initializeSearchForNewParenthesisPosition(UndefinedOperationsList undefinedOperationsList){
        this.undefinedOperationsList = undefinedOperationsList;
        foundPositionForNewParenthesis = false;
    }

    protected abstract boolean newParenthesisPositionNotFound();

    protected abstract void checkForNewParenthesisPosition();

    protected abstract void addParenthesis();
}
