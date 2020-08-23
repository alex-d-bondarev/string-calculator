package org.practice.app.parser.parenthesis;

import org.practice.app.data_structure.SubListIndex;
import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;
import org.practice.app.parser.operations.DefinedOperationParser;

import java.util.ArrayList;
import java.util.List;

public class ParenthesisParser {
    private UndefinedOperationGroup undefinedOperationGroup;
    private Operation operation;
    private SubListIndex index;

    private int parenthesisStart = 0;
    private int parenthesisEnd = 0;

    public ParenthesisParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public DefinedOperationParser parseParenthesis() {
        startFromBeginning();
        while (undefinedOperationGroup.hasNext()) {
            updateParenthesisGroups();
        }

        return new DefinedOperationParser(undefinedOperationGroup);
    }

    private void updateParenthesisGroups() {
        operation = undefinedOperationGroup.next();

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

    private void updateParenthesisIndex() {
        index = new SubListIndex(parenthesisStart, parenthesisEnd + 1);
    }

    private void convertExpressionInBracketsIntoSubgroup() {
        List<Operation> subOperations = new ArrayList<>(
                undefinedOperationGroup.subGroup(parenthesisStart + 1, parenthesisEnd));

        undefinedOperationGroup = undefinedOperationGroup
                .replaceByIndex(new UndefinedOperationGroup(subOperations), index);
    }

    private boolean parenthesisSurroundsOnly1Number() {
        return parenthesisEnd - parenthesisStart == 2;
    }

    private void removeExtraParenthesis() {
        undefinedOperationGroup = undefinedOperationGroup.replaceByIndex(
                undefinedOperationGroup.getPrevious(), index);
    }

    private void ifParenthesis_thenUpdateParenthesisPosition() {
        UndefinedOperation undefinedOperation = (UndefinedOperation) operation;
        if (undefinedOperation.getValue() == Parenthesis.OPENING) {
            updateParenthesisStart();
        } else if (undefinedOperation.getValue() == Parenthesis.CLOSING) {
            updateParenthesisEnd();
        }
        updateParenthesisIndex();
    }

    private void updateParenthesisEnd() {
        parenthesisEnd = undefinedOperationGroup.getPosition();
    }

    private void updateParenthesisStart() {
        parenthesisStart = undefinedOperationGroup.getPosition();
    }

    private void startFromBeginning() {
        undefinedOperationGroup.toStart();
        parenthesisStart = 0;
        parenthesisEnd = 0;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }
}
