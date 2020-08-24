package org.practice.app.parser.number;

import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationsList;

public abstract class AbstractNumberParser {

    private UndefinedOperationsList operations;
    private String textNumber;
    private int from;
    private int to;

    protected UndefinedOperationsList replaceCurrentUndefinedOperationInGroup_WithNumberOperation(UndefinedOperationsList operations) {
        this.operations = operations;

        calculateSubListIndex();
        return replaceSublistOfUndefinedOperationsWithNumberOperation();
    }

    private void calculateSubListIndex() {
        from = operations.getPosition();
        extractTextNumberStartingWithGivenPosition();
        to = from + textNumber.length();
    }

    private void extractTextNumberStartingWithGivenPosition() {
        boolean isNumberNow = true;
        textNumber = Character.toString(((UndefinedOperation) operations.getCurrentOperation()).getValue());

        while (operations.hasNext() && isNumberNow) {
            char operationValue = ((UndefinedOperation) operations.next()).getValue();

            if (Character.isDigit(operationValue)) {
                textNumber += operationValue;
            } else {
                isNumberNow = false;
            }
        }
    }

    private UndefinedOperationsList replaceSublistOfUndefinedOperationsWithNumberOperation() {
        NumberOperation numberOperation = new NumberOperation(Double.parseDouble(textNumber));
        return operations.replace(numberOperation, from, to);
    }
}
