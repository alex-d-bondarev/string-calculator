package org.practice.app.parser.number;

import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public abstract class AbstractNumberParser {

    private UndefinedOperationGroup group;
    private String textNumber;
    private int from;
    private int to;

    protected UndefinedOperationGroup replaceCurrentUndefinedOperationInGroup_WithNumberOperation(UndefinedOperationGroup group) {
        this.group = group;

        calculateSubListIndex();
        return replaceSublistOfUndefinedOperationsWithNumberOperation();
    }

    private void calculateSubListIndex() {
        from = group.getPosition();
        extractTextNumberStartingWithGivenOperation();
        to = from + textNumber.length();
    }

    private void extractTextNumberStartingWithGivenOperation() {
        textNumber = Character.toString(((UndefinedOperation) group.getCurrent()).getValue());

        while (group.hasNext()) {
            char operationValue = ((UndefinedOperation) group.next()).getValue();

            if (Character.isDigit(operationValue)) {
                textNumber += operationValue;
            } else {
                break;
            }
        }
    }

    private UndefinedOperationGroup replaceSublistOfUndefinedOperationsWithNumberOperation() {
        NumberOperation numberOperation = new NumberOperation(Double.parseDouble(textNumber));
        return group.replaceByIndex(numberOperation, from, to);
    }
}
