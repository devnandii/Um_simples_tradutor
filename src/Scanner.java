import java.util.HashMap;
import java.util.Map;

public class Scanner {

    private byte[] input;
    private int current;

    private static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("let", TokenType.LET);
        keywords.put("print", TokenType.PRINT);
    }

    public Scanner(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    private char peek() {
        if (current < input.length) {
            return (char) input[current];
        }

        return '\0';
    }

    private void advance() {
        if (peek() != '\0') {
            current++;
        }
    }

    private void skipWhitespace() {

        char ch = peek();

        while (ch == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
            advance();
            ch = peek();
        }
    }

    private boolean isAlphaNumeric(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    private Token number() {

        int start = current;

        while (Character.isDigit(peek())) {
            advance();
        }

        String n = new String(input, start, current - start);

        return new Token(TokenType.NUMBER, n);
    }

    private Token identifier() {

        int start = current;

        while (isAlphaNumeric(peek())) {
            advance();
        }

        String id = new String(input, start, current - start);

        TokenType type = keywords.getOrDefault(id, TokenType.IDENT);

        return new Token(type, id);
    }

    public Token nextToken() {

        skipWhitespace();

        char ch = peek();

        if (Character.isDigit(ch)) {
            return number();
        }

        if (Character.isLetter(ch) || ch == '_') {
            return identifier();
        }

        switch (ch) {

            case '+':
                advance();
                return new Token(TokenType.PLUS, "+");

            case '-':
                advance();
                return new Token(TokenType.MINUS, "-");

            case '*':
                advance();
                return new Token(TokenType.STAR, "*");

            case '/':
                advance();
                return new Token(TokenType.SLASH, "/");

            case '=':
                advance();
                return new Token(TokenType.EQ, "=");

            case ';':
                advance();
                return new Token(TokenType.SEMICOLON, ";");

            case '\0':
                return new Token(TokenType.EOF, "EOF");

            default:
                throw new Error("lexical error at " + ch);
        }
    }
}