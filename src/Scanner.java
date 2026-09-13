import java.util.HashMap;
import java.util.Map;

// O Scanner é responsável pela análise léxica.
// Ele lê a entrada caractere por caractere e agrupa os caracteres
// em tokens que serão utilizados pelo Parser.

public class Scanner {    
    private byte[] input;// Entrada do programa transformada em bytes.    
    private int current;// Posição atual que está sendo analisada.    
    private static final Map<String, TokenType> keywords = new HashMap<>();// Tabela que associa palavras reservadas aos seus respectivos tokens.
    // Cadastro das palavras reservadas da linguagem.
    static {
        keywords.put("let", TokenType.LET);
        keywords.put("print", TokenType.PRINT);
    }

    public Scanner(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    // Retorna o caractere atual sem avançar a posição.
    private char peek() {
        if (current < input.length) {
            return (char) input[current];
        }
        return '\0';
    }

    // Avança para o próximo caractere da entrada.
    private void advance() {
        if (peek() != '\0') {
            current++;
        }
    }

    // Ignora espaços, tabulações e quebras de linha.
    // Dessa forma, esses caracteres não interferem na análise.
    private void skipWhitespace() {
        char ch = peek();
        while (ch == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
            advance();
            ch = peek();
        }
    }

    // Verifica se o caractere pode fazer parte de um identificador.
    // Além de letras e números, o caractere '_' também é permitido.
    private boolean isAlphaNumeric(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    // Lê um número inteiro completo.
    // Exemplo: os caracteres "123" formam um único token NUMBER.
    private Token number() {
        int start = current;
        while (Character.isDigit(peek())) {
            advance();
        }
        String n = new String(
            input,
            start,
            current - start
        );
        return new Token(TokenType.NUMBER, n);
    }

    // Lê um identificador ou uma palavra reservada.
    // Exemplo: "idade" é IDENT e "let" é LET.
    private Token identifier() {
        int start = current;
        while (isAlphaNumeric(peek())) {
            advance();
        }

        String id = new String(
            input,
            start,
            current - start
        );

        // Se a palavra estiver na tabela de palavras reservadas,
        // recebe o tipo correspondente. Caso contrário, é IDENT.
        TokenType type = keywords.getOrDefault(
            id,
            TokenType.IDENT
        );
        return new Token(type, id);
    }

    // Retorna o próximo token da entrada.
    public Token nextToken() {        
        skipWhitespace();// Primeiro ignoramos os espaços em branco.
        char ch = peek();

        // Se começar com número, analisamos como NUMBER.
        if (Character.isDigit(ch)) {
            return number();
        }

        // Se começar com letra ou '_', analisamos como identificador.
        if (Character.isLetter(ch) || ch == '_') {
            return identifier();
        }

        // Análise dos símbolos individuais.
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

            // Quando não existem mais caracteres,
            // informamos que chegamos ao final da entrada.
            case '\0':
                return new Token(TokenType.EOF, "EOF");

            // Qualquer outro caractere não pertence à linguagem.
            default:
                throw new Error("lexical error at " + ch);
        }
    }
}