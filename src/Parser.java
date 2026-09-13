import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Scanner scan;
    private Token currentToken;
    private List<String> commands;

    public Parser(byte[] input) {

        scan = new Scanner(input);
        currentToken = scan.nextToken();

        commands = new ArrayList<>();
    }

    private void nextToken() {
        currentToken = scan.nextToken();
    }

    private void match(TokenType type) {

        if (currentToken.type == type) {
            nextToken();
        } else {
            throw new Error(
                "syntax error: esperado "
                + type
                + ", encontrado "
                + currentToken.type
            );
        }
    }

    public void parse() {

        statements();

        if (currentToken.type != TokenType.EOF) {
            throw new Error("syntax error");
        }
    }

    private void statements() {

        while (currentToken.type != TokenType.EOF) {
            statement();
        }
    }

    private void statement() {

        if (currentToken.type == TokenType.LET) {

            letStatement();

        } else if (currentToken.type == TokenType.PRINT) {

            printStatement();

        } else {

            throw new Error("syntax error");
        }
    }

    private void letStatement() {

        match(TokenType.LET);

        String id = currentToken.lexeme;

        match(TokenType.IDENT);

        match(TokenType.EQ);

        expr();

        commands.add("pop " + id);

        match(TokenType.SEMICOLON);
    }

    private void printStatement() {

        match(TokenType.PRINT);

        expr();

        commands.add("print");

        match(TokenType.SEMICOLON);
    }

    /*
     * expression
     *     -> term expressionTail
     */
    private void expr() {

        term();

        expressionTail();
    }

    /*
     * expressionTail
     *     -> + term expressionTail
     *     -> - term expressionTail
     *     -> vazio
     */
    private void expressionTail() {

        if (currentToken.type == TokenType.PLUS) {

            match(TokenType.PLUS);

            term();

            commands.add("add");

            expressionTail();

        } else if (currentToken.type == TokenType.MINUS) {

            match(TokenType.MINUS);

            term();

            commands.add("sub");

            expressionTail();
        }
    }

    /*
     * term
     *     -> factor termTail
     */
    private void term() {

        factor();

        termTail();
    }

    /*
     * termTail
     *     -> * factor termTail
     *     -> / factor termTail
     *     -> vazio
     */
    private void termTail() {

        if (currentToken.type == TokenType.STAR) {

            match(TokenType.STAR);

            factor();

            commands.add("mul");

            termTail();

        } else if (currentToken.type == TokenType.SLASH) {

            match(TokenType.SLASH);

            factor();

            commands.add("div");

            termTail();
        }
    }

    /*
     * factor
     *     -> NUMBER
     *     -> IDENT
     */
    private void factor() {

        if (currentToken.type == TokenType.NUMBER) {

            commands.add("push " + currentToken.lexeme);

            match(TokenType.NUMBER);

        } else if (currentToken.type == TokenType.IDENT) {

            commands.add("push " + currentToken.lexeme);

            match(TokenType.IDENT);

        } else {

            throw new Error("syntax error");
        }
    }

    public List<String> getCommands() {
        return commands;
    }
}