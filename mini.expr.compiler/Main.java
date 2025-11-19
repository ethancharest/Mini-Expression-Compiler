import java.util.Scanner;
import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Scanner scanby = new Scanner(System.in);
        System.out.println("Enter an expression: ");
        String input = scanby.nextLine();

        System.out.println("===============================================");
        System.out.println("Input: " + input);
        System.out.println("===============================================");

        // 1. Tokenization

        Lexer lexer = new Lexer(input);
        List<Token> tokens = null;
        // List<Token> tokens = lexer.tokenize();

        try {
            tokens = lexer.tokenize();
            System.out.println("Tokens: " + Lexer.formatTokenList(tokens));
            System.out.println("List of tokens:");
            for (Token t : tokens) {
                System.out.println("" + t);
            }
        } catch (Exception e) {
            System.out.println("Lexical Error: " + e.getMessage());
            scanby.close();
            return;
        }

        // // 2. Parsing

        // Parser parser = new Parser(tokens);
        // Expr ast = null;

        // try {
        //     ast = parser.parse();
        //     System.out.println("\nParser: success");
        // } catch (ParseException e) {
        //     System.out.println("\nParser: failure");
        //     System.out.println("Reason: " + e.getMessage());
        //     return;
        // }

        // // 3. Print Parse Tree
        // System.out.println("\nParse Tree:");
        // AstPrinter printer = new AstPrinter();
        // System.out.println(printer.print(ast));

        // // 4. Evaluate

        // Evaluator eval = new Evaluator();
        // try {
        //     double result = eval.evaluate(ast);
        //     System.out.println("\nEvaluation Result: " + result);
        // } catch (Exception e) {
        //     System.out.println("\nEvaluation Error: " + e.getMessage());
        // }

        System.out.println("====================================================");
        scanby.close();
    }
}