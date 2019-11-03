package org.practice.app.parser;

import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class NumberParser {
    private static final char MINUS_CHAR = '-';
    private UndefinedOperationGroup undefinedOperationGroup;

    public NumberParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }

    public ParenthesisForPriorityOperandsParser getPriorityOperandsParser(){
        return new ParenthesisForPriorityOperandsParser(undefinedOperationGroup);
    }

    public NumberParser parseNegativeNumbers() {
        undefinedOperationGroup.toStart();
        UndefinedOperation currentUndefinedOperation;
        boolean previousIsDigit;
        boolean nextIsDigit;

        while (undefinedOperationGroup.hasNext()) {
            currentUndefinedOperation = (UndefinedOperation) undefinedOperationGroup.next();
            previousIsDigit = isPreviousOperationADigit();
            nextIsDigit = isNextOperationADigit();

            if (currentUndefinedOperation.getValue() == MINUS_CHAR && !previousIsDigit && nextIsDigit) {
                replaceCurrentUndefinedOperationContainingNumbersWithNumberOperation(currentUndefinedOperation);
            }
        }

        return this;
    }

    public NumberParser parsePositiveNumbers() {
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

    private boolean isNextOperationADigit() {
        boolean nextIsDigit = false;
        if (undefinedOperationGroup.hasNext() && undefinedOperationGroup.getNext() instanceof UndefinedOperation) {
            nextIsDigit = Character.isDigit(((UndefinedOperation) undefinedOperationGroup.getNext()).getValue());
        }
        return nextIsDigit;
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
