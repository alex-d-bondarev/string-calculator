package org.practice.app.parser;

import org.practice.app.operation.Operation;
import org.practice.app.operation.parsed.DefinedOperation;
import org.practice.app.operation.parsed.DifferenceOperation;
import org.practice.app.operation.parsed.DivisionOperation;
import org.practice.app.operation.parsed.MultiplicationOperation;
import org.practice.app.operation.parsed.SumOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;
import org.practice.app.operation.raw.UndefinedOperationGroup;

public class DefinedOperationParser {
    private UndefinedOperationGroup undefinedOperationGroup;

    public DefinedOperationParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public DefinedOperation parseToDefinedOperation() {
        return parseOperationsToDefinedOperation(undefinedOperationGroup);
    }

    private DefinedOperation parseOperationsToDefinedOperation(Operation operationToParse) {
        if (operationToParse instanceof DefinedOperation) {
            return (DefinedOperation) operationToParse;
        } else {
            UndefinedOperationGroup undefinedGroup = (UndefinedOperationGroup) operationToParse;
            if (undefinedGroup.getSize() == 1) {
                return parseOperationsToDefinedOperation(undefinedGroup.getOperations().get(0));
            } else {
                undefinedGroup.toEnd();
                Operation operationNow;

                while (undefinedGroup.hasPrevious()) {
                    operationNow = undefinedGroup.previous();
                    if (operationNow instanceof SingleUndefinedOperation) {
                        SingleUndefinedOperation operand = (SingleUndefinedOperation) operationNow;

                        UndefinedOperationGroup left = undefinedGroup.getLeftSubGroup();
                        UndefinedOperationGroup right = undefinedGroup.getRightSubGroup();

                        DefinedOperation leftOperation = parseOperationsToDefinedOperation(left);
                        DefinedOperation rightOperation = parseOperationsToDefinedOperation(right);

                        switch (operand.getValue()) {
                            case '+':
                                return new SumOperation(leftOperation, rightOperation);
                            case '-':
                                return new DifferenceOperation(leftOperation, rightOperation);
                            case '*':
                                return new MultiplicationOperation(leftOperation, rightOperation);
                            case '/':
                                return new DivisionOperation(leftOperation, rightOperation);
                            default:
                                throw new RuntimeException("Unexpected operation: " + operand);
                        }
                    }
                }
                throw new RuntimeException("Unexpected group of operations: " + undefinedGroup);
            }
        }
    }
}
