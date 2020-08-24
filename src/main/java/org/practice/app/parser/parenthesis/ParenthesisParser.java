package org.practice.app.parser.parenthesis;

import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;
import org.practice.app.parser.operations.DefinedOperationParser;

import java.util.ArrayList;
import java.util.List;

public class ParenthesisParser {
    private UndefinedOperationsList undefinedOperationsList;
    private Operation operation;

    private int parenthesisStart = 0;
    private int parenthesisEnd = 0;

    public ParenthesisParser(UndefinedOperationsList undefinedOperationsList) {
        this.undefinedOperationsList = undefinedOperationsList;
    }

    public DefinedOperationParser parseParenthesis() {
        startFromBeginning();
        while (undefinedOperationsList.hasNext()) {
            updateParenthesisGroups();
        }

        return new DefinedOperationParser(undefinedOperationsList);
    }

    private void updateParenthesisGroups() {
        operation = undefinedOperationsList.next();

        if (notANumber()) {
            ifParenthesis_thenUpdateParenthesisPosition();
            ifParenthesisGroupIdentified_thenConvertToSubgroup();
        }
    }

    private boolean notANumber() {
        return operation instanceof UndefinedOperation;
    }

    private void ifParenthesisGroupIdentified_thenConvertToSubgroup() {
        if (parenthesisSurroundsOnly1Number()) {
            removeExtraParenthesis();
            startFromBeginning();
        } else if (parenthesisStart < parenthesisEnd) {
            convertExpressionInBracketsIntoSubgroup();
            startFromBeginning();
        }
    }

    private void convertExpressionInBracketsIntoSubgroup() {
        List<Operation> subOperations = new ArrayList<>(
                undefinedOperationsList.subList(parenthesisStart + 1, parenthesisEnd));

        undefinedOperationsList = undefinedOperationsList
                .replace(new UndefinedOperationsList(subOperations), parenthesisStart, parenthesisEnd + 1);
    }

    private boolean parenthesisSurroundsOnly1Number() {
        return parenthesisEnd - parenthesisStart == 2;
    }

    private void removeExtraParenthesis() {
        undefinedOperationsList = undefinedOperationsList.replace(
                undefinedOperationsList.getPrevious(), parenthesisStart, parenthesisEnd + 1);
    }

    private void ifParenthesis_thenUpdateParenthesisPosition() {
        UndefinedOperation undefinedOperation = (UndefinedOperation) operation;
        if (undefinedOperation.getValue() == Parenthesis.OPENING) {
            updateParenthesisStart();
        } else if (undefinedOperation.getValue() == Parenthesis.CLOSING) {
            updateParenthesisEnd();
        }
    }

    private void updateParenthesisEnd() {
        parenthesisEnd = undefinedOperationsList.getPosition();
    }

    private void updateParenthesisStart() {
        parenthesisStart = undefinedOperationsList.getPosition();
    }

    private void startFromBeginning() {
        undefinedOperationsList.toStart();
        parenthesisStart = 0;
        parenthesisEnd = 0;
    }

    public UndefinedOperationsList getUndefinedOperationGroup() {
        return undefinedOperationsList;
    }
}
