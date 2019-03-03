package exceptions;

@SuppressWarnings("serial")
public class UnequalBracketsException extends Exception {

	// methods
	// constructor which outputs a custom message
	public UnequalBracketsException(String expression) {
		super("There are an unequal number of opening and closing brackets in the expression: " + expression);
	}

}