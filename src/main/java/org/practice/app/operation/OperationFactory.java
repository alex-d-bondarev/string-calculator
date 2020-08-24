package org.practice.app.operation;

import org.practice.app.operation.parsed.DefinedOperation;
import org.practice.app.operation.parsed.DifferenceOperation;
import org.practice.app.operation.parsed.DivisionOperation;
import org.practice.app.operation.parsed.MultiplicationOperation;
import org.practice.app.operation.parsed.OperandOperation;
import org.practice.app.operation.parsed.SumOperation;
import org.practice.app.operation.raw.SingleUndefinedOperation;

public class OperationFactory {
    public OperandOperation getOperandOperation(SingleUndefinedOperation operand, DefinedOperation leftOperation, DefinedOperation rightOperation) {
        switch (operand.getValue()) {
            case '+':
                return new SumOperation(leftOperation, rightOperation);
            case '-':
                return new DifferenceOperation(leftOperation, rightOperation);
            case '*':
                return new MultiplicationOperation(leftOperation, rightOperation);
            case '/':
            default:
                return new DivisionOperation(leftOperation, rightOperation);
        }
    }
}
