import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Interpreter {

    private Stack<Integer> stack;
    private Map<String, Integer> variables;

    public Interpreter() {

        stack = new Stack<>();
        variables = new HashMap<>();
    }

    public void execute(String[] commands) {

        for (String command : commands) {

            String[] parts = command.split(" ");

            switch (parts[0]) {

                case "push":
                    push(parts[1]);
                    break;

                case "pop":
                    pop(parts[1]);
                    break;

                case "add":
                    add();
                    break;

                case "sub":
                    sub();
                    break;

                case "mul":
                    mul();
                    break;

                case "div":
                    div();
                    break;

                case "print":
                    print();
                    break;

                default:
                    throw new Error(
                        "comando desconhecido: " + parts[0]
                    );
            }
        }
    }

    private void push(String value) {

        if (variables.containsKey(value)) {

            stack.push(variables.get(value));

        } else {

            stack.push(Integer.parseInt(value));
        }
    }

    private void pop(String variable) {

        int value = stack.pop();

        variables.put(variable, value);
    }

    private void add() {

        int b = stack.pop();
        int a = stack.pop();

        stack.push(a + b);
    }

    private void sub() {

        int b = stack.pop();
        int a = stack.pop();

        stack.push(a - b);
    }

    private void mul() {

        int b = stack.pop();
        int a = stack.pop();

        stack.push(a * b);
    }

    private void div() {

        int b = stack.pop();
        int a = stack.pop();

        stack.push(a / b);
    }

    private void print() {

        int value = stack.pop();

        System.out.println(value);
    }
}