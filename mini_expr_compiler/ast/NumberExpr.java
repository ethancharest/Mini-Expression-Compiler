package mini_expr_compiler.ast;

/**
 * AST node representing an integer ("3","59","86")
 */
public class NumberExpr implements Expr {

    private final int value;

    /**
     * Constructs a new NumberExpr 
     * @param value the integer value this node represents 
     */
    public NumberExpr(int value){
        this.value = value;
    }

    /**
     * @return the numeric value of this number expression 
     */
    public int getValue() {
        return value;
    }
}
