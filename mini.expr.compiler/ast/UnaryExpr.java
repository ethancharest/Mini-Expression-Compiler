
/**
 * AST node representing a unary operation, currently only:
 * -()
 */
public class UnaryExpr implements Expr {
    private final Token operator;
    private final Expr right;

    public UnaryExpr(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }

    public Token getOperator() {
        return operator;
    }

    public Expr getRight() {
        return right;
    }
}
