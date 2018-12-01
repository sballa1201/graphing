package structures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;

public class ExplicitFunction {

	private Expression f;

	public ExplicitFunction() {
	}

	public ExplicitFunction(String expression, char parameter)
			throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		this.setF(new Expression(expression, parameter));
	}

	public double evaluate(double x) {
		try {
			return f.evaluate(x);
		} catch (StackUnderflowException e) {
			System.out.println("underflow");
			e.printStackTrace();
		} catch (StackOverflowException e) {
			System.out.println("overflow");
			e.printStackTrace();
		}
		return Double.NaN;
	}

	public void setF(Expression f) {
		this.f = f;
	}

	@Override
	public String toString() {
		return this.f.toString();
	}

}
