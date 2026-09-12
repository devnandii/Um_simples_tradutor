public class Parser {

    private Scanner scan;
    private Token currentToken;

    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
    }

    private void nextToken() {
        currentToken = scan.nextToken();
    }

    private void match(TokenType type) {

        if (currentToken.type == type) {
            nextToken();
        } else {
            throw new Error("syntax error");
        }
    }

    public void parse() {

        expr();

        if (currentToken.type != TokenType.EOF) {
            throw new Error("syntax error");
        }
    }

    private void expr() {
        digit();
        oper();
    }

    private void digit() {

        if (currentToken.type == TokenType.NUMBER) {

            System.out.println("push " + currentToken.lexeme);

            match(TokenType.NUMBER);

        } else {
            throw new Error("syntax error");
        }
    }

    private void oper() {

        if (currentToken.type == TokenType.PLUS) {

            match(TokenType.PLUS);

            digit();

            System.out.println("add");

            oper();

        } else if (currentToken.type == TokenType.MINUS) {

            match(TokenType.MINUS);

            digit();

            System.out.println("sub");

            oper();
        }

        // ε
    }
}
