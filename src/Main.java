public class Main {

    public static void main(String[] args) {

        String source =
            "let a = 42 + 5 * 2; " +
            "let b = a / 2; " +
            "print b + 6;";

        Parser parser = new Parser(source.getBytes());

        parser.parse();

        Interpreter interpreter = new Interpreter();

        interpreter.execute(
            parser.getCommands().toArray(new String[0])
        );
    }
}