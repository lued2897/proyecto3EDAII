package Arboles;

import java.io.PrintStream;
import java.util.Stack;


public class Aritmetico extends Tree {
    
    public Aritmetico() {
        super();
    }

    public Aritmetico(int data) {
        super(data);
    }

    public Aritmetico(Node node) {
        super(node);
    }
    
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static void main(String[] args) {
        
        String notacionInfija = "3+4*(2-7)/5";
        System.out.println(notacionInfija);
        
        String notacionPostfija = ShuntingYard.infixToRpn(notacionInfija);
        System.out.println("Por Shunting-Yard: " + notacionPostfija);
        
        //Node r = expressionTree(notacionPostfija);
        //inorder(r);
    }
    
    /*public static Node expressionTree(String postfix){
		Stack<Node> st = new Stack<Node>();
		Node t1,t2,temp;

		for(int i=0;i<postfix.length();i++){
			if(!isOperator(postfix.charAt(i))){
				temp = new Node(postfix.charAt(i));
				st.push(temp);
			}
			else{
				temp = new Node(postfix.charAt(i));

				t1 = st.pop();
				t2 = st.pop();

				temp.left = t2;
				temp.right = t1;

				st.push(temp);
			}

		}
		temp = st.pop();
		return temp;
	}
	public static void inorder(Node root){
		if(root==null) return;

		inorder(root.left);
		System.out.print(root.data);
		inorder(root.right);
	}
    */	
}

