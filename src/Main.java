import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String source = "289-85+69";
        byte[] input =
            source.getBytes(StandardCharsets.UTF_8);

        Parser parser = new Parser(input);
        parser.parse();
    }
}