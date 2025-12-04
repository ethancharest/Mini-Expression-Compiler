package mini_expr_compiler.eval;

import mini_expr_compiler.ast.*;

/**
 * This class implements a post-order tree traversal that recursively evaluates
 * the entire expression tree bottom-up. It supports NumberExpr, UnaryExpr, and BinaryExpr (+, -, *, /)
 * 
 * Error handling includes division-by-zero checks and validation for unsupported operators
 * All arithmetic is performed using integer (int) arithmetic, including integer division
 */
public class Evaluator {
    
    /**
     * Evaluates an expression tree and returns its computed integer result.
     * 
     * This method uses recursive dispatch based on the expression type:
     * - NumberExpr: returns the value directly (base case)
     * - UnaryExpr: recursively evaluates the operand and applies the unary operator
     * - BinaryExpr: recursively evaluates both operands and applies the binary operator
     * 
     * @param expr  The root of the expression tree to evaluate (cannot be null)
     * @return      The computed integer result of the expression
     * @throws      ArithmeticException if division by zero is attempted
     * @throws      RuntimeException if an unsupported operator is encountered or expr type is unknown
     */
    public int evaluate(Expr expr){
        if (expr instanceof NumberExpr) {
            // Base case: Leaf node containing a numeric value
            // Return the stored integer directly
            return ((NumberExpr) expr).getValue();  
        }

        if (expr instanceof UnaryExpr) {
            // Unary expression: operator applied to a single operand
            // Recursively evaluate the child operand, then apply the operator
            UnaryExpr u = (UnaryExpr) expr;
            int right = evaluate(u.getRight());

            switch (u.getOperator().getType()) {
                case MINUS:
                    // Negation operator: return the negative of the operand
                    return -right;
                default:
                    throw new RuntimeException("Unsupported unary operator: " + u.getOperator().getLexeme());
            }
        }

        if (expr instanceof BinaryExpr) {
            // Binary expression: operator applied to two operands.
            // Recursively evaluate both left and right subtrees (post-order traversal),
            // then apply the operator to combine the results
            BinaryExpr b = (BinaryExpr) expr;
            int left = evaluate(b.getLeft());
            int right = evaluate(b.getRight());

            switch (b.getOperator().getType()) {
                case PLUS:
                    // Addition operator: return sum of left and right operands
                    return left + right;
                case MINUS:
                    // Subtraction operator: return left operand minus right operand
                    return left - right;
                case STAR:
                    // Multiplication operator: return product of left and right operands
                    return left * right;
                case SLASH:
                    // Division operator: return integer division of left by right
                    // Throws ArithmeticException if right operand is zero
                    if (right == 0) {
                        throw new ArithmeticException("Cannot divide by 0");
                    }
                    return left / right;
                default:
                    throw new RuntimeException("Unsupported binary operator: " + b.getOperator().getLexeme());
            }
        }
        
        // Error case: expression type is not recognized
        // This should not occur if the parser correctly builds the AST
        throw new RuntimeException("Unknown expression: " + expr.getClass().getSimpleName());
        
    }
}
