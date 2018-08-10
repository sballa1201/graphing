package exceptions;

@SuppressWarnings("serial")
public class StackOverflowException extends Exception {

	public StackOverflowException() {
		super("Stack has reached its max height");
	}

}
