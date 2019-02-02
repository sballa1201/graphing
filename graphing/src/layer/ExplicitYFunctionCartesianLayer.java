package layer;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import structures.ExplicitFunction;

public class ExplicitYFunctionCartesianLayer extends CartesianLayer {

	// attributes
	private ExplicitFunction f;

	// methods
	// constructor - create a layer with an expression given
	public ExplicitYFunctionCartesianLayer(String expression) {
		// super constructor must be invoked first
		super();
		// try to create the function with the given expression
		try {
			this.f = new ExplicitFunction(expression, 'y');
		} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
			// output the error in parsing the function
			e.printStackTrace();
		}
	}

	// constructor - create a layer with a set function
	public ExplicitYFunctionCartesianLayer(ExplicitFunction f) {
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
		// initialize the first value of y
		y1 = this.minY.doubleValue();
		// evaluate the first value of x
		x1 = f.evaluate(y1);
		// calculate the dy value i.e. the step value
		double step = (this.maxY.doubleValue() - this.minY.doubleValue()) / this.steps.doubleValue();
		// loop until we draw everything in the range, minY to maxY, required
		for (y2 = this.minY.doubleValue() + step; y2 < this.maxY.doubleValue(); y2 = y2 + step) {
			// evaluate the next value of y
			x2 = f.evaluate(y2);
			// draw the line
			gc.strokeLine(this.convertX(x1), this.convertY(y1), this.convertX(x2), this.convertY(y2));
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