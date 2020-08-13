package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class ClosingParenthesisAppender extends Appender{

    public void append(UndefinedOperationGroup undefinedOperationGroup) {
        int operationIndex = undefinedOperationGroup.getPosition();
        int openingParenthesisAmount = 0;
        int positionForNewParenthesis = undefinedOperationGroup.getOperations().size();
        Operation operationNow;

        while (undefinedOperationGroup.hasNext()) {
            operationNow = undefinedOperationGroup.next();

            if (operationNow instanceof UndefinedOperation) {
                UndefinedOperation undefinedOperationNow = (UndefinedOperation) operationNow;

                if (undefinedOperationNow.getValue() == OPENING_PARENTHESIS) {
                    openingParenthesisAmount++;
                } else if (openingParenthesisAmount > 0 && undefinedOperationNow.getValue() == CLOSING_PARENTHESIS) {
                    openingParenthesisAmount--;
                } else if (openingParenthesisAmount == 0) {
                    positionForNewParenthesis = undefinedOperationGroup.getPosition();
                    break;
                }
            }
        }

        undefinedOperationGroup.addOperationTo(
                new SingleUndefinedOperation(CLOSING_PARENTHESIS),
                positionForNewParenthesis);
        undefinedOperationGroup.setPosition(operationIndex);
    }
}
