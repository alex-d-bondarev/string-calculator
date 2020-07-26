package org.practice.app.operation;

import org.junit.Test;
import org.practice.app.operation.parsed.*;

import static org.junit.Assert.assertEquals;

public class DefinedOperationTest {

    private static final double DELTA = 0;
    private static final double ONE = 1d;
    private static final double TEN = 10d;
    private static final double TWENTY = 20d;

    @Test
    public void testNumber() {
        DefinedOperation testNumber = new NumberOperation(TEN);
        assertEquals(TEN, testNumber.evaluate(), DELTA);
    }

    @Test
    public void testSimpleSum() {
        double expectedResult = 11d;
        DefinedOperation firstNumber = new NumberOperation(TEN);
        DefinedOperation secondNumber = new NumberOperation(ONE);
        DefinedOperation sum = new SumOperation(firstNumber, secondNumber);

        assertEquals(expectedResult, sum.evaluate(), DELTA);
    }

    @Test
    public void testSimpleDifference() {
        double expectedResult = 9d;
        DefinedOperation firstNumber = new NumberOperation(TEN);
        DefinedOperation secondNumber = new NumberOperation(ONE);
        DefinedOperation difference = new DifferenceOperation(firstNumber, secondNumber);

        assertEquals(expectedResult, difference.evaluate(), DELTA);
    }

    @Test
    public void testSimpleMultiplication() {
        double expectedResult = 200d;
        DefinedOperation firstNumber = new NumberOperation(TWENTY);
        DefinedOperation secondNumber = new NumberOperation(TEN);
        DefinedOperation multiplication = new MultiplicationOperation(firstNumber, secondNumber);

        assertEquals(expectedResult, multiplication.evaluate(), DELTA);
    }

    @Test
    public void testSimpleDivision() {
        double expectedResult = 2d;
        DefinedOperation firstNumber = new NumberOperation(TWENTY);
        DefinedOperation secondNumber = new NumberOperation(TEN);
        DefinedOperation division = new DivisionOperation(firstNumber, secondNumber);

        assertEquals(expectedResult, division.evaluate(), DELTA);
    }

    @Test
    public void testComplexExpression() {
        // "(20 * ((10 + 1) - 1))/20 = 10"
        DefinedOperation firstNumber = new NumberOperation(TWENTY);
        DefinedOperation secondNumber = new NumberOperation(TEN);
        DefinedOperation thirdNumber = new NumberOperation(ONE);

        DefinedOperation sum = new SumOperation(secondNumber, thirdNumber);
        DefinedOperation difference = new DifferenceOperation(sum, thirdNumber);
        DefinedOperation multiplication = new MultiplicationOperation(firstNumber, difference);
        DefinedOperation division = new DivisionOperation(multiplication, firstNumber);

        assertEquals(TEN, division.evaluate(), DELTA);
    }
}
