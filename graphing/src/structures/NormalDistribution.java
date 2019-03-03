package structures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;

public class NormalDistribution extends ExplicitFunction {

	// attributes
	private double μ; // mean
	private double σ2; // variation

	// methods
	// constructor
	public NormalDistribution(double μ, double σ2)
			throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		// super constructor must be called first in Java
		super();

		// set the attributes
		this.μ = μ;
		this.σ2 = σ2;

		// create the expression for the normal distribution function
		String expression = "(1/(2*pi*σ2)^(1/2))*e^(-(x-μ)^2/(2*σ2^(1/2)))";
		// substitute the mean and variance values
		expression = expression.replace("σ2", Double.toString(σ2));
		expression = expression.replace("μ", Double.toString(μ));

		// set the new expression
		this.setF(new Expression(expression, 'x'));
	}

	// return the mean
	public double getΜ() {
		return μ;
	}

	// return the variance
	public double getΣ2() {
		return σ2;
	}

	// return the standard deviation
	public double getΣ() {
		return Math.sqrt(σ2);
	}

}