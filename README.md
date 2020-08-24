string-calculator
=================

Calculate expression of a given string.

You can copy and reuse code [![MIT License](http://img.shields.io/badge/license-MIT-green.svg)](https://github.com/selenide/selenide/blob/master/LICENSE)

This calculator parses given String expression and calculates its result. Input string may contain the following:
- Positive integers
- Negative integers
- Sum **+**
- Difference **-**
- Multiplication *
- Division **/**
- Opening and closing brackets _(are processed as equal)_ **(** **)**, **{** **}** and **\[** **\]**  
- Spaces

## How to use

Fill `AdvancedCalculator` with expression that should be evaluated:

```java
String result = new AdvancedCalculator().evaluate("2 + 2");
```
Evaluated result is expected to contain one of the following:
1. Decimal number as a String
1. Unexpected symbols error. Like: `"Given expression '%s' contains unexpected symbols."`
1. Unbalanced parenthesis error. Like: `"Given expression '%s' has unbalanced parenthesis."`.

For examples please check [StringCalculatorTest](./src/test/java/org/practice/app/StringCalculatorTest.java) 
or other project tests for more details.

## How it works

1. Prepare expression for being parsed
    1. Remove all spaces
    1. Replace all different kinds of brackets with parenthesis
1. Verify given expression is valid
    1. Ensure no extra symbols were provided
    1. Ensure parentheses are balanced
1.  Parse expression into more specific operations:
    1.  Convert each symbol into a List of undefined operations. 
        For example: `1+-1` will become `1,+,-,1`.
    1.  Parse all negative numbers into `NumberOperation`.
        For example: `1,+,-,1` will become `1,+,-1`.
    1.  Parse all positive numbers into `NumberOperation`.
    1.  Surround multiplication and division operands with parentheses to simplify further parsing.
        For example `1,+,-1,*,2` will become `1,+,(,-1,*,2,)`.
    1.  Group all operands to simplify further parsing.
        For example `1,+,(,-1,*,2,)` will become `(,1,+,(,-1,*,2,),)`.
    1.  Parse received expression from right to left\* into specific operations as a tree.
        For example `(,1,+,(,-1,*,2,),)` will become:
        ```
          +
         / \
        1   *
           / \
         -1   2
        ```
1.  Evaluate received operations tree into expression result

\* **Note**:

```text
We can parse from left to right. This will result into the following cases:
1. expression like "10 - 2 + 4 - 5 + 6 = 13" will be calculated as "10 - (2 + (4 - (5 + 6))) = 15"
1. expression like "10 - 2 - 4 + 5 - 6 = 3" will be calculated as "10 - (2 - (4 + (5 - 6))) = 11"

There are 2 possible options to overcome this issue:
1. Update parsed results afterwards and invert all operands to the left of each difference operand
1. Parse from right to left.

Second option is easier and simplifies the code:
1. expression like "10 - 2 + 4 - 5 + 6 = 13" will be calculated as "((((10 - 2) + 4) - 5) + 6) = 13"
1. expression like "10 - 2 - 4 + 5 - 6 = 3" will be calculated as "((((10 - 2) - 4) + 5) - 6) = 3"
```

## Maintenance

**Code changes should not break existing tests.**   
Tests are triggered during project build. Build status should be _BUILD SUCCESS_
```bash
mvn clean test
```
**Test coverage is not necessary 100%, but should be high:**  
Can be checked through IDE. Current coverage is:
- Class: 95%
- Method: 100%
- Line: 98%

**Test mutation should be at high level:**
```bash
mvn -DwithHistory org.pitest:pitest-maven:mutationCoverage
```
Current score is:
- Line Coverage = 99%
- Mutation Coverage = 93%
