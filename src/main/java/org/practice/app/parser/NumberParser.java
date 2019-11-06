package org.practice.app.parser;

import org.practice.app.data_structure.SubListIndex;
import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class NumberParser {
    private static final char DASH_CHAR = '-';
    private UndefinedOperationGroup undefinedOperationGroup;

    public NumberParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }

    public ParenthesisForPriorityOperandsParser getPriorityOperandsParser() {
        return new ParenthesisForPriorityOperandsParser(undefinedOperationGroup);
    }

    public NumberParser parseNegativeNumbers() {
        undefinedOperationGroup.toStart();
        UndefinedOperation currentUndefinedOperation;
        boolean previousIsDigit;
        boolean nextIsDigit;
        Operation currentOperation;

        while (undefinedOperationGroup.hasNext()) {
            currentOperation = undefinedOperationGroup.next();

            if (currentOperation instanceof UndefinedOperation) {

                currentUndefinedOperation = (UndefinedOperation) currentOperation;
                previousIsDigit = isPreviousOperationADigit(undefinedOperationGroup);
                nextIsDigit = isNextOperationADigit(undefinedOperationGroup);

                if (currentUndefinedOperation.getValue() == DASH_CHAR && !previousIsDigit && nextIsDigit) {
                    undefinedOperationGroup = replaceNumberUndefinedOperationsWithNumberOperation(
                            undefinedOperationGroup, currentUndefinedOperation);
                }
            }
        }

        return this;
    }

    private boolean isPreviousOperationADigit(UndefinedOperationGroup group) {
        boolean previousIsDigit = false;
        if (group.hasPrevious() && group.getPrevious() instanceof UndefinedOperation) {
            previousIsDigit = Character.isDigit(((UndefinedOperation) group.getPrevious()).getValue());
        }
        return previousIsDigit;
    }

    private boolean isNextOperationADigit(UndefinedOperationGroup group) {
        boolean nextIsDigit = false;
        if (group.hasNext() && group.getNext() instanceof UndefinedOperation) {
            nextIsDigit = Character.isDigit(((UndefinedOperation) group.getNext()).getValue());
        }
        return nextIsDigit;
    }


    // TODO: move to different class
    public NumberParser parsePositiveNumbers() {
        undefinedOperationGroup.toStart();
        UndefinedOperation currentUndefinedOperation;
        Operation currentOperation;

        while (undefinedOperationGroup.hasNext()) {
            currentOperation = undefinedOperationGroup.next();

            if (currentOperation instanceof UndefinedOperation) {

                currentUndefinedOperation = (UndefinedOperation) currentOperation;
                if (Character.isDigit(currentUndefinedOperation.getValue())) {
                    undefinedOperationGroup = replaceNumberUndefinedOperationsWithNumberOperation(
                            undefinedOperationGroup, currentUndefinedOperation);
                }
            }
        }

        return this;
    }


    // TODO: move to abstract parent class
    private UndefinedOperationGroup replaceNumberUndefinedOperationsWithNumberOperation(UndefinedOperationGroup group,
                                                                                        UndefinedOperation currentOperation) {
        SubListIndex index;
        int from, to;
        String number;

        from = group.getPosition();
        number = extractNumberStartingWithGivenOperation(group, currentOperation);
        to = from + number.length();
        index = new SubListIndex(from, to);

        return replaceSublistOfUndefinedOperationsWithNumberOperation(group, index, number);
    }

    private String extractNumberStartingWithGivenOperation(UndefinedOperationGroup group,
                                                           UndefinedOperation operation) {
        String number = Character.toString(operation.getValue());

        while (group.hasNext()) {
            char operationValue = ((UndefinedOperation) group.next()).getValue();

            if (Character.isDigit(operationValue)) {
                number += operationValue;
            } else {
                break;
            }
        }
        return number;
    }

    private UndefinedOperationGroup replaceSublistOfUndefinedOperationsWithNumberOperation(UndefinedOperationGroup group,
                                                                                           SubListIndex index,
                                                                                           String number) {
        NumberOperation numberOperation = new NumberOperation(Double.parseDouble(number));
        return group.replaceBetween(numberOperation, index);
    }
}
