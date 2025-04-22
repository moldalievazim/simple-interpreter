package src.compiler;

public class Main {
    public static void main(String[] args) {
        String input = """
                a + b
                """;

        Lexer lexer = new Lexer(input);
        Lexer.Token token;
        do {
            token = lexer.nextToken();
            System.out.println(token);
        } while (token.tokenType != Lexer.TokenType.EOF);

        Parser parser = new Parser(new Lexer(input));
        Parser.ASTNode tree = parser.parse();
        parser.printAST(tree, 0);
    }
}
