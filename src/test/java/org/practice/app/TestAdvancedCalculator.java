package org.practice.app;

import org.junit.Test;
import org.practice.app.parser.ProcessorException;

import static org.junit.Assert.assertEquals;


public class TestAdvancedCalculator {
    private static final AdvancedCalculator calc = new AdvancedCalculator();
    private static final double DELTA = 0d;

    @Test
    public void charsAreUnexpected() {
        String testExpression = "abc";
        String expectedResult = "Given expression 'abc' contains unexpected symbols.";
        CalculationResult result = calc.evaluate(testExpression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void unsupportedOperandsAreUnexpected() {
        String testExpression = "2^2";
        String expectedResult = "Given expression '2^2' contains unexpected symbols.";
        CalculationResult result = calc.evaluate(testExpression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void unbalancedBracketsAreUnexpected() {
        String testExpression = "2 + 2)";
        String expectedResult = "Given expression '2 + 2)' has unbalanced brackets.";
        CalculationResult result = calc.evaluate(testExpression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void simpleNumberIsAcceptable() {
        String testExpression = "10";
        String expectedResult = "10.0";
        CalculationResult result = calc.evaluate(testExpression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void simpleNumberWithBracketsIsAcceptable() {
        String testExpression = "{10}";
        String expectedResult = "10.0";
        CalculationResult result = calc.evaluate(testExpression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void simpleSum() {
        String testExpression = "2 + 2";
        String expectedResult = "4.0";
        CalculationResult result = calc.evaluate(testExpression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void simpleDifference(){
        String testExpression = "2 - 2";
        String expectedResult = "0.0";
        CalculationResult result = calc.evaluate(testExpression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void simpleMultiplication() throws ProcessorException {
        String expression = "2 * 2";
        String expectedResult = "4.0";
        CalculationResult result = calc.evaluate(expression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void simpleDivision() throws ProcessorException {
        String expression = "2 / 2";
        String expectedResult = "1.0";
        CalculationResult result = calc.evaluate(expression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void combinationOfOperands() throws ProcessorException {
        String expression = "1+2-3/4";
        String expectedResult = "2.25";
        CalculationResult result = calc.evaluate(expression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void multiplicationHappensBeforeSum() throws ProcessorException {
        String expression = "2 + 2 * 2";
        String expectedResult = "6.0";
        CalculationResult result = calc.evaluate(expression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void divisionHappensBeforeDifference() throws ProcessorException {
        String expression = "2 - 2 / 2";
        String expectedResult = "1.0";
        CalculationResult result = calc.evaluate(expression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void useAllSupportedSymbols() throws ProcessorException {
        String expression = "{3*4} - [2 + 3] + (9/3)";
        String expectedResult = "10.0";
        CalculationResult result = calc.evaluate(expression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void ensureAllNumbersToTheRightInvert() throws ProcessorException {
        String expression = "10 - 2 + 4 - 5 + 6";
        String expectedResult = "13.0";
        CalculationResult result = calc.evaluate(expression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void ensureAllButLastNumbersToTheRightInvert() throws ProcessorException {
        String expression = "10 - 2 - 4 + 5 - 6";
        String expectedResult = "3.0";
        CalculationResult result = calc.evaluate(expression);

        assertEquals(expectedResult, result.getResult());
    }
}
