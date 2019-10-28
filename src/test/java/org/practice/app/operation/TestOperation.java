package org.practice.app.operation;

import org.junit.Test;
import org.practice.app.operation.parsed.*;

import static org.junit.Assert.assertEquals;

public class TestOperation {

    private static final double DELTA = 0;
    private static final double ONE = 1d;
    private static final double TEN = 10d;
    private static final double TWENTY = 20d;

    @Test
    public void testNumber(){
        Operation testNumber = new NumberOperation(TEN);
        assertEquals(TEN, testNumber.evaluate(), DELTA);
    }

    @Test
    public void testSimpleSum(){
        double expectedResult    = 11d;
        Operation firstNumber = new NumberOperation(TEN);
        Operation secondNumber = new NumberOperation(ONE);
        Operation sum = new SumOperation(firstNumber, secondNumber);

        assertEquals(expectedResult, sum.evaluate(), DELTA);
    }

    @Test
    public void testSimpleDifference(){
        double expectedResult = 9d;
        Operation firstNumber = new NumberOperation(TEN);
        Operation secondNumber = new NumberOperation(ONE);
        Operation difference = new DifferenceOperation(firstNumber, secondNumber);

        assertEquals(expectedResult, difference.evaluate(), DELTA);
    }

    @Test
    public void testSimpleMultiplication(){
        double expectedResult = 200d;
        Operation firstNumber = new NumberOperation(TWENTY);
        Operation secondNumber = new NumberOperation(TEN);
        Operation multiplication = new MultiplicationOperation(firstNumber, secondNumber);

        assertEquals(expectedResult, multiplication.evaluate(), DELTA);
    }

    @Test
    public void testSimpleDivision(){
        double expectedResult = 2d;
        Operation firstNumber = new NumberOperation(TWENTY);
        Operation secondNumber = new NumberOperation(TEN);
        Operation division = new DivisionOperation(firstNumber, secondNumber);

        assertEquals(expectedResult, division.evaluate(), DELTA);
    }

    @Test
    public void testComplexExpression(){
        // "(20 * ((10 + 1) - 1))/20 = 10"
        Operation firstNumber = new NumberOperation(TWENTY);
        Operation secondNumber = new NumberOperation(TEN);
        Operation thirdNumber = new NumberOperation(ONE);

        Operation sum = new SumOperation(secondNumber, thirdNumber);
        Operation difference = new DifferenceOperation(sum, thirdNumber);
        Operation multiplication = new MultiplicationOperation(firstNumber, difference);
        Operation division = new DivisionOperation(multiplication, firstNumber);

        assertEquals(TEN, division.evaluate(), DELTA);
    }
}
