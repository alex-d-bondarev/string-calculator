package org.practice.app.parser.parenthesis;

import org.practice.app.operation.raw.UndefinedOperationGroup;

public abstract class Appender {
    protected static final char OPENING_PARENTHESIS = '(';
    protected static final char CLOSING_PARENTHESIS = ')';

    protected boolean foundPositionForNewParenthesis;
    protected int appendedOperationPosition;
    protected int newParenthesisPosition;
    protected UndefinedOperationGroup undefinedOperationGroup;

    public void append(UndefinedOperationGroup undefinedOperationGroup) {
        initializeSearchForNewParenthesisPosition(undefinedOperationGroup);

        while (newParenthesisPositionNotFound()) {
            checkForNewParenthesisPosition();
        }

        addParenthesis();
    }

    protected void initializeSearchForNewParenthesisPosition(UndefinedOperationGroup undefinedOperationGroup){
        this.undefinedOperationGroup = undefinedOperationGroup;
        foundPositionForNewParenthesis = false;
    }

    protected abstract boolean newParenthesisPositionNotFound();

    protected abstract void checkForNewParenthesisPosition();

    protected abstract void addParenthesis();
}
