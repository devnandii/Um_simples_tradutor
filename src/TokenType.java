// Define os tipos de tokens que podem ser encontrados na linguagem.
public enum TokenType {

    // Operadores aritméticos
    PLUS,       // +
    MINUS,      // -
    STAR,       // *
    SLASH,      // /

    // Valores e identificadores
    NUMBER,     // Números inteiros
    IDENT,      // Nome de uma variável

    // Palavras reservadas
    LET,        // let
    PRINT,      // print

    // Símbolos da linguagem
    EQ,         // =
    SEMICOLON,  // ;

    // Indica o fim da entrada
    EOF
}