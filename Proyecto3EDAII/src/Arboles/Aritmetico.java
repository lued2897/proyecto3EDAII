package Arboles;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

public class Aritmetico{
    Node root;
    
    public Aritmetico() {
        super();
    }

    public Aritmetico(char data) {
        root= new Node(data);
    }
    
    public Aritmetico(String postfix) {
        expressionTree(postfix);
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    
    class Node{
	char data;
	Node left,right;
	public Node(char data){
            this.data = data;
            left = right = null;
	}
    }
    
    private void expressionTree(String postfix) {
        Stack<Node> st = new Stack<>();
        Node t1, t2, temp;

        for (int i = 0; i < postfix.length(); i++) {
            if (!isOperator(postfix.charAt(i))) {
                temp = new Node(postfix.charAt(i));
                st.push(temp);
            } else {
                temp = new Node(postfix.charAt(i));

                t1 = st.pop();
                t2 = st.pop();

                temp.left = t2;
                temp.right = t1;

                st.push(temp);
            }
        }
        temp = st.pop();
        this.root=temp;
    }
    
    
    public static void inorder(Node root){
        if(root==null) return;
        
        inorder(root.left);
	System.out.print(root.data);
	inorder(root.right);
    }
    
    public void traversePreOrder(StringBuilder sb, String padding, String pointer, Node node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.data);  
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("│  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└── ";
            String pointerForLeft = (node.right != null) ? "├── " : "└── ";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.left);
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.right);
        }
    }

    public void printExpressionTree(PrintStream os) {
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "", root); 
        os.print(sb.toString());
    }
    
    
    public int evaluateExpressionTree(Node root) {
        if (root == null) {
            throw new IllegalArgumentException("Expression tree is empty.");
        }

        Stack<Integer> stack = new Stack<>();
        postOrderEvaluation(root, stack);

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression tree.");
        }

        return stack.pop();
    }

    private void postOrderEvaluation(Node node, Stack<Integer> stack) {
        if (node != null) {
            postOrderEvaluation(node.left, stack);
            postOrderEvaluation(node.right, stack);

            if (Character.isDigit(node.data)) {
                // Operando
                stack.push(Character.getNumericValue(node.data));
            } else if (isOperator(node.data)) {
                // Operador
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = performOperation(operand1, operand2, node.data);
                stack.push(result);
            } else {
                throw new IllegalArgumentException("Invalid node in the expression tree.");
            }
        }
    }

    private int performOperation(int operand1, int operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero.");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
    
    public static void menu() {
        
        Scanner scanner = new Scanner(System.in);
        Aritmetico arbolAritmetico = null;

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Ingresar expresión aritmética");
            System.out.println("2. Imprimir árbol");
            System.out.println("3. Resolver expresión ");
            System.out.println("4. Salir");

            System.out.print("Ingrese su opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la expresión aritmética: ");
                    scanner.nextLine();
                    String expresion = scanner.nextLine();
                    String expresionPostfija = ShuntingYard.infixToRpn(expresion);
                    arbolAritmetico = new Aritmetico(expresionPostfija);
                    System.out.println("Árbol creado correctamente.");
                    break;
                case 2:
                    if (arbolAritmetico != null) {
                        System.out.println("Árbol de expresión:");
                        arbolAritmetico.printExpressionTree(System.out);
                    } else {
                        System.out.println("Primero ingrese la expresión aritmética y cree el árbol.");
                    }
                    break;
                case 3:
                    if (arbolAritmetico != null) {
                        int resultado = arbolAritmetico.evaluateExpressionTree(arbolAritmetico.root);
                        System.out.println("Resultado: " + resultado);
                    } else {
                        System.out.println("Primero ingrese la expresión aritmética y cree el árbol.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del programa.");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}
