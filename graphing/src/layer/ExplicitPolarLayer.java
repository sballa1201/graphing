package layer;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import structures.ExplicitFunction;

public class ExplicitPolarLayer extends PolarLayer {

	private ExplicitFunction f;
	
	private double maxT = Math.PI * 10;
	
	public ExplicitPolarLayer(String expression) {
		super();
		try {
			this.f = new ExplicitFunction(expression,'t');
		} catch (StackOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StackUnderflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnequalBracketsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void draw() {
		this.clearCanvas();
		gc.setLineWidth(2);
		gc.setStroke(color);
		double t1, t2, r1, r2;
		t1 = 0;
		try {
			r1 = f.evaluate(t1);
		} catch(Exception e) {
			return;
		}
		
		
		double step = Math.PI / 100;
		
		for(t2=step; t2<this.maxT;t2=t2+step) {
			try {
				//System.out.println(this.convertX(x1) + " - " + this.convertY(y1));
				r2 = f.evaluate(t2);
				gc.strokeLine(this.convertX(r1, t1), this.convertY(r1, t1), this.convertX(r2, t2), this.convertY(r2, t2));
				t1 = t2;
				r1 = r2;
			} catch(ArithmeticException e) {
				t1 = t2 + step;
				t2 = t1;
				r1 = f.evaluate(t1);
			}
			
		}
	}

}
