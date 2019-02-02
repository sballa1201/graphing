package layer;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import structures.ExplicitFunction;

public class ExplicitXFunctionCartesianLayer extends CartesianLayer {

	// attributes
	private ExplicitFunction f;

	// methods
	// constructor - create a layer with an expression given
	public ExplicitXFunctionCartesianLayer(String expression) {
		// super constructor must be invoked first
		super();
		// try to create the function with the given expression
		try {
			this.f = new ExplicitFunction(expression, 'x');
		} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
			// output the error in parsing the function
			e.printStackTrace();
		}
	}

	// constructor - create a layer with a set function
	public ExplicitXFunctionCartesianLayer(ExplicitFunction f) {
		// super constructor must be invoked first
		super();
		// set the function
		this.f = f;
	}

	// draw the function
	public void draw() {
		// setup the canvas by clearing it and set up the line to be drawn
		this.clearCanvas();
		gc.setLineWidth(2);
		gc.setStroke(color);
		double x1, x2, y1, y2;
		// initialize the first value of x
		x1 = this.minX.doubleValue();
		// evaluate the first value of y
		y1 = f.evaluate(x1);
		// calculate the dx value i.e. the step value
		double step = (this.maxX.doubleValue() - this.minX.doubleValue()) / this.steps.doubleValue();
		// loop until we draw everything in the range, minX to minX, required
		for (x2 = this.minX.doubleValue() + step; x2 < this.maxX.doubleValue(); x2 = x2 + step) {
			// evaluate the next value of y
			y2 = f.evaluate(x2);
			// draw the line
			gc.strokeLine(this.convertX(x1), this.convertY(y1), this.convertX(x2), this.convertY(y2));
			// update the old values
			x1 = x2;
			y1 = y2;
		}
	}

	@Override
	// output the function that this layer is drawing
	public String toString() {
		return this.f.toString();
	}

}