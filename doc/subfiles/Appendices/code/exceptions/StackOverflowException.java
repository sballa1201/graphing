package exceptions;

@SuppressWarnings("serial")
public class StackOverflowException extends Exception {

	// methods
	// constructor which outputs a custom message
	public StackOverflowException() {
		super("Stack has reached its max height");
	}

}