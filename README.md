# Tradutor Aritmético

Projeto desenvolvido para a disciplina de **Compiladores**. O objetivo é implementar um tradutor simples de expressões aritméticas, passando pelas etapas de **análise léxica, análise sintática, geração de comandos pós-fixos e interpretação**.

---

## 1. Objetivo

O programa recebe um código-fonte simples contendo declarações de variáveis e expressões aritméticas, como:

```text
let a = 42 + 5 * 2;
let b = a / 2;
print b + 6;
```

O código é analisado e transformado em uma sequência de comandos em **notação pós-fixa**, que posteriormente são executados pelo interpretador.

---

## 2. Funcionamento

O funcionamento do tradutor é dividido em três etapas principais:

```text
Código-fonte
     ↓
  Scanner
     ↓
   Tokens
     ↓
   Parser
     ↓
Comandos pós-fixos
     ↓
 Interpreter
     ↓
  Resultado
```

### Scanner

Realiza a **análise léxica**, lendo o código caractere por caractere e identificando os tokens da linguagem.

Reconhece:

* Números inteiros
* Identificadores
* `let`
* `print`
* `+`
* `-`
* `*`
* `/`
* `=`
* `;`

Espaços, tabulações e quebras de linha são ignorados.

### Parser

Realiza a **análise sintática** utilizando um parser descendente recursivo.

O Parser verifica se os tokens seguem a gramática definida e gera os comandos em notação pós-fixa.

### Interpreter

Executa os comandos gerados pelo Parser utilizando:

* Uma pilha para armazenar os valores das expressões;
* Um mapa para armazenar as variáveis.

---

## 3. Gramática

A gramática utilizada pelo Parser pode ser representada de forma simplificada como:

```text
programa        → declaração*

declaração      → "let" IDENT "=" expressão ";"
                | "print" expressão ";"

expressão       → termo ("+" termo | "-" termo)*

termo           → fator ("*" fator | "/" fator)*

fator           → NUMBER | IDENT
```

Essa estrutura garante a **precedência dos operadores**.

Por exemplo:

```text
2 + 3 * 4
```

é interpretado como:

```text
2 + (3 * 4)
```

e não:

```text
(2 + 3) * 4
```

---

## 4. Notação pós-fixa

As expressões são convertidas para uma representação em que os operadores aparecem depois dos operandos.

Por exemplo:

```text
42 + 5
```

é representado como:

```text
push 42
push 5
add
```

Outro exemplo:

```text
2 + 3 * 4
```

é representado como:

```text
push 2
push 3
push 4
mul
add
```

O resultado da expressão é então obtido pelo Interpreter através da pilha.

---

## 5. Operadores suportados

| Operador | Operação      | Comando |
| -------- | ------------- | ------- |
| `+`      | Soma          | `add`   |
| `-`      | Subtração     | `sub`   |
| `*`      | Multiplicação | `mul`   |
| `/`      | Divisão       | `div`   |

Os operadores `*` e `/` possuem maior precedência que `+` e `-`.

---

## 6. Variáveis

O tradutor permite declarar e utilizar variáveis através do comando `let`.

Exemplo:

```text
let a = 10 + 5;
print a * 2;
```

A declaração:

```text
let a = 10 + 5;
```

gera comandos semelhantes a:

```text
push 10
push 5
add
pop a
```

O valor calculado é armazenado na variável `a`.

Posteriormente:

```text
print a * 2;
```

utiliza o valor armazenado para realizar a multiplicação.

---

## 7. Exemplo completo

### Entrada

```text
let a = 42 + 5 * 2;
let b = a / 2;
print b + 6;
```

### Tradução

A primeira expressão:

```text
42 + 5 * 2
```

é processada respeitando a precedência:

```text
42 + (5 * 2)
```

Gerando:

```text
push 42
push 5
push 2
mul
add
pop a
```

A segunda expressão:

```text
a / 2
```

gera:

```text
push a
push 2
div
pop b
```

E a última expressão:

```text
b + 6
```

gera:

```text
push b
push 6
add
print
```

### Resultado

```text
32
```

---

## 8. Estrutura do projeto

```text
TradutorAritmetico/
├── src/
│   ├── Main.java
│   ├── Scanner.java
│   ├── Token.java
│   ├── TokenType.java
│   ├── Parser.java
│   └── Interpreter.java
│
└── README.md
```

### Principais arquivos

| Arquivo            | Função                                         |
| ------------------ | ---------------------------------------------- |
| `Main.java`        | Inicia a execução do programa                  |
| `Scanner.java`     | Realiza a análise léxica                       |
| `Token.java`       | Representa um token                            |
| `TokenType.java`   | Define os tipos de tokens                      |
| `Parser.java`      | Realiza a análise sintática e gera os comandos |
| `Interpreter.java` | Executa os comandos gerados                    |

---

## 9. Tecnologias utilizadas

* **Java**
* **Git**
* **GitHub**
* **Visual Studio Code**

---

## 10. Como executar

Com o Java instalado, abra o terminal na pasta do projeto.

### Compilar

```powershell
javac -d bin src/*.java
```

### Executar

```powershell
java -cp bin Main
```

O resultado será exibido diretamente no terminal.

---

## 11. Conclusão

O projeto apresenta uma implementação simplificada do processo de tradução de uma linguagem aritmética. A separação entre **Scanner, Parser e Interpreter** permite visualizar as principais etapas envolvidas no processamento de uma linguagem.

Além das operações básicas de soma e subtração, o projeto também possui suporte a **multiplicação e divisão**, incluindo o tratamento correto da precedência entre os operadores.
