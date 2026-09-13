
public class Token {
    // Representa um token encontrado pelo Scanner.
    // Cada token possui um tipo e o texto que foi encontrado.

    final TokenType type;
    final String lexeme;

    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    // Facilita a visualização dos tokens durante testes.
    @Override
    public String toString() {
        return "<" + type + ">" + lexeme + "</" + type + ">";
    }
}