
package Arboles;

import java.util.Stack;
import java.lang.Character;

public class ShuntingYard {

	private static boolean letterOrDigit(char c){
		if (Character.isLetterOrDigit(c))
			return true;
		else
			return false;
	}

	static int getPrecedence(char ch){
		if (ch == '+' || ch == '-')
			return 1;
		else if (ch == '*' || ch == '/')
			return 2;
		else if (ch == '^')
			return 3;
		else
			return -1;
	}
	
	static boolean hasLeftAssociativity(char ch) {
		if (ch == '+' || ch == '-' || ch == '/' || ch == '*') {
			return true;
		} else {
			return false;
		}
	}

	public static String infixToRpn(String expression){

		Stack<Character> stack = new Stack<>();

		String output = new String("");

		for (int i = 0; i < expression.length(); ++i) {
			char c = expression.charAt(i);

			if (letterOrDigit(c))
				output += c;

			else if (c == '(')
				stack.push(c);

			else if (c == ')') {
				while (!stack.isEmpty()
					&& stack.peek() != '(')
					output += stack.pop();

				stack.pop();
			}

			else {
				while( !stack.isEmpty()
					&& getPrecedence(c)
						<= getPrecedence(stack.peek()) 
					&& hasLeftAssociativity(c)) {
					
					output += stack.pop();
				}
				stack.push(c);
			}
		}

		while (!stack.isEmpty()) {
			if (stack.peek() == '(')
				return "This expression is invalid";
			output += stack.pop();
		}
		return output;
	}

	/*public static void main(String[] args){
		String expression = "3+4*(2-7)/5";
		System.out.println(infixToRpn(expression));
	}*/
}
