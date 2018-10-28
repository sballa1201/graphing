package application;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import structures.*;

public class ExplicitFunctionLayer extends Layer {
	
	private ExplicitFunction f;	
	
	public ExplicitFunctionLayer(String expression) {
		try {
			this.f = new ExplicitFunction(expression);
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
		this.canvas = new Canvas();
		this.gc = canvas.getGraphicsContext2D();
		
		
	}
	
	public void drawFunction() {
		this.clearCanvas();
		gc.setLineWidth(1.5);
		gc.setStroke(color);
		double x1, x2, y1, y2;
		x1 = this.minX.doubleValue();
		y1 = f.evaluate(x1);
		
		double step = (this.maxX.doubleValue() - this.minX.doubleValue())/this.steps.doubleValue();

		for(x2=this.minX.doubleValue()+step; x2<this.maxX.doubleValue();x2=x2+step) {
			try {
				y2 = f.evaluate(x2);
				gc.strokeLine(this.convertX(x1), this.convertY(y1), this.convertX(x2), this.convertY(y2));
				x1 = x2;
				y1 = y2;
			} catch(ArithmeticException e) {
				x1 = x2 + step;
				x2 = x1;
				y1 = f.evaluate(x1);
			}
			
		}
		
		x2 = this.maxX.doubleValue();
		y2 = f.evaluate(x2);
		gc.strokeLine(this.convertX(x1), this.convertY(y1), this.convertX(x2), this.convertY(y2));
	}
	
	

}
