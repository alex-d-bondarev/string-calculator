package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class ParenthesisForPriorityOperandsParser {
    private static final String PRIORITY_OPERANDS = "*/";
    private static final char OPENING_PARENTHESIS = '(';
    private static final char CLOSING_PARENTHESIS = ')';

    private final UndefinedOperationGroup undefinedOperationGroup;

    public ParenthesisForPriorityOperandsParser(UndefinedOperationGroup operationsGroup) {
        undefinedOperationGroup = operationsGroup;
    }

    public ParenthesisParser getParenthesisParser() {
        return new ParenthesisParser(undefinedOperationGroup);
    }

    public ParenthesisForPriorityOperandsParser parsePriorityOperands() {
        undefinedOperationGroup.toStart();

        while (undefinedOperationGroup.hasNext()) {
            ifNextOperationIsPriorityOperand_thenSurroundWithParenthesis();
        }

        return this;
    }

    private void ifNextOperationIsPriorityOperand_thenSurroundWithParenthesis() {
        Operation operation = undefinedOperationGroup.next();
        if (operation instanceof SingleUndefinedOperation
                && thisIsPriorityOperand(operation)) {
            surroundWithParenthesis();
        }
    }

    private void surroundWithParenthesis() {
        int operationIndex = undefinedOperationGroup.getPosition() + 1;
        addOpeningParenthesisToTheLeft(undefinedOperationGroup);
        undefinedOperationGroup.setPosition(operationIndex);
        addClosingParenthesisToTheRight(undefinedOperationGroup);
        undefinedOperationGroup.setPosition(operationIndex);
    }

    private boolean thisIsPriorityOperand(Operation currentOperation) {
        return PRIORITY_OPERANDS.indexOf(((UndefinedOperation) currentOperation).getValue()) >= 0;
    }

    private void addOpeningParenthesisToTheLeft(UndefinedOperationGroup undefinedOperationGroup) {
        int closingParenthesisAmount = 0;
        int newOpeningParenthesisPosition = 0;
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
                } else if (openingParenthesisAmount == 0) {
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
