import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String source = "let a = 42 + 5;";
        byte[] input =
            source.getBytes(StandardCharsets.UTF_8);

        Parser parser = new Parser(input);
        parser.parse();
    }
}