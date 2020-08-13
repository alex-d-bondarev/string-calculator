package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class ParenthesisForPriorityOperandsParser {
    private static final String PRIORITY_OPERANDS = "*/";

    private final UndefinedOperationGroup undefinedOperationGroup;
    private final OpeningParenthesisAppender openingParenthesis;
    private final ClosingParenthesisAppender closingParenthesis;

    public ParenthesisForPriorityOperandsParser(UndefinedOperationGroup operationsGroup) {
        undefinedOperationGroup = operationsGroup;
        openingParenthesis = new OpeningParenthesisAppender();
        closingParenthesis = new ClosingParenthesisAppender();
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
        openingParenthesis.append(undefinedOperationGroup);
        closingParenthesis.append(undefinedOperationGroup);
    }

    private boolean thisIsPriorityOperand(Operation currentOperation) {
        return PRIORITY_OPERANDS.indexOf(((UndefinedOperation) currentOperation).getValue()) >= 0;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }
}
