package mini_expr_compiler.lexer1;


import java.util.ArrayList;
import java.util.List;

/**
 * The Lexer  takes a raw string input like: (24 + 36) * 78 - 2
 * and converts it into a sequence of token objects (e.g NUMBER(12), PLUS(3),...)
 */
public class Lexer {

    private final String input;
    private final int length;
    private int current = 0;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();
    }

    /**
     * Converts the entire input string into a list of tokens
     * 
     * @return list of tokens ending with an EOF token
     * @throws RuntimeException if an unexpected character is encountered 
     */

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            char c = peek();

            // Remember where this token starts (for error messages)
            int startPos = current;

            if (Character.isWhitespace(c)) {
                // Skip spaces, tabs, newlines, etc.
                advance();
                continue;
            }

            // Number literal (we'll treat all digits as integers and store as double)
            if (Character.isDigit(c)) {
                tokens.add(numberToken());
                continue;
            }

            // Single-character tokens (operators and parentheses)
            switch(c) {
                case '+':
                    tokens.add(new Token(TokenType.PLUS, "+", startPos));
                    advance();
                    break;
                case '-':
                    tokens.add(new Token(TokenType.MINUS, "-", startPos));
                    advance();
                    break;
                case '*':
                    tokens.add(new Token(TokenType.STAR, "*", startPos));
                    advance();
                    break;
                case '/':
                    tokens.add(new Token(TokenType.SLASH, "/", startPos));
                    advance();
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LPAREN, "(", startPos));
                    advance();
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPAREN, ")", startPos));
                    advance();
                    break;
                default:
                    // Anything else would be an error
                    throw new RuntimeException("Unexpected character '" + c + "' at position " + startPos);
            }
        }

        // Final EOF token to mark end-of-input
        tokens.add(new Token(TokenType.EOF, "", current));
        return tokens;
    }

    /**
     * Handles a sequence of digits and returns a NUMBER token
     * "321 -> NUMBER(321.0)"
     */
    private Token numberToken() {
        int start = current;

        // Consume all consecutive digits
        while (!isAtEnd() && Character.isDigit(peek())) {
            advance();
        }

        String lexeme = input.substring(start, current);
        int value = Integer.parseInt(lexeme);

        return new Token(TokenType.NUMBER, lexeme, value, start);
    }

    /**
     * @return true if weve consumed all characters in the input
     */
    private boolean isAtEnd() {
        return current >= length;
    }

    /**
     * @return the current character without consuming it
     */
    private char peek() {
        return input.charAt(current);
    }

    /**
     * Consumes and returns the current character, then moves forward one step
     */
    private char advance() {
        char c = input.charAt(current);
        current++;
        return c;
    }

    public static String formatTokenList(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        sb.append("["); 

        boolean first = true;
        for (Token t: tokens) {
            if (t.getType() == TokenType.EOF) {
              continue;  
            }
            if (!first) sb.append(",");
            sb.append(t.getLexeme());
            first = false; 
        }

        sb.append("]");
        return sb.toString();
    }
}