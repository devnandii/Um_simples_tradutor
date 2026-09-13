// Classe principal do programa.
public class Main {

    public static void main(String[] args) {
        // Programa de teste.
        //
        // Primeiro calcula:
        // a = 42 + (5 * 2)
        //
        // Depois:
        // b = a / 2
        //
        // Por fim:
        // imprime b + 6
        String source =
            "let a = 42 + 5 * 2; " +
            "let b = a / 2; " +
            "print b + 6;";
        // Cria o Parser e envia o código-fonte para análise.
        Parser parser = new Parser(source.getBytes());

        // Realiza a análise sintática e gera os comandos
        // em notação pós-fixa.
        parser.parse();

        // Cria o interpretador.
        Interpreter interpreter = new Interpreter();

        // Executa os comandos produzidos pelo Parser.
        interpreter.execute(
            parser.getCommands().toArray(new String[0])
        );
    }
}