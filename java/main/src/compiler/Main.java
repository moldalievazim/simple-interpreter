package compiler;

public class Main {
    public static void main(String[] args) {
        String input = """
                config "num_users" = 100
                config "request_rate" = 5.5
                config "timeout" = 30
                update "num_users" = 200
                update "request_rate" = 7.5
                compute "total_requests" = %num_users * %request_rate
                compute "total_timeout" = %num_users * %timeout
                show configs
                """;

        Lexer lexer = new Lexer(input);
        for (Lexer.Token token: lexer.tokenize()) {
            System.out.println(token);
        }

    }
}
