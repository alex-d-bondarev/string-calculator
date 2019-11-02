package org.practice.app.parser;

import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class OperationParser {
    private static final char MINUS_CHAR = '-';
    private UndefinedOperationGroup undefinedOperationGroup;

    public OperationParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }

    public OperationParser parseNegativeNumbers() {
        undefinedOperationGroup.toStart();
        UndefinedOperation currentUndefinedOperation;
        boolean previousIsDigit;

        while (undefinedOperationGroup.hasNext()) {
            currentUndefinedOperation = (UndefinedOperation) undefinedOperationGroup.next();
            previousIsDigit = isPreviousOperationADigit();

            if (currentUndefinedOperation.getValue() == MINUS_CHAR && !previousIsDigit) {
                replaceCurrentUndefinedOperationContainingNumbersWithNumberOperation(currentUndefinedOperation);
            }
        }

        return this;
    }

    public OperationParser parsePositiveNumbers() {
        undefinedOperationGroup.toStart();
        UndefinedOperation currentUndefinedOperation;
        Operation currentOperation;

        while (undefinedOperationGroup.hasNext()) {
            currentOperation = undefinedOperationGroup.next();

            if(currentOperation instanceof UndefinedOperation) {

                currentUndefinedOperation = (UndefinedOperation) currentOperation;
                if (Character.isDigit(currentUndefinedOperation.getValue())) {
                    replaceCurrentUndefinedOperationContainingNumbersWithNumberOperation(currentUndefinedOperation);
                }
            }
        }

        return this;
    }

    private void replaceCurrentUndefinedOperationContainingNumbersWithNumberOperation(UndefinedOperation currentOperation) {
        int start, end;
        String number;

        start = undefinedOperationGroup.getPosition();
        number = extractNumberStartingWithGivenOperation(currentOperation);
        end = start + number.length();

        replaceUndefinedOperationsBetweenWithNumberOperationFrom(start, end, number);
    }

    private boolean isPreviousOperationADigit() {
        boolean previousIsDigit = false;
        if (undefinedOperationGroup.hasPrevious() && undefinedOperationGroup.getPrevious() instanceof UndefinedOperation) {
            previousIsDigit = Character.isDigit(((UndefinedOperation) undefinedOperationGroup.getPrevious()).getValue());
        }
        return previousIsDigit;
    }

    private String extractNumberStartingWithGivenOperation(UndefinedOperation operation) {
        String number = Character.toString(operation.getValue());

        while (undefinedOperationGroup.hasNext()) {
            operation = (UndefinedOperation) undefinedOperationGroup.next();

            if (Character.isDigit(operation.getValue())) {
                number += operation.getValue();
            } else {
                break;
            }
        }
        return number;
    }

    private void replaceUndefinedOperationsBetweenWithNumberOperationFrom(int start, int end, String number) {
        NumberOperation negativeNumberOperation = new NumberOperation(Double.parseDouble(number));
        undefinedOperationGroup.replaceBetween(negativeNumberOperation, start, end);
    }
}
