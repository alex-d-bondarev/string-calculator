package org.practice.app.parser;

import org.practice.app.operation.parsed.NumberOperation;
import org.practice.app.operation.raw.UndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class OperationParser {
    private UndefinedOperationGroup undefinedOperationGroup;

    public OperationParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }

    public OperationParser parseNegativeNumbers() {
        undefinedOperationGroup.toStart();
        UndefinedOperation currentOperation = (UndefinedOperation) undefinedOperationGroup.current();
        int start, end, currentPositionExcluder = 1;
        String negativeNumber = "";
        boolean previousIsDigit = false;

        while (undefinedOperationGroup.hasNext()){
            if (undefinedOperationGroup.hasPrevious()){
                previousIsDigit = Character.isDigit(((UndefinedOperation)undefinedOperationGroup.getPrevious()).getValue());
            }

            if (currentOperation.getValue() == '-' && !previousIsDigit){
                end = start = undefinedOperationGroup.getPosition();
                negativeNumber += currentOperation.getValue();

                while (undefinedOperationGroup.hasNext()){
                    currentOperation = (UndefinedOperation) undefinedOperationGroup.next();

                    end = undefinedOperationGroup.getPosition() + currentPositionExcluder;

                    if(Character.isDigit(currentOperation.getValue())){
                        negativeNumber += currentOperation.getValue();
                    } else {
                        break;
                    }
                }

                NumberOperation negativeNumberOperation = new NumberOperation(Double.parseDouble(negativeNumber));
                undefinedOperationGroup.replaceBetween(negativeNumberOperation, start, end);
            } else {
                currentOperation = (UndefinedOperation) undefinedOperationGroup.next();
            }
        }

        return this;
    }
}
