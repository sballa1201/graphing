package exceptions;

@SuppressWarnings("serial")
public class StackUnderflowException extends Exception {

	public StackUnderflowException() {
		super("Stack has no items for you to pop");
	}

}
