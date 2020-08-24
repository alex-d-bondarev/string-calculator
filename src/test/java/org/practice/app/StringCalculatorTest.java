package org.practice.app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {
    private StringCalculator calc;

    @Before
    public void setup() {
        calc = new StringCalculator();
    }

    @Test
    public void charsAreUnexpected() {
        String testExpression = "abc";
        String expectedResult = "Given expression 'abc' contains unexpected symbols.";
        assertCalculatorEvaluation(testExpression, expectedResult);
    }

    @Test
    public void unsupportedOperandsAreUnexpected() {
        String testExpression = "2^2";
        String expectedResult = "Given expression '2^2' contains unexpected symbols.";
        assertCalculatorEvaluation(testExpression, expectedResult);
    }

    @Test
    public void unbalancedBracketsAreUnexpected() {
        String testExpression = "2 + 2)";
        String expectedResult = "Given expression '2+2)' has unbalanced parenthesis.";
        assertCalculatorEvaluation(testExpression, expectedResult);
    }

    @Test
    public void simpleNumberIsAcceptable() {
        String testExpression = "10";
        String expectedResult = "10.0";
        assertCalculatorEvaluation(testExpression, expectedResult);
    }

    @Test
    public void simpleNumberWithBracketsIsAcceptable() {
        String testExpression = "{10}";
        String expectedResult = "10.0";
        assertCalculatorEvaluation(testExpression, expectedResult);
    }

    @Test
    public void simpleSum() {
        String testExpression = "2 + 2";
        String expectedResult = "4.0";
        assertCalculatorEvaluation(testExpression, expectedResult);
    }

    @Test
    public void simpleDifference() {
        String testExpression = "2 - 2";
        String expectedResult = "0.0";
        assertCalculatorEvaluation(testExpression, expectedResult);
    }

    @Test
    public void simpleMultiplication() {
        String expression = "2 * 2";
        String expectedResult = "4.0";
        assertCalculatorEvaluation(expression, expectedResult);
    }

    @Test
    public void simpleDivision() {
        String expression = "2 / 2";
        String expectedResult = "1.0";
        assertCalculatorEvaluation(expression, expectedResult);
    }

    @Test
    public void combinationOfOperands() {
        String expression = "1+2-3/4";
        String expectedResult = "2.25";
        assertCalculatorEvaluation(expression, expectedResult);
    }

    @Test
    public void multiplicationHappensBeforeSum() {
        String expression = "2 + 2 * 2";
        String expectedResult = "6.0";
        assertCalculatorEvaluation(expression, expectedResult);
    }

    @Test
    public void divisionHappensBeforeDifference() {
        String expression = "2 - 2 / 2";
        String expectedResult = "1.0";
        assertCalculatorEvaluation(expression, expectedResult);
    }

    @Test
    public void useAllSupportedSymbols() {
        String expression = "{3*4} - [2 + 3] + (9/3)";
        String expectedResult = "10.0";
        assertCalculatorEvaluation(expression, expectedResult);
    }

    @Test
    public void ensureAllNumbersToTheRightInvert() {
        String expression = "10 - 2 + 4 - 5 + 6";
        String expectedResult = "13.0";
        assertCalculatorEvaluation(expression, expectedResult);
    }

    @Test
    public void ensureAllButLastNumbersToTheRightInvert() {
        String expression = "10 - 2 - 4 + 5 - 6";
        String expectedResult = "3.0";
        assertCalculatorEvaluation(expression, expectedResult);
    }

    private void assertCalculatorEvaluation(String expression, String expectedResult) {
        String result = calc.evaluate(expression);
        assertEquals(expectedResult, result);
    }
}
