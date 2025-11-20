/**
 * AST node representing a binary operation (left (operator) right)
 * Example: 5 + 6 or (3 + 2) + 5
 */

public class BinaryExpr implements Expr {
    private final Expr left;
    private final Token operator; // PLUS, MINUS, STAR, SLASH
    private final Expr right; 

    public BinaryExpr(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expr getLeft(){
        return left;
    }

    public Token getOperator(){
        return operator;
    }

    public Expr getRight(){
        return right;
    }
}
