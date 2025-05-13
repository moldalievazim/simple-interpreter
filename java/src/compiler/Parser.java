package src.compiler;

public class Parser {
    private final Lexer lexer;
    private Lexer.Token currToken;

    public Parser (Lexer lexer) {
        this.lexer = lexer;
        this.currToken = lexer.nextToken();
    }

    public static void printAST(ASTNode node, int indent) {
        String indentStr = "  ".repeat(indent);

        if (node instanceof NumberNode numberNode) {
            System.out.println(indentStr + "Number: " + numberNode.value);
        } else if (node instanceof IdentifierNode identifierNode) {
            System.out.println(indentStr + "Identifier: " + identifierNode.value);
        } else if (node instanceof BinaryOpNode binaryOpNode) {
            System.out.println(indentStr + "BinaryOp: " + binaryOpNode.op);
            printAST(binaryOpNode.left, indent + 1);
            printAST(binaryOpNode.right, indent + 1);
        } else {
            System.out.println(indentStr + "Unknown node");
        }
    }

    public ASTNode parse() {
        return parseExpression();
    }

    private ASTNode parseExpression() {
        ASTNode leftTerm = parseTerm();

        if (currToken.tokenType == Lexer.TokenType.PLUS || currToken.tokenType == Lexer.TokenType.MINUS) {
            Lexer.TokenType op = currToken.tokenType;
            consume(currToken.tokenType);

            return new BinaryOpNode(op, leftTerm, parseExpression());
        }

        return leftTerm;
    }

    private ASTNode parseTerm() {
        ASTNode leftNode = parseFactor();

        if (currToken.tokenType == Lexer.TokenType.MUL || currToken.tokenType == Lexer.TokenType.DIV) {
            Lexer.TokenType op = currToken.tokenType;
            consume(currToken.tokenType);

            return new BinaryOpNode(op, leftNode, parseTerm());
        }

        return null;
    }

    private ASTNode parseFactor() {
        if (currToken.tokenType == Lexer.TokenType.NUMBER) {
            consume(Lexer.TokenType.NUMBER);
            return new NumberNode(currToken.value);
        }
        if (currToken.tokenType == Lexer.TokenType.IDENTIFIER) {
            consume(Lexer.TokenType.IDENTIFIER);
            return new IdentifierNode(currToken.value);
        }
        throw new ParserException("Unexpected token type received: " + currToken.tokenType.name());
    }

    private void consume(Lexer.TokenType tokenType) {
        if (currToken.tokenType == tokenType) {
            currToken = lexer.nextToken();
        } else {
            throw new ParserException("Unexpected token type received: " + currToken.tokenType.name());
        }
    }

    static class ASTNode {}

    static class NumberNode extends ASTNode {
        final String value;

        NumberNode(String value) {
            this.value = value;
        }
    }

    static class BinaryOpNode extends ASTNode {
        final Lexer.TokenType op;
        final ASTNode left;
        final ASTNode right;

        BinaryOpNode(Lexer.TokenType op, ASTNode left, ASTNode right) {
            this.op = op;
            this.left = left;
            this.right = right;
        }
    }

    static class IdentifierNode extends ASTNode {
        final String value;

        IdentifierNode(String value) {
            this.value = value;
        }
    }
}
