package Arboles;

import java.io.PrintStream;
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
        //return temp;
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
                // Operand
                stack.push(Character.getNumericValue(node.data));
            } else if (isOperator(node.data)) {
                // Operator
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
    
    
    public static void main(String[] args) {
        
        String notacionInfija = "3+4*(2-7)/5";// = -1
        System.out.println(notacionInfija);
        
        String notacionPostfija = ShuntingYard.infixToRpn(notacionInfija);
        System.out.println("Notacion Polaca Inversa: " + notacionPostfija);
                
        Aritmetico arbolAritmetico = new Aritmetico(notacionPostfija);
        //Node expressionTreeRoot = arbolAritmetico.expressionTree(notacionPostfija);
        //arbolAritmetico.root = expressionTreeRoot;

        // Evaluate the expression tree
        int result = arbolAritmetico.evaluateExpressionTree(arbolAritmetico.root);
        System.out.println("Resultado: " + result);
        
        // Imprimir el resultado de la operación
        arbolAritmetico.printExpressionTree(System.out);
        
    }
}

