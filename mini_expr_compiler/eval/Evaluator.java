package mini_expr_compiler.eval;

import mini_expr_compiler.ast.*;

public class Evaluator {
    
    public int evaluate(Expr expr){
        if (expr instanceof NumberExpr) {
            // Leaf node: just return the stored integer value
            return ((NumberExpr) expr).getValue();  
        }

        if (expr instanceof UnaryExpr) {
            UnaryExpr u = (UnaryExpr) expr;
            int right = evaluate(u.getRight());

            switch (u.getOperator().getType()) {
                case MINUS:
                    return -right;
                default:
                    throw new RuntimeException("Unsupported unary operator: " + u.getOperator().getLexeme());
            }
        }

        if (expr instanceof BinaryExpr) {
            BinaryExpr b = (BinaryExpr) expr;
            int left = evaluate(b.getLeft());
            int right = evaluate(b.getRight());

            switch (b.getOperator().getType()) {
                case PLUS:
                    return left + right;
                case MINUS:
                    return left - right;
                case STAR:
                    return left * right;
                case SLASH:
                    if (right == 0) {
                        throw new ArithmeticException("Cannot divide by 0");
                    }
                    return left / right;
                default:
                    throw new RuntimeException("Unsupported binary operator: " + b.getOperator().getLexeme());
            }
        }
        throw new RuntimeException("Unknown expression: " + expr.getClass().getSimpleName());
        
    }
}
