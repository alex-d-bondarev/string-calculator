string-calculator
=================

Calculate expression of a given string.

You can copy and reuse code [![MIT License](http://img.shields.io/badge/license-MIT-green.svg)](https://github.com/selenide/selenide/blob/master/LICENSE)

This calculator parses given String expression and calculates its result.
Negative numbers are not supported (at least for now).
Only sum (+), difference (-), multiplication (*) and division (/) operands are supported. 

How it works (in progress)
------------

1. [x] Remove all spaces
1. [x] Replace all different kinds of brackets with parenthesis
1. [ ] Make each symbol into a List of undefined operations
1. [ ] Parse undefined operations into more specific operations:
    1. [ ] Parse all negative numbers into `NumberOperation`
    1. [ ] Parse all positive numbers into `NumberOperation`
    1. [ ] Parse multiplication and division operands as priority operands into corresponding operations
    1. [ ] Parse sum and difference expressions as the rest from right to left\* into corresponding operations
1. [ ] Evaluate received operations tree into expression result

\* Note:

```text
We can parse from left to right. This will result into the following cases:
1. expression like "10 - 2 + 4 - 5 + 6 = 13" will be calculated as "10 - (2 + (4 - (5 + 6))) = 15"
1. expression like "10 - 2 - 4 + 5 - 6 = 3" will be calculated as "10 - (2 - (4 + (5 - 6))) = 11"

There are 2 possible options to overcome this issue:
1. Update parsed results afterwards and invert all operands to the left of each difference operand
1. Parse from right to left.

Second option is easier and avoids spaghetti code. The parsed expressions will look like:
1. expression like "10 - 2 + 4 - 5 + 6 = 13" will be calculated as "((((10 - 2) + 4) - 5) + 6) = 13"
1. expression like "10 - 2 - 4 + 5 - 6 = 3" will be calculated as "((((10 - 2) - 4) + 5) - 6) = 3"
```

