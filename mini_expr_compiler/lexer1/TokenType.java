package mini_expr_compiler.lexer1;
/**
 * TokenType lists every type of token that can appear in Mini Expression Compiler
 * The lexer will read characters and classify them into one of these token types.
 */
public enum TokenType {
    // Literals
    NUMBER,     // '3'

    // Single-character operators 
    PLUS,       // '+'
    MINUS,      // '-'
    STAR,       // '*'
    SLASH,      // '/'

    // Parentheses
    LPAREN,    // '('
    RPAREN,    // ')'

    // Special marke used to signal end-of-input
    EOF
}