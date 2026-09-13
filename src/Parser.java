import java.util.ArrayList;
import java.util.List;

// O Parser realiza a análise sintática.
// Ele recebe os tokens produzidos pelo Scanner e verifica
// se eles seguem a gramática da linguagem.
//
// Além disso, gera comandos em notação pós-fixa,
// que posteriormente serão executados pelo Interpreter.
public class Parser {
    private Scanner scan;
    
    private Token currentToken;// Token que está sendo analisado no momento.    
    private List<String> commands;// Lista que armazena os comandos gerados pelo Parser.
    public Parser(byte[] input) {
        scan = new Scanner(input);
        // Obtém o primeiro token da entrada.
        currentToken = scan.nextToken();
        commands = new ArrayList<>();
    }

    // Avança para o próximo token.
    private void nextToken() {
        currentToken = scan.nextToken();
    }

    // Verifica se o token atual é do tipo esperado.
    // Se estiver correto, avança para o próximo token.
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

    // Inicia a análise do programa.
    public void parse() {
        statements();
        // Ao terminar, esperamos encontrar o fim da entrada.
        if (currentToken.type != TokenType.EOF) {
            throw new Error("syntax error");
        }
    }

    // Um programa pode possuir várias instruções.
    //
    // statements -> statement*
    //
    // O símbolo * significa zero ou mais instruções.
    private void statements() {
        while (currentToken.type != TokenType.EOF) {
            statement();
        }
    }

    // Define qual tipo de instrução será analisada.
    //
    // statement -> letStatement | printStatement
    private void statement() {
        if (currentToken.type == TokenType.LET) {
            letStatement();

        } else if (currentToken.type == TokenType.PRINT) {
            printStatement();

        } else {
            throw new Error("syntax error");
        }
    }

    // Analisa uma declaração de variável.
    //
    // letStatement ->
    //     LET IDENT EQ expression SEMICOLON
    //
    // Exemplo:
    // let a = 42 + 5;
    private void letStatement() {
        match(TokenType.LET);
        // Guarda o nome da variável.
        String id = currentToken.lexeme;
        match(TokenType.IDENT);
        match(TokenType.EQ);
        // Analisa a expressão matemática.
        expr();
        // Depois de calcular a expressão,
        // o resultado será retirado da pilha e armazenado na variável.
        commands.add("pop " + id);
        match(TokenType.SEMICOLON);
    }

    // Analisa uma instrução de impressão.
    //
    // printStatement ->
    //     PRINT expression SEMICOLON
    //
    // Exemplo:
    // print a + 6;
    private void printStatement() {
        match(TokenType.PRINT);
        // Analisa a expressão que será impressa.
        expr();
        // O resultado da expressão está no topo da pilha.
        commands.add("print");
        match(TokenType.SEMICOLON);
    }

    /*
     * Expressão:
     *
     * expression -> term expressionTail
     *
     * A expressão é dividida em termos para garantir
     * a precedência dos operadores.
     */
    private void expr() {
        term();
        expressionTail();
    }

    /*
     * Continuação da expressão:
     *
     * expressionTail ->
     *       + term expressionTail
     *     | - term expressionTail
     *     | vazio
     *
     * Soma e subtração possuem menor precedência
     * que multiplicação e divisão.
     */
    private void expressionTail() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            term();
            // Gera o comando de soma em pós-fixa.
            commands.add("add");
            expressionTail();

        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            term();
            // Gera o comando de subtração em pós-fixa.
            commands.add("sub");
            expressionTail();
        }
    }

    /*
     * Termo:
     *
     * term -> factor termTail
     *
     * É responsável por tratar multiplicação e divisão.
     */
    private void term() {
        factor();
        termTail();
    }

    /*
     * Continuação do termo:
     *
     * termTail ->
     *       * factor termTail
     *     | / factor termTail
     *     | vazio
     *
     * Multiplicação e divisão possuem maior precedência
     * que soma e subtração.
     */
    private void termTail() {
        if (currentToken.type == TokenType.STAR) {
            match(TokenType.STAR);
            factor();
            // Comando de multiplicação.
            commands.add("mul");
            termTail();

        } else if (currentToken.type == TokenType.SLASH) {
            match(TokenType.SLASH);
            factor();
            // Comando de divisão.
            commands.add("div");
            termTail();
        }
    }

    /*
     * Fator:
     *
     * factor -> NUMBER | IDENT
     *
     * Um fator pode ser um número ou uma variável.
     */
    private void factor() {
        if (currentToken.type == TokenType.NUMBER) {
            // Coloca o número na pilha.
            commands.add("push " + currentToken.lexeme);
            match(TokenType.NUMBER);
        } else if (currentToken.type == TokenType.IDENT) {
            // Coloca o nome da variável na pilha.
            commands.add("push " + currentToken.lexeme);
            match(TokenType.IDENT);
        } else {
            throw new Error("syntax error");
        }
    }

    // Retorna os comandos gerados pelo Parser.
    public List<String> getCommands() {
        return commands;
    }
}