package compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private String input;
    private int currentPosition;

    public Lexer(String input) {
        this.input = input;
        this.currentPosition = 0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (currentPosition < input.length()) {
            char currentChar = input.charAt(currentPosition);

            if (Character.isWhitespace(currentChar)) {
                currentPosition++;
                continue;
            }

            Token token = nextToken();
            if (token != null) {
                tokens.add(token);
            } else {
                throw new RuntimeException("Unknown character: " + currentChar);
            }
        }

        return tokens;
    }

    private Token nextToken() {
        if (currentPosition >= input.length()) return null;

        String[] tokenPatterns = {
                "config|compute|update|show",
                "[a-zA-Z_][a-zA-Z0-9_]*",
                "\\d+",
                "[+-/*=<>!]",
                "[.,;(){}]",
        };

        TokenType[] tokenTypes = {
                TokenType.KEYWORD,
                TokenType.IDENTIFIER,
                TokenType.ASSIGNMENT,
                TokenType.CONFIG,
                TokenType.COMPUTE,
                TokenType.UPDATE,
                TokenType.STRING,
                TokenType.NUMBER,
                TokenType.OPERATOR,
        };

        for (int i = 0; i < tokenPatterns.length; i++) {
            Pattern pattern = Pattern.compile("^" + tokenPatterns[i]);
            Matcher matcher = pattern.matcher(input.substring(currentPosition));

            if (matcher.find()) {
                String value = matcher.group();
                currentPosition += value.length();
                return new Token(tokenTypes[i], value);
            }
        }

        return null;
    };

    public class Token{
        final TokenType type;
        final String value;

        Token (TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString () {
            return "Token{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public enum TokenType {
        KEYWORD,
        IDENTIFIER,
        OPERATOR,
        CONFIG,
        UPDATE,
        COMPUTE,
        ASSIGNMENT,
        STRING,
        NUMBER,
    }
}
