# The Bling Parser

This project builds a parser for a simple little expression language called "bling".

The Bling language consists entirely of:

  * Numeric Values (like `0` or `1.25` or `-10e9`)
  * Arithmetic Operators (like `+`, `-`, `*`, `/`, and `^`)
  * Parentheses used for grouping sub-expressions

The langauge parser should skip common whitespace characters (like space, tab, and newlines).

It should also correctly implement the standard mathematical order-of-operations, where the
exponentiation operator (`^`) binds more tightly than multiplicative operators (`*` and `/`), which
bind more tightly than the additive operators (`+` and `-`).

Here are a few examples of typical Bling expressions we'd like to parse and evaluate:

```
1
1 + 2
1 + 2 + 3
2 * 3 + 4 ^ 5
2 * (3 + 4) ^ 5
3.25 - 2.125
2.5e2 / 1.25e-2
-1 - -2
```

We can design a recursive-descent parser for the Bling language, where the order-of-operations
are enforced by the hierarchy of the grammar. Here's a basic EBNF grammar that does the trick:

```
WHITESPACE:
  ( " " | "\t" | "\n" | "\r" )

Expression:
  AdditiveExpression
  <EOF>

AdditiveExpression:
  MultiplicativeExpression
  (
    ( "+" | "-" )
    MultiplicativeExpression
  )*

MultiplicativeExpression:
  ExponentialExpression
  (
    ( "*" | "/" )
    ExponentialExpression
  )*

ExponentialExpression:
  NegatableExpression
  (
    "^"
    NegatableExpression
  )*

NegatableExpression:
  ( "-" )?
  PrimeExpression

PrimeExpression:
  ( NumberLiteral | GroupedExpression )

NumberLiteral:
  (["0"-"9"])+
  (
    "."
    (["0"-"9"])+
  )?
  (
    ["e","E"]
    (["+","-"])?
    (["0"-"9"])+
  )?

GroupedExpression:
  "("
  AdditiveExpression
  ")"
```