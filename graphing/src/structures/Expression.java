package structures;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;

public class Expression {

	// attributes
	private Stack<String> postFixStack;
	private char parameter; // this allows us to adapt our explicit function
							// for something that isn't in x
	private String expression;

	// methods
	// constructor
	public Expression(String expression, char parameter)
			throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		this.parameter = parameter;
		this.expression = standardize(expression);
		BinaryTree tree = createTree(this.expression);
		this.postFixStack = tree.traverse();
	}

	// evaluate the expression for a value of the parameter
	public double evaluate(double x) throws StackUnderflowException, StackOverflowException {
		Stack<String> subStack = substitute(x);
		Stack<Double> numStack = new Stack<Double>(this.postFixStack.getHeight());
		for (int i = subStack.getHeight(); i > 0; i--) {
			String pop = subStack.pop();
			double a, b;
			switch (pop) {
			case "+":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(a + b);
				break;
			case "-":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(a - b);
				break;
			case "*":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(a * b);
				break;
			case "/":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(a / b);
				break;
			case "^":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(Math.pow(a, b));
				break;
			default:
				try {
					numStack.push(Double.valueOf(pop));
				} catch (NumberFormatException e) {
					throw e;
				}
				break;
			}
		}
		return numStack.pop();

	}

	// substitute a value of the parameter into the expression
	private Stack<String> substitute(double x) throws StackUnderflowException, StackOverflowException {
		Stack<String> copy = new Stack<String>(this.postFixStack);
		Stack<String> subStack = new Stack<String>(this.postFixStack.getHeight());
		for (int i = copy.getHeight(); i > 0; i--) {
			String pop = copy.pop();
			if (pop.equals(String.valueOf(this.parameter))) {
				pop = Double.toString(x);
			}
			subStack.push(pop);
		}
		return subStack;
	}

	// create the abstract syntax tree for the expression - single-threaded
	private static BinaryTree createTree(String expression) throws UnequalBracketsException {
		// remove enclosing matching brackets
		expression = checkBracket(expression);
		// find the least significant operator, if an operator remains
		int leastSigOperatorPos = leastSigOperatorPos(expression);
		if (leastSigOperatorPos == -1) { // base case - no operators remain
			return new BinaryTree(expression);
		} else { // recursive case - recurse on the sub-expressions
			// locate and hold the operator
			String operator = String.valueOf(expression.charAt(leastSigOperatorPos));
			// split the expression into sub-expressions by the operator and recurse on them
			String a = expression.substring(0, leastSigOperatorPos);
			String b = expression.substring(leastSigOperatorPos + 1);
			// return the new tree containing the trees of the sub-expressions and the
			// operator as the root value
			return new BinaryTree(operator, createTree(a), createTree(b));
		}
	}

	// create the abstract syntax tree for the expression - multi-threaded
	private static BinaryTree createTreeThread(String expression)
			throws StringIndexOutOfBoundsException, UnequalBracketsException, InterruptedException, ExecutionException {
		// remove enclosing matching brackets
		expression = checkBracket(expression);
		// find the least significant operator, if an operator remains
		int leastSigOperatorPos = leastSigOperatorPos(expression);
		if (leastSigOperatorPos == -1) { // base case - no operators remain
			return new BinaryTree(expression);
		} else { // recursive case - recurse on the sub-expressions
			// locate and hold the operator
			String operator = String.valueOf(expression.charAt(leastSigOperatorPos));
			// split the expression into sub-expressions by the operator and recurse on them
			String a = expression.substring(0, leastSigOperatorPos);
			String b = expression.substring(leastSigOperatorPos + 1);
			BinaryTree tree0;
			BinaryTree tree1;
			// create the threads and execute them
			ExecutorService executor0 = Executors.newSingleThreadExecutor();
			Future<BinaryTree> future0 = executor0.submit(() -> {
				return createTree(a);
			});
			ExecutorService executor1 = Executors.newSingleThreadExecutor();
			Future<BinaryTree> future1 = executor1.submit(() -> {
				return createTree(b);
			});
			// return the tree values from the threads
			tree0 = future0.get();
			tree1 = future1.get();
			// shutdown the threads
			executor0.shutdown();
			executor1.shutdown();
			// return the new tree containing the trees of the sub-expressions and the
			// operator as the root value
			return new BinaryTree(operator, tree0, tree1);
		}
	}

	// find the least significant operator in an expression
	private static int leastSigOperatorPos(String input) {
		int parenthesis = 0;
		int leastSigOperatorPos = -1;
		int leastSigOpcode = 1000; // make the opcode super high so every operator is more significant than it
		// used a string to store the operators as it is essentially a char array but
		// with added utility such as finding elements
		final String operators = "+-*/^";
		int currentOpcode;
		// loop through the entire input
		for (int i = 0; i < input.length(); i++) {
			char currentChar = input.charAt(i);
			currentOpcode = operators.indexOf(currentChar);
			// check if it is an operator
			if (currentOpcode >= 0) {
				// check if it the same or more significant and if it is not enclosed in
				// parenthesis
				if ((currentOpcode <= leastSigOpcode) && (parenthesis == 0)) {
					// update the significance and the position of the operator
					leastSigOperatorPos = i;
					leastSigOpcode = currentOpcode;
				}
			} else if (currentChar == '(') {
				// increment to signify we have entered an enclosing bracket
				parenthesis++;
			} else if (currentChar == ')') {
				// decrement to signify we have exited an enclosing bracket
				parenthesis--;
			}
		}
		return leastSigOperatorPos;
	}

	// check for and remove enclosing matching brackets
	private static String checkBracket(String input) throws UnequalBracketsException, StringIndexOutOfBoundsException {
		boolean done = false;
		// check if there are an equal number of opening and closing brackets
		if ((input.length() - input.replace("(", "").length()) != (input.length() - input.replace(")", "").length())) {
			throw new UnequalBracketsException(input);
		}
		while (!done) { // repeat until there are no enclosing brackets
			done = true;// assume no enclosing matching brackets until otherwise found
			// check if there are enclosing brackets (not necessarily matching)
			if ((input.charAt(0) == '(') && (input.charAt(input.length() - 1) == ')')) {
				int countMatching = 1; // initialize the bracket count as 1 to count for the opening bracket
				// loop from the second character to one before the end
				for (int i = 1; i < input.length() - 1; i++) {
					if (countMatching == 0) { // if we find the matching bracket before the end terminate and return the
												// input
						return input;
					} else if (input.charAt(i) == '(') {
						// increment if we find an opening bracket
						countMatching++;
					} else if (input.charAt(i) == ')') {
						// decrement if we find an closing bracket
						countMatching--;
					}
				}
				// if the last character is the matching bracket keep the outer while loop going
				// and and remove the enclosing bracket
				if (countMatching == 1) {
					input = input.substring(1, input.length() - 1);
					done = false;
				}
			}
		}
		return input;
	}

	// standardize an expression to remove ambiguity
	private static String standardize(String expression) {
		// remove whitespace
		expression = expression.replace(" ", "");
		// make pi one character for faster regex
		expression = expression.replace("pi", "π");
		// inconsistency 1
		String regex1 = "([^\\(\\)\\+\\-\\*\\/\\^])([\\(a-zπ])";
		String replace1 = "$1*$2";
		// inconsistency 2
		String regex2 = "([\\)a-zπ])([^\\(\\)\\+\\-\\*\\/\\^])";
		String replace2 = "$1*$2";
		// inconsistency 3
		String regex3 = "\\)\\(";
		String replace3 = ")*(";
		// inconsistency 4
		String regex4 = "([\\+\\-\\*\\/\\^])-([^\\+\\-\\*\\/\\(\\)\\^]*)";
		String replace4 = "$1(-$2)";
		// inconsistency 5
		String regex5 = "(^|\\()-";
		String replace5 = "$10-";

		// loop until check and expression are equal
		String check = "";
		while (!expression.equals(check)) {
			check = expression;
			// perform regex
			expression = expression.replaceAll(regex1, replace1);
			expression = expression.replaceAll(regex2, replace2);
			expression = expression.replaceAll(regex3, replace3);
			expression = expression.replaceAll(regex4, replace4);
			expression = expression.replaceAll(regex5, replace5);
		}

		// replace constants with numerical values
		expression = expression.replaceAll("e", Double.toString(Math.E));
		expression = expression.replaceAll("π", Double.toString(Math.PI));
		return expression;
	}

	public double bisection(double a, double b, int iterations) throws StackUnderflowException, StackOverflowException {
		if ((this.evaluate(a) > 0 && this.evaluate(b) < 0) || (this.evaluate(b) > 0 && this.evaluate(a) < 0)) {
			double c = 0;
			for (int i = 0; i < iterations; i++) {
				c = (a + b) / 2;
				double fA = this.evaluate(a);
				double fC = this.evaluate(c);

				if (fC < 0) {
					if (fA > 0) {
						b = c;
					} else {
						a = c;
					}
				} else {
					if (fA > 0) {
						a = c;
					} else {
						b = c;
					}
				}

			}
			return c;
		} else {
			return Double.NaN;
		}
	}

	public double falsePositive(double a, double b, int iterations)
			throws StackUnderflowException, StackOverflowException {
		if ((this.evaluate(a) > 0 && this.evaluate(b) < 0) || (this.evaluate(b) > 0 && this.evaluate(a) < 0)) {
			double c = 0;
			for (int i = 0; i < iterations; i++) {

				double fA = this.evaluate(a);
				double fB = this.evaluate(b);
				double fC = this.evaluate(c);

				c = (a * fB - b * fA) / (fB - fA);
				if (fC < 0) {
					if (fA > 0) {
						b = c;
					} else {
						a = c;
					}
				} else {
					if (fA > 0) {
						a = c;
					} else {
						b = c;
					}
				}
			}
			return c;
		} else {
			return Double.NaN;
		}
	}

	@Override
	public String toString() {
		return this.expression + " -> " + this.postFixStack.toString();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws StringIndexOutOfBoundsException, UnequalBracketsException,
			StackOverflowException, InterruptedException, ExecutionException {
		// create the standardized expressions
		String ex = new String(new char[100]).replace("\0", "x");
		// String ex = "2x^(2x+4)";
		String a = standardize(ex);
		// create the trees
		BinaryTree treeA, treeB, treeThreadA, treeThreadB;
		Instant start, end;

		start = Instant.now();
		treeThreadA = createTreeThread(a);
		end = Instant.now();
		System.out.println("time taken for threaded approach - " + Duration.between(start, end).toMillis() + "ms");
		start = Instant.now();
		treeA = createTree(a);
		end = Instant.now();
		System.out.println("time taken for sequential approach - " + Duration.between(start, end).toMillis() + "ms");
		System.out.println();
	}

}
