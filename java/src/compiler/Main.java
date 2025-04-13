package compiler;

public class Main {
    public static void main(String[] args) {
        String input = """
                x = 5;
                if (x > 3) {
                    y = x + 2;
                } else {
                    y = x * (2 + 3);
                }
                print y;
                """;

        Lexer lexer = new Lexer(input);
        for (Lexer.Token token: lexer.tokenize()) {
            System.out.println(token);
        }

    }
}
