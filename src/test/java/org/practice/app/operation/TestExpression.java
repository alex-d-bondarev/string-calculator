package org.practice.app.operation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestExpression {

    private static final double DELTA = 0;
    private static final double ONE = 1d;
    private static final double TEN = 10d;
    private static final double TWENTY = 20d;

    @Test
    public void testNumber(){
        Expression testNumber = new NumberExpression(TEN);
        assertEquals(TEN, testNumber.evaluate(), DELTA);
    }

    @Test
    public void testSimpleSum(){
        double expectedResult    = 11d;
        Expression firstNumber = new NumberExpression(TEN);
        Expression secondNumber = new NumberExpression(ONE);
        Expression sum = new SumExpression(firstNumber, secondNumber);

        assertEquals(expectedResult, sum.evaluate(), DELTA);
    }

    @Test
    public void testSimpleDifference(){
        double expectedResult = 9d;
        Expression firstNumber = new NumberExpression(TEN);
        Expression secondNumber = new NumberExpression(ONE);
        Expression difference = new DifferenceExpression(firstNumber, secondNumber);

        assertEquals(expectedResult, difference.evaluate(), DELTA);
    }

    @Test
    public void testSimpleMultiplication(){
        double expectedResult = 200d;
        Expression firstNumber = new NumberExpression(TWENTY);
        Expression secondNumber = new NumberExpression(TEN);
        Expression multiplication = new MultiplicationExpression(firstNumber, secondNumber);

        assertEquals(expectedResult, multiplication.evaluate(), DELTA);
    }

    @Test
    public void testSimpleDivision(){
        double expectedResult = 2d;
        Expression firstNumber = new NumberExpression(TWENTY);
        Expression secondNumber = new NumberExpression(TEN);
        Expression division = new DivisionExpression(firstNumber, secondNumber);

        assertEquals(expectedResult, division.evaluate(), DELTA);
    }

    @Test
    public void testComplexExpression(){
        // "(20 * ((10 + 1) - 1))/20 = 10"
        Expression firstNumber = new NumberExpression(TWENTY);
        Expression secondNumber = new NumberExpression(TEN);
        Expression thirdNumber = new NumberExpression(ONE);

        Expression sum = new SumExpression(secondNumber, thirdNumber);
        Expression difference = new DifferenceExpression(sum, thirdNumber);
        Expression multiplication = new MultiplicationExpression(firstNumber, difference);
        Expression division = new DivisionExpression(multiplication, firstNumber);

        assertEquals(TEN, division.evaluate(), DELTA);
    }
}
