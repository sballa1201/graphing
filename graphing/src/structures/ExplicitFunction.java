package structures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;

public class ExplicitFunction {

	// attributes
	private Expression f; // the actual mathematical function

	// methods
	// empty constructor to allow for inheritance
	public ExplicitFunction() {
	}

	// constructor
	public ExplicitFunction(String expression, char parameter)
			throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		this.setF(new Expression(expression, parameter));
	}

	// evaluate method calls the expression evaluate method
	public double evaluate(double x) {
		// try to evaluate the expression
		//if it fails simply return NaN and the associated error
		try {
			return f.evaluate(x);
		} catch (StackUnderflowException e) {
			System.out.println("underflow");
		} catch (StackOverflowException e) {
			System.out.println("overflow");
		} catch (NumberFormatException e) {
			System.out.println("multiple parameters");
		}
		return Double.NaN;
	}

	// set the expression attribute - used for child classes
	protected void setF(Expression f) {
		this.f = f;
	}

	@Override
	// output the representation of the expression object
	public String toString() {
		return this.f.toString();
	}

}