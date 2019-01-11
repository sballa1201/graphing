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
		this.parameter = parameter; // sets the parameter value
		this.expression = standardize(expression); // removes whitespace + standardizes
		BinaryTree tree = createTree(this.expression); // creates the tree
		this.postFixStack = tree.traverse(); // creates the post-fix stack
	}

	// evaluate the expression for a value of the parameter
	public double evaluate(double x) throws StackUnderflowException, StackOverflowException, NumberFormatException, ArithmeticException {
		// create a stack to store the numerical values
		// and the substitute stack
		Stack<String> subStack = substitute(x);
		Stack<Double> numStack = new Stack<Double>(this.postFixStack.getHeight());
		for (int i = subStack.getHeight(); i > 0; i--) {
			String pop = subStack.pop(); // pop a value of the substitute stack and store it
			double a, b; // initialize two double variables for potential calculation
			switch (pop) { // check if the popped value is a number or operator
			// if it is an operator
			// do the operation on the two numbers at the top of the number stack
			// and replace them both with the result
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
			// if it is a number push it onto the number stack
			default:
				// try to cast the popped value, it is of type String, to double
				// this may fail so the function throws NumberFormatException
				// this will fail if there are multiple parameters e.g. "xy"
				// since it will treat the y as a number which it is not
				numStack.push(Double.valueOf(pop));
				break;
			}
		}
		// pop and return the remaining value in the number stack, this is the evaluated value
		return numStack.pop();
	}

	// substitute a value of the parameter into the expression
	private Stack<String> substitute(double x)
			throws StackUnderflowException, StackOverflowException {
		Stack<String> copy = new Stack<String>(this.postFixStack); // copy the post fix stack so we don't edit it
		Stack<String> subStack = new Stack<String>(this.postFixStack.getHeight()); // create a stack to store the
																					// substituted value of the same
																					// size as the post-fix stack
		for (int i = copy.getHeight(); i > 0; i--) { // loop through the copied stack
			String pop = copy.pop(); // pop an item off and store it in a variable
			if (pop.equals(String.valueOf(this.parameter))) { // check if it is a variable if so replace it with the
																// value
				pop = Double.toString(x);
			}
			subStack.push(pop); // push the value onto the new stack
		}
		return subStack; // return the new stack
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

	@Override
	// output the stack and the expression this object represents
	public String toString() {
		return this.expression + " -> " + this.postFixStack.toString();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		
		System.out.println(new Expression("2e^x", 'x').substitute(10));
		
		// create the standardized expressions
		String ex = standardize("");
		// create the trees
		BinaryTree treeA, treeThreadA;
		Instant start, end;
		start = Instant.now();
		treeThreadA = createTreeThread(ex); 
		end = Instant.now();
		System.out.println("time taken for threaded approach - " + Duration.between(start, end).toMillis() + "ms");
		start = Instant.now();
		treeA = createTree(ex);
		end = Instant.now();
		System.out.println("time taken for sequential approach - " + Duration.between(start, end).toMillis() + "ms");
		 
	}

}
