package exceptions;

@SuppressWarnings("serial")
public class UnequalBracketsException extends Exception {
	
	String expression;
	
	public UnequalBracketsException(String expression) {
		super("There are an unequal number of opening and closing brackets in the expression: " + expression);
		this.expression = expression;
	}

}
