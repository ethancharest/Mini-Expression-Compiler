/**
 * checked exception type used to signal syntax errors when parsing 
 */
public class ParseException extends Exception{
    public ParseException(String message) {
        super(message);
    }
}
