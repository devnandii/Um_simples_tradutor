import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

// O Interpreter executa os comandos gerados pelo Parser.
//
// Ele utiliza:
// - uma pilha para realizar as operações;
// - um mapa para armazenar as variáveis.
public class Interpreter {
    // Pilha utilizada para armazenar os valores das expressões.
    private Stack<Integer> stack;
    // Armazena as variáveis e seus respectivos valores.
    private Map<String, Integer> variables;
    public Interpreter() {
        stack = new Stack<>();
        variables = new HashMap<>();
    }

    // Executa todos os comandos gerados pelo Parser.
    public void execute(String[] commands) {
        for (String command : commands) {

            // Divide o comando em partes.
            // Exemplo:
            // "push 42" -> ["push", "42"]
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

    // Coloca um valor na pilha.
    //
    // Se for uma variável, recuperamos o valor dela.
    // Caso contrário, tratamos o valor como um número.
    private void push(String value) {
        if (variables.containsKey(value)) {
            // Recupera o valor armazenado na variável.
            stack.push(variables.get(value));

        } else {
            // Converte o texto para um número inteiro.
            stack.push(Integer.parseInt(value));
        }
    }

    // Retira um valor da pilha e armazena em uma variável.
    //
    // Exemplo:
    // pop a
    //
    // O valor que está no topo da pilha será armazenado em "a".
    private void pop(String variable) {
        int value = stack.pop();
        variables.put(variable, value);
    }

    // Realiza uma soma.
    private void add() {
        // O segundo valor é retirado primeiro.
        int b = stack.pop();
        // Depois retiramos o primeiro valor.
        int a = stack.pop();
        // Coloca o resultado novamente na pilha.
        stack.push(a + b);
    }

    // Realiza uma subtração.
    private void sub() {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a - b);
    }

    // Realiza uma multiplicação.
    private void mul() {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a * b);
    }

    // Realiza uma divisão.
    private void div() {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a / b);
    }

    // Retira o resultado da pilha e mostra na tela.
    private void print() {
        int value = stack.pop();
        System.out.println(value);
    }
}