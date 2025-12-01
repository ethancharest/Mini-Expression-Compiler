Mini Expression Compiler

This project is a small expression language that supports integers, parentheses, unary minus, and the standard arithmetic operators (+, -, *, /). The program reads an expression from the user, tokenizes it, parses it into an abstract syntax tree (AST), prints the tree, and finally evaluates it.

The goal is to demonstrate the classic compiler phases (lexer → parser → AST → evaluator) in a compact and easy-to-follow way.

Features:

- Integer expression evaluation
- Proper operator precedence (*// before +/-)
- Parentheses support
- Unary minus (-3, -(1+2))
- Tree visualization using ASCII branches
- Clear error messages for syntax mistakes

Project Structure:

Main.java
- Runs the whole pipeline: reads input, tokenizes, parses, prints AST, evaluates.

Lexer.java
- Turns the raw input string into a list of tokens (numbers, operators, parens, EOF).

Parser.java
- Recursive-descent parser based on the grammar:

        expression → term (('+' | '-') term)*
        term       → factor (('*' | '/') factor)*
        factor     → NUMBER 
                | '(' expression ')'
                | '-' factor


- Builds the AST as Expr nodes.

AST Node Classes: 

- Expr – marker interface for all expression nodes

- NumberExpr – integer literal

- UnaryExpr – unary minus

- BinaryExpr – +, -, *, /

AstPrinter.java
- Prints the AST as a visual ASCII tree using / and \ branches.

Evaluator.java
- Recursively evaluates the AST and returns an integer result.

Sample Inputs & Outputs

Input:
(3 + 2) * 5 - 1

Output:
Tokens: [(, 3, +, 2, ), *, 5, -, 1]

Parse Tree:
        -
       / \
      *   1
     / \
    +   5
   / \
  3   2

Evaluation Result: 24

Input:
-(4 + 1) * 3

Output:
Parse Tree:
       *
      / \
     -   3
      \
       +
      / \
     4   1

Evaluation Result: -15

Bad Input:
3 + 

Output:

Parser Error: Unexpected token 'EOF' at position 3

Notes

This project isn’t a full compiler, but it implements the exact core ideas: lexical analysis, parsing, AST construction, tree visualization, and interpretation. It’s designed to be easy to read, and simple to extend.