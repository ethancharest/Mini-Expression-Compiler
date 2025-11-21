package mini_expr_compiler.parser;

import mini_expr_compiler.lexer1.*;
import mini_expr_compiler.ast.*;
import java.text.ParseException;
import java.util.List;
/**
 * Parser for Mini Expression Compiler.
 *
 * Implements a recursive descent parser for the grammar:
 *
 *   E -> E + T | E - T | T
 *   T -> T * F | T / F | F
 *   F -> (E) | number
 *
 * Internally, we use the equivalent right-recursive / iterative form:
 *
 *   expression -> term ( (PLUS | MINUS) term )*
 *   term       -> factor ( (STAR | SLASH) factor )*
 *   factor     -> NUMBER
 *               | LPAREN expression RPAREN
 *               | MINUS factor      // unary minus
 *
 * The parser both validates the syntax, then builds an AST (expression tree).
 */
public class Parser {

    private final List<Token> tokens; 
    private int current = 0;    // index of current token

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public Expr parse() throws ParseException  {

        Expr expr = expression();

        // After parsing it should be at EOF
        if(!isAtEnd()){
            Token t = peek();
            throw error(t, "Unexpected token '" + t.getLexeme() + "' at position " + t.getPosition() + " after complete expression");
        }

        return expr;
    }

    // Grammar Rules 
    /**
     * expression --> term ( (PLUS | MINUS ))
     */
    private Expr expression() throws ParseException{
        Expr expr = term();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expr right = term();
            expr = new BinaryExpr(expr, operator, right); 
        }
      return expr;
    }

    /**
     * term -> factor ( (STAR | SLASH) )
     */
    private Expr term() throws ParseException {
        Expr expr = factor();

         while (match(TokenType.STAR, TokenType.SLASH)) {
            Token operator = previous();
            Expr right = term();
            expr = new BinaryExpr(expr, operator, right); 
        }
        return expr;
    }

    /**
     * factor -> NUMBER
     *         | LPAREN expression RPAREN
     *         | MINUS factor        (unary minus)
     */

    private Expr factor() throws ParseException {

        if(match(TokenType.MINUS)) {
            Token operator = previous();
            Expr right = factor();
            return new UnaryExpr(operator,right);
        }

        if(match(TokenType.NUMBER)) {
            Token numberToken = previous();
            int value = numberToken.getNumericValue();
            return new NumberExpr(value);
        }

        if(match(TokenType.LPAREN)) {
            Expr expr = expression();
            // Expects a closing ')' 
            consume(TokenType.RPAREN, "Expected ')' to match '(' at starting position " + previous().getPosition());
            return expr;
        }

        // if we reach here its an unexpected token
        Token t = peek();
        throw error(t, "Unexpected Token '" + t.getLexeme() + " at position " + t.getPosition());
    }

    // Helper Methods for token handling

    /**
     * returns true and consumes the current token if it matches any of the specified types 
     */

    private boolean match(TokenType... types) { //varargs for convience 
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true; 
            }
        }
        return false;
    }

    /**
     * Ensures current token has a type. if not throw ParseException
     */
    private Token consume(TokenType type, String message) throws ParseException {
        if (check(type)) {
            return advance();
        }
        throw error(peek(), message);
    }

    /**
     * Checks if the current token has a type 
     * @param type  Type of token 
     * @return type 
     */
    private boolean check(TokenType type) {
        if(isAtEnd()) {
            return false;
        }
        return peek().getType() == type;
    }

    /**
     * Consumes the current token
     */
    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    /**
     * @return true if reached the EOF token 
     */
    private boolean isAtEnd(){
        return peek().getType() == TokenType.EOF;
    }

    /**
     * @return the current token 
     */
    private Token peek() {
        return tokens.get(current);
    }

    /**
     * @return the most recently consumed token
     */
    private Token previous() {
        return tokens.get(current - 1);
    }

    // error handling, can be modified
    private ParseException error(Token token, String message){
        return new ParseException(message,current);
    }
}
