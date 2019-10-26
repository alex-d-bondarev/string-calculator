package org.practice.app;

import org.junit.Test;
import org.practice.app.parser.ProcessorException;

import static org.junit.Assert.assertEquals;


public class TestAdvancedCalculator {
    private static final AdvancedCalculator calc = new AdvancedCalculator();
    private static final double DELTA = 0d;

    @Test
    public void charsAreUnexpected(){
        String testExpression = "abc";
        String expectedResult = "Given expression 'abc' contains unexpected symbols.";
        CalculationResult result = calc.evaluate(testExpression);

        assertEquals(expectedResult, result.getResult());
    }

    @Test
    public void unsupportedOperandsAreUnexpected(){
        String testExpression = "2^2";
        String expectedResult = "Given expression '2^2' contains unexpected symbols.";
        CalculationResult result = calc.evaluate(testExpression);

        assertEquals(expectedResult, result.getResult());
    }

    // All tests below are planned for deprecation
    @Test(expected = ProcessorException.class)
    public void unbalancedParenthesisNotAllowed() throws ProcessorException {
        String expression = "(10";
        calc.calculate(expression);
    }

    @Test(expected = ProcessorException.class)
    public void nonSimpleCalculationSymbolsNotAllowed() throws ProcessorException {
        String expression = "3^2";
        calc.calculate(expression);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void negativeNumbersAreNotSupported() throws ProcessorException {
        String expression = "1*-2";
        calc.calculate(expression);
    }

    @Test
    public void simpleNumberIsAcceptable() throws ProcessorException {
        String expression = "10";
        double result = 10d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void simpleNumberWithBracketsIsAcceptable() throws ProcessorException {
        String expression = "{10}";
        double result = 10d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void simpleSum() throws ProcessorException {
        String expression = "2 + 2";
        double result = 4d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void simpleDifference() throws ProcessorException {
        String expression = "2 - 2";
        double result = 0;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void simpleMultiplication() throws ProcessorException {
        String expression = "2 * 2";
        double result = 4d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void simpleDivision() throws ProcessorException {
        String expression = "2 / 2";
        double result = 1d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void combinationOfOperands() throws ProcessorException {
        String expression = "1+2-3/4";
        double result = 2.25d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void multiplicationHappensBeforeSum() throws ProcessorException {
        String expression = "2 + 2 * 2";
        double result = 6d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void divisionHappensBeforeDifference() throws ProcessorException {
        String expression = "2 - 2 / 2";
        double result = 1d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void useAllSupportedSymbols() throws ProcessorException {
        String expression = "{3*4} - [2 + 3] + (9/3)";
        double result = 10d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void ensureAllNumbersToTheRightInvert() throws ProcessorException {
        String expression = "10 - 2 + 4 - 5 + 6";
        double result = 13d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }

    @Test
    public void ensureAllButLastNumbersToTheRightInvert() throws ProcessorException {
        String expression = "10 - 2 - 4 + 5 - 6";
        double result = 3d;

        assertEquals(result, calc.calculate(expression), DELTA);
    }
}
