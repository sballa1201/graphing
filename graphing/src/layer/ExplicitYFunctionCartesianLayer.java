package layer;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import structures.ExplicitFunction;

public class ExplicitYFunctionCartesianLayer extends CartesianLayer {

	private ExplicitFunction f;

	public ExplicitYFunctionCartesianLayer(String expression) {
		super();

		try {
			this.f = new ExplicitFunction(expression, 'y');
		} catch (StackOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StackUnderflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnequalBracketsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public ExplicitYFunctionCartesianLayer(ExplicitFunction f) {
		super();
		this.f = f;
	}
	
	public void draw() {
		this.clearCanvas();
		gc.setLineWidth(2);
		gc.setStroke(color);
		double x1, x2, y1, y2;
		y1 = this.minY.doubleValue();
			x1 = f.evaluate(y1);

		double step = (this.maxY.doubleValue() - this.minY.doubleValue()) / this.steps.doubleValue();

		for (y2 = this.minY.doubleValue() + step; y2 < this.maxY.doubleValue(); y2 = y2 + step) {
				x2 = f.evaluate(y2);
				gc.strokeLine(this.convertX(x1), this.convertY(y1), this.convertX(x2), this.convertY(y2));
				x1 = x2;
				y1 = y2;

		}

	}

	@Override
	public String toString() {
		return this.f.toString();
	}

}
