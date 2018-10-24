package structures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;

public class ExplicitFunction implements Function {
	
	Expression f;
	
	public ExplicitFunction(String expression) throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		this.f = new Expression(expression);
	}

	@Override
	public double evaluate(double x) {
		try {
			return f.evaluate(x);
		} catch (StackUnderflowException e) {
			System.out.println("underflow");
			e.printStackTrace();
		} catch (StackOverflowException e) {
			// TODO Auto-generated catch block
			System.out.println("overflow");
			e.printStackTrace();
		}
		return Double.NaN;
	}
}
