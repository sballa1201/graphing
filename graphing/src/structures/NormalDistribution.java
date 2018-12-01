package structures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;

public class NormalDistribution extends ExplicitFunction {

	private double μ;
	private double σ;

	public NormalDistribution(double μ, double σ)
			throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		super();

		this.μ = μ;
		this.σ = σ;

		String expression = "(1/(2*pi*σ^2)^(1/2))*e^(-(x-μ)^2/(2*σ^2))";
		expression = expression.replace("σ", Double.toString(σ));
		expression = expression.replace("μ", Double.toString(μ));
		System.out.println(expression);

		this.setF(new Expression(expression, 'x'));
	}

	public double getΜ() {
		return μ;
	}

	public double getΣ() {
		return σ;
	}

}
