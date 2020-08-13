package org.practice.app.parser.number;

import org.practice.app.data_structure.SubListIndex;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public abstract class AbstractNumberParser {

    private UndefinedOperationGroup group;
    private SubListIndex subListIndex;
    String textNumber;

    protected UndefinedOperationGroup replaceCurrentUndefinedOperationInGroup_WithNumberOperation(UndefinedOperationGroup group) {

        this.group = group;

        calculateSubListIndex();
        return replaceSublistOfUndefinedOperationsWithNumberOperation();
    }

    private void calculateSubListIndex() {
        int from = group.getPosition();
        extractTextNumberStartingWithGivenOperation();
        int to = from + textNumber.length();
        subListIndex = new SubListIndex(from, to);
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
        return group.replaceBetween(numberOperation, subListIndex);
    }
}
