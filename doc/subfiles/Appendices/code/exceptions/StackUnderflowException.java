package exceptions;

@SuppressWarnings("serial")
public class StackUnderflowException extends Exception {

	// methods
	// constructor which outputs a custom message
	public StackUnderflowException() {
		super("Stack has no items for you to pop");
	}

}