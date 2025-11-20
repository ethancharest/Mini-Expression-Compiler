
/**
 * AST node representing a unary operation, currently only:
 * -()
 */
public class UnaryExpr {
    private final Token operator;
    private final Expr right;

    private UnaryExpr(Token operator, Expr right) {
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
