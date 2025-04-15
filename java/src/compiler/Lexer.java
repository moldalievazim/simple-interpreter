package src.compiler;

import java.util.*;

public class Lexer {
    private static final char EOF_CHAR = '\0';
    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList("while", "if", "else", "print", "for", "do", "switch", "case"));
    private String input;
    private int position;
    private char currChar;

    public Lexer(String input) {
        this.input = input;
        this.position = 0;
        this.currChar = input == null || input.isEmpty() ? EOF_CHAR : input.charAt(position);
    }

    public Token nextToken() {
        while (currChar != EOF_CHAR) {
            if (Character.isWhitespace(currChar)) {
                skipWhitespace();
                continue;
            }
            if (Character.isDigit(currChar)) {
                return readNumber();
            }
            if (Character.isLetter(currChar)) {
                return readIdentifier();
            }
            return switch (currChar) {
                case '=' -> {
                    advance();
                    yield new Token(TokenType.ASSIGNMENT, "=");
                }
                case '+' -> {
                    advance();
                    yield new Token(TokenType.PLUS, "+");
                }
                case '-' -> {
                    advance();
                    yield new Token(TokenType.MINUS, "-");
                }
                case '/' -> {
                    advance();
                    yield new Token(TokenType.DIV, "/");
                }
                case '*' -> {
                    advance();
                    yield new Token(TokenType.MUL, "*");
                }
                case ';' -> {
                    advance();
                    yield new Token(TokenType.SEPARATOR, ";");
                }
                case '(' -> {
                    advance();
                    yield new Token(TokenType.LPAREN, "(");
                }
                case ')' -> {
                    advance();
                    yield new Token(TokenType.RPAREN, ")");
                }
                case '{' -> {
                    advance();
                    yield new Token(TokenType.LPAREN, "{");
                }
                case '}' -> {
                    advance();
                    yield new Token(TokenType.RPAREN, "}");
                }
                case '>' -> {
                    advance();
                    yield new Token(TokenType.GREATER, ">");
                }
                case '<' -> {
                    advance();
                    yield new Token(TokenType.LESS, "<");
                }
                default -> throw new LexerException("Unexpected character: " + currChar);
            };
        }

        return new Token(TokenType.EOF, "");
    }

    private Token readIdentifier() {
        StringBuilder sb = new StringBuilder();
        while (Character.isLetterOrDigit(currChar)) {
            sb.append(currChar);
            advance();
        }
        String value = sb.toString();

        return KEYWORDS.contains(value) ?
                new Token(TokenType.KEYWORD, value) :
                new Token(TokenType.IDENTIFIER, sb.toString());
    }

    private Token readNumber() {
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(currChar)) {
            sb.append(currChar);
            advance();
        }
        return new Token(TokenType.NUMBER, sb.toString());
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(currChar)){
            advance();
        }
    }

    private void advance() {
        position++;
        currChar = position < input.length() ? input.charAt(position) : EOF_CHAR;
    }

    public static class Token{
        public final TokenType tokenType;
        public final String value;

        Token (TokenType type, String value) {
            this.tokenType = type;
            this.value = value;
        }

        @Override
        public String toString () {
            return "Token{" +
                    "type=" + tokenType +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public enum TokenType {
        ASSIGNMENT,
        COMPUTE,
        CONFIG,
        DIV,
        EOF,
        GREATER,
        IDENTIFIER,
        KEYWORD,
        LESS,
        LPAREN, MINUS, MUL, NUMBER, PLUS, RPAREN, SEPARATOR, UPDATE,
    }
}

