package mini_expr_compiler.ast;

/**
 * Responsible for turning an Expr tree into a readable AST tree
 * Does not modify the Expr tree, it only traverses the tree and formats it
 * 
 * Class recursively "builds" each subtree into an array of strings
 * For binary nodes, horizontally merge the left and right subtrees
 * Places the operator and branch connectors ("/" "\\") above them working upward. 
 */
public class AstPrinter {
    
    /**
     * public print method, called by Main
     * @param expr
     * @return
     */
    public String print(Expr expr) {
        if (expr == null) {
            return ""; // empty expressions print as empty rather than throwing a NullPointerException
        }
        StringBuilder sb = new StringBuilder();
        String[] lines = build(expr);   //Build the tree layout as an array of lines

        // Join the individual lines into one string using newline seperation
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * Recursively builds a AST representation of the input expression 
     * @param expr  Expr subtree is turned into an array of strings
     * 
     * @return
     */
    private String[] build(Expr expr) {
        
        // Handle leaf nodes (numbers)
        // Base case: a number has no children, so print normal value
        if (expr instanceof NumberExpr) {
            String s = String.valueOf(((NumberExpr) expr).getValue());
            return new String[]{ s };
        }

        // Unary nodes
        // If it's a unary minus applied to a number, just display it as the negative number itself
        if (expr instanceof UnaryExpr) {
            UnaryExpr u = (UnaryExpr) expr;
            
            // Special case: unary minus on a number - display as negative number
            if (u.getRight() instanceof NumberExpr && u.getOperator().getType().toString().equals("MINUS")) {
                double value = ((NumberExpr) u.getRight()).getValue();
                String negativeNum = value == (int)value ? "-" + (int)value : "-" + value;
                return new String[] { negativeNum };
            }
            
            // Special case: unary minus on a binary expression - distribute the minus
            if (u.getRight() instanceof BinaryExpr && u.getOperator().getType().toString().equals("MINUS")) {
                BinaryExpr binExpr = (BinaryExpr) u.getRight();
                // Create new unary expressions for left and right with minus operator
                UnaryExpr negLeft = new UnaryExpr(u.getOperator(), binExpr.getLeft());
                UnaryExpr negRight = new UnaryExpr(u.getOperator(), binExpr.getRight());
                // Create a new binary expression with the negated operands
                BinaryExpr negatedBinExpr = new BinaryExpr(negLeft, binExpr.getOperator(), negRight);
                // Build the negated expression
                return build(negatedBinExpr);
            }
            
            String op = u.getOperator().getLexeme();
            String opDisplay = "/".equals(op) ? "÷" : op;

            // Recursively build the right subtree
            String[] child = build(u.getRight());

            // Result has one extra line: operator on top, then the child subtree
            String[] result = new String[child.length + 1];
            result[0] = opDisplay; // operator at the root line

            // Indent every line of the child by one space so its right under the operator
            for (int i = 0; i < child.length; i++) {
                result[i + 1] = " " + child[i];
            }
            return result;
        }

        // Binary nodes
        if (expr instanceof BinaryExpr) {
            BinaryExpr b = (BinaryExpr) expr;
            String op = b.getOperator().getLexeme();
            String opDisplay = "/".equals(op) ? "÷" : op;

            // Build the AST blocks for left / right subtrees
            String[] left = build(b.getLeft());
            String[] right = build(b.getRight());

            // width(left) gives us how many characters the left subtree occupies on its widest line.
            // We can use this to decide where we place the root operator and the branch connectors. 
            int leftWidth = width(left);

            // Root line: pad with spaces so the operator sits roughly above the gap between L and R. "+2" is a small tuning offset
            String root = " ".repeat(leftWidth + 1) + opDisplay;

            // Connector line: spaces up to the left subtree width for proper spacing
            String connector = " ".repeat(leftWidth) + "/ \\";   // / and \ separated by ONE space

            // Merge the left and right subtrees together
            String[] merged = merge(left, right);

            // Final Block:
            String[] lines = new String[2 + merged.length];
            lines[0] = root;        // operator
            lines[1] = connector;   // branch connectors

            for (int i = 0; i < merged.length; i++) {
                lines[i + 2] = merged[i];
            }

            return lines;
        }

        return new String[] {"(unknown)"};
    }

    /**
     * Computes the max line width in a block of text. Used to understand how "wide" the given subtree is, and used to position operators and connectors.
     */
    private int width(String[] lines) {
        int w = 0;
        for (String s : lines) {
            if (s.length() > w) {
                 w = s.length();
            }
        }
        return w;
    }

    /**
     * Merges the left and right subtree blocks into a single block
     * how it works:
     *      - iterate up to the tallest height of the two trees
     *      - pad missing lines on the short side with spaces
     *      - insert 3 spaces between left and right to align with / \ connectors
     */
    private String[] merge(String[] left, String[] right) {
        int h = Math.max(left.length, right.length);
        String[] result = new String[h];

        int leftWidth = width(left);
        int rightWidth = width(right);

        for (int i = 0; i < h; i++) {
            // If the left subtree has a line here use it, otherwise pad with spaces to leftWidth
            String L = i < left.length ? padRight(left[i], leftWidth) : " ".repeat(leftWidth);

            // If the right subtree has a line here use it, otherwise pad with spaces to rightWidth
            String R = i < right.length ? padRight(right[i], rightWidth) : " ".repeat(rightWidth);
            
            result[i] = L + "   " + R;  // 3 spaces to match connector spacing
        }
        return result;
    }

    /**
     * Pad the given string on the right with spaces up to the given width.
     */
    private String padRight(String s, int width) {
        if (s.length() >= width) return s;
        return s + " ".repeat(width - s.length());
    }

}
