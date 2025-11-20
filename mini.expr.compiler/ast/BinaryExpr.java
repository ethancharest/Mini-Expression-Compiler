/**
 * AST node representing a binary operation (left (operator) right)
 * Example: 5 + 6 or (3 + 2) + 5
 */

public class BinaryExpr implements Expr {
    private final Expr left;    // left side of the binary operation
    private final Token operator; // PLUS, MINUS, STAR, SLASH
    private final Expr right;   //right side of the binary operation

    /**
     * Constructs a new BinaryExpr node
     * @param left      the expression on the left side of the operator
     * @param operator  the operator token between left and right
     * @param right     the expression on the right side 
     */
    public BinaryExpr(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    /**
     * @return the left child expression of this binary operation
     */
    public Expr getLeft(){
        return left;
    }

    /**
     * @return the operator token for this binary expression
     */
    public Token getOperator(){
        return operator;
    }

    /**
     * @return the right child expression of this binary operation
     */
    public Expr getRight(){
        return right;
    }
}
