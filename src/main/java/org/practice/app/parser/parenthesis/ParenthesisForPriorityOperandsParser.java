package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;

public class ParenthesisForPriorityOperandsParser {
    private static final String PRIORITY_OPERANDS = "*/";

    private final UndefinedOperationsList undefinedOperationsList;
    private final OpeningParenthesisAppender openingParenthesis;
    private final ClosingParenthesisAppender closingParenthesis;

    public ParenthesisForPriorityOperandsParser(UndefinedOperationsList operationsGroup) {
        undefinedOperationsList = operationsGroup;
        openingParenthesis = new OpeningParenthesisAppender();
        closingParenthesis = new ClosingParenthesisAppender();
    }

    public ParenthesisParser parsePriorityOperands() {
        undefinedOperationsList.toStart();

        while (undefinedOperationsList.hasNext()) {
            ifNextOperationIsPriorityOperand_thenSurroundWithParenthesis();
        }

        return new ParenthesisParser(undefinedOperationsList);
    }

    private void ifNextOperationIsPriorityOperand_thenSurroundWithParenthesis() {
        Operation operation = undefinedOperationsList.next();
        if (operation instanceof SingleUndefinedOperation
                && thisIsPriorityOperand(operation)) {
            surroundWithParenthesis();
        }
    }

    private void surroundWithParenthesis() {
        openingParenthesis.append(undefinedOperationsList);
        closingParenthesis.append(undefinedOperationsList);
    }

    private boolean thisIsPriorityOperand(Operation currentOperation) {
        return PRIORITY_OPERANDS.indexOf(((UndefinedOperation) currentOperation).getValue()) >= 0;
    }

    public UndefinedOperationsList getUndefinedOperationGroup() {
        return undefinedOperationsList;
    }
}
