package org.practice.app.parser.parenthesis;

import org.practice.app.data_structure.SubListIndex;
import org.practice.app.operation.Operation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;
import org.practice.app.parser.DefinedOperationParser;

import java.util.ArrayList;
import java.util.List;

public class ParenthesisParser {
    private static final char OPENING_PARENTHESIS = '(';
    private static final char CLOSING_PARENTHESIS = ')';

    private UndefinedOperationGroup undefinedOperationGroup;

    public ParenthesisParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public DefinedOperationParser getDefinedOperationParser() {
        return new DefinedOperationParser(undefinedOperationGroup);
    }

    public ParenthesisParser parseParenthesis() {
        undefinedOperationGroup.toStart();
        UndefinedOperation currentUndefinedOperation;
        Operation currentOperation;
        int parenthesisStart = 0;
        int parenthesisEnd = 0;

        while (undefinedOperationGroup.hasNext()) {
            currentOperation = undefinedOperationGroup.next();

            if(currentOperation instanceof UndefinedOperation) {

                currentUndefinedOperation = (UndefinedOperation) currentOperation;
                if (currentUndefinedOperation.getValue() == OPENING_PARENTHESIS) {
                    parenthesisStart = undefinedOperationGroup.getPosition();
                } else if (currentUndefinedOperation.getValue() == CLOSING_PARENTHESIS){
                    parenthesisEnd = undefinedOperationGroup.getPosition();
                }

                SubListIndex index = new SubListIndex(parenthesisStart, parenthesisEnd + 1);

                if(parenthesisEnd - parenthesisStart == 2){
                    undefinedOperationGroup = undefinedOperationGroup.replaceBetween(
                            undefinedOperationGroup.getPrevious(), index);
                    undefinedOperationGroup.toStart();
                    parenthesisStart = 0;
                    parenthesisEnd = 0;
                } else if (parenthesisStart < parenthesisEnd) {
                    List<Operation> subOperations = new ArrayList<>(
                            undefinedOperationGroup.getOperations().subList(parenthesisStart + 1, parenthesisEnd));

                    UndefinedOperationGroup newSubGroup = new UndefinedOperationGroup(subOperations);

                    undefinedOperationGroup = undefinedOperationGroup.replaceBetween(
                            newSubGroup, index);
                    undefinedOperationGroup.toStart();
                    parenthesisStart = 0;
                    parenthesisEnd = 0;
                }
            }
        }

        return this;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }
}