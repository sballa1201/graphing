package structures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;

public class ExplicitYFunction {
	
	private Expression f;
	
	public ExplicitYFunction(String expression) throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		this.f = new Expression(expression, "y");
	}
	
	public double evaluate(double y) {
		try {
			return f.evaluate(y);
		} catch (StackUnderflowException e) {
			System.out.println("underflow");
			e.printStackTrace();
		} catch (StackOverflowException e) {
			System.out.println("overflow");
			e.printStackTrace();
		}
		return Double.NaN;
	}
	
	@Override
	public String toString() {
		return this.f.toString();
	}
	
}
