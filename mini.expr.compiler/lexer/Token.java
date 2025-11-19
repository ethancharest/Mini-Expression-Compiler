/**
 * A Token represents one unit produced by the lexer
 * 
 * Example:
 * - "42" = NUMBER token
 * - "+" = PLUS token
 * - ")" = LPAREN token
 * 
 * Each token has:
 * - its type (from TokenType)
 * - its original text (lexeme)
 * - a numeric value (for NUMBER tokens)
 * - its position in the source string
 */

public class Token {

    private final TokenType type;
    private final String lexeme;
    private final Double numericValue;  // only positive values
    private final int position;         // index in original input string

    /**
     * General constructor for tokens
     * 
     * @param type          what kind of token this is
     * @param lexeme        the exact substring from the input
     * @param numericValue  parsed numeric value (only for NUMBER, otherwise null)
     * @param position      starting character index of this token in the input
     */
    public Token(TokenType type, String lexeme, Double numericValue, int position) {
        this.type = type;
        this.lexeme = lexeme;
        this.numericValue = numericValue;
        this.position = position; 
    }

    // Convienence constructor for non-number tokens 
    public Token(TokenType type, String lexeme, int position) {
        this(type, lexeme, null, position);
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    /**
     * @return the numeric value of the token, or null if non-number
     */
    public Double getNumericValue() {
        return numericValue;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        if (type == TokenType.NUMBER) {
            return String.format("Token(%s, lexeme '%s', value=%s, pos=%d" , type, lexeme, numericValue, position);
        } else {
            return String.format("Token(%s, lexeme '%s', pos=%d" , type, lexeme, position);
        }
    }
}