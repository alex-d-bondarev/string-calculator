package org.practice.app.parser;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class PriorityOperandsParser {
    private static final String PRIORITY_OPERANDS = "*/";
    private static final char OPENING_PARENTHESIS = '(';
    private static final char CLOSING_PARENTHESIS = ')';

    private UndefinedOperationGroup undefinedOperationGroup;

    public PriorityOperandsParser(UndefinedOperationGroup operationsGroup) {
        undefinedOperationGroup = operationsGroup;
    }

    public ParenthesisParser getParenthesisParser(){
        return new ParenthesisParser(undefinedOperationGroup);
    }

    public PriorityOperandsParser parsePriorityOperands() {
        int continueIndex = 0;
        undefinedOperationGroup.toStart();
        UndefinedOperation currentUndefinedOperation;
        Operation currentOperation;

        while (undefinedOperationGroup.hasNext()) {
            currentOperation = undefinedOperationGroup.next();

            if (currentOperation instanceof SingleUndefinedOperation) {

                currentUndefinedOperation = (UndefinedOperation) currentOperation;
                if (PRIORITY_OPERANDS.indexOf(currentUndefinedOperation.getValue()) >= 0) {
                    continueIndex = undefinedOperationGroup.getPosition() + 1;
                    addOpeningParenthesisToTheLeft(undefinedOperationGroup);
                    undefinedOperationGroup.setPosition(continueIndex);
                    addClosingParenthesisToTheRight(undefinedOperationGroup);
                    undefinedOperationGroup.setPosition(continueIndex);
                }
            }
        }

        return this;
    }

    private void addOpeningParenthesisToTheLeft(UndefinedOperationGroup undefinedOperationGroup) {
        int closingParenthesisAmount = 0;
        int positionForNewParenthesis = 0;
        Operation operationNow;

        while (undefinedOperationGroup.hasPrevious()) {
            operationNow = undefinedOperationGroup.moveAndGetPrevious();

            if (operationNow instanceof UndefinedOperation) {
                UndefinedOperation undefinedOperationNow = (UndefinedOperation) operationNow;

                if (undefinedOperationNow.getValue() == CLOSING_PARENTHESIS) {
                    closingParenthesisAmount++;
                } else if (closingParenthesisAmount > 0 && undefinedOperationNow.getValue() == OPENING_PARENTHESIS) {
                    closingParenthesisAmount--;
                } else {
                    positionForNewParenthesis = undefinedOperationGroup.getPosition() + 1;
                    break;
                }
            }
        }

        undefinedOperationGroup.addOperationTo(
                new SingleUndefinedOperation(OPENING_PARENTHESIS),
                positionForNewParenthesis);
    }

    private void addClosingParenthesisToTheRight(UndefinedOperationGroup undefinedOperationGroup) {
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
                } else if (openingParenthesisAmount == 0){
                    positionForNewParenthesis = undefinedOperationGroup.getPosition();
                    break;
                }
            }
        }

        undefinedOperationGroup.addOperationTo(
                new SingleUndefinedOperation(CLOSING_PARENTHESIS),
                positionForNewParenthesis);
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }
}
