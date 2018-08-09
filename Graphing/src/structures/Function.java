package structures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;

public class Function {
	
	private Stack<String> postFixStack;
	private String parameter;
	private String expression;
	@SuppressWarnings("unused")
	final private static String[] operators = {"+","-","*","/","^"}; 
	
	
	
	public Function(String expression) {
		this.expression = expression;
		this.parameter = "x";
	}
	
	public double evaluate(double x) throws StackUnderflowException, StackOverflowException {
		Stack<String> subStack = substitute(x);
		//System.out.println(subStack);
		Stack<Double> numStack = new Stack<Double>(this.postFixStack.getHeight());
		for(int i=subStack.getHeight(); i>0 ;i--) {
			String pop = subStack.pop();
			switch(pop) {
			case "+":
				numStack.push(numStack.pop() + numStack.pop());
				break;
			case "-":
				numStack.push(numStack.pop() - numStack.pop());
				break;
			case "*":
				numStack.push(numStack.pop() * numStack.pop());
				break;
			case "/":
				numStack.push(numStack.pop() / numStack.pop());
				break;
			case "^":
				numStack.push(Math.pow(numStack.pop(),  numStack.pop()));
				break;
			default:
				numStack.push(Double.valueOf(pop));
				break;
			}
		}
		return numStack.pop();
		
	}
	
	private Stack<String> substitute(double x) throws StackUnderflowException, StackOverflowException {
		Stack<String> copy = new Stack<String>(this.postFixStack);
		Stack<String> subStack = new Stack<String>(this.postFixStack.getHeight());
		for(int i=copy.getHeight(); i>0 ;i--) {
			String pop = copy.pop();
			if(pop == this.parameter) {
				pop = Double.toString(x);
			} 
			subStack.push(pop);
		}
		subStack.reverse();
		return subStack;
	}
	
	@Override
	public String toString() {
		return this.expression;
	}
	
	public static void main(String[] args) throws StackOverflowException, StackUnderflowException {
		Function f = new Function("4 + 3x");
		Stack<String> s = new Stack<String>(6);
		s.push("-");
		s.push("*");
		s.push("3");
		s.push("x");
		s.push("4");
		System.out.println(s);
		f.postFixStack = s;
		for(int i=0; i<10; i++) {
			System.out.println(i+" - "+f.evaluate(i));
		}
		
	}

}
