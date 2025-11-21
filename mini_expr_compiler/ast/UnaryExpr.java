package mini_expr_compiler.ast;

 import mini_expr_compiler.lexer1.*;
/**
 * AST node representing a unary operation, currently only:
 * -()
 * UnaryExpr is similar to BinaryExpr, except it only has one child (right).
 * This is done to keep all expression types consistent and easy to traverse during printing and eval.
 */
public class UnaryExpr implements Expr {
    
    private final Token operator; // the operator token ("-"). Full token is stores to printer and eval can use its type / lexeme if needed
    private final Expr right; // the expression the operator applies to. (NumberExpr, UnaryExpr, BinaryExpr)

    /**
     * Creates a UnaryExpr node
     * @param operator  the unary operator token (MINUS)
     * @param right     the expression the operator is applied to
     */
    public UnaryExpr(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }

    /**
     * @return the operator for this unary expression
     */
    public Token getOperator() {
        return operator;
    }

    /**
     * @return the operand of the unary operaton
     */
    public Expr getRight() {
        return right;
    }
}
