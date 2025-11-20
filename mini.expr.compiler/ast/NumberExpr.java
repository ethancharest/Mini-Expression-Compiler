/**
 * AST node representing an integer ("3","59","86")
 */
public class NumberExpr implements Expr {

    private final int value;

    public NumberExpr(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
