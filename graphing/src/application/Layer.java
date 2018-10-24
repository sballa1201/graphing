package application;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import structures.*;

public class Layer {
	
	private Function f;
	
	private Canvas canvas;
	private GraphicsContext gc;
	private Color color = Color.BLACK;
	
	private int steps = 1000;
	private double minX = -10;
	private double maxX = 10;
	private double minY = -10;
	private double maxY = 10;
	private double pixelWorthX;
	private double pixelWorthY;
	
	
	public Layer(String expression) {
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

	public void bindProperties(Pane plotPane) {
		canvas.heightProperty().bind(plotPane.heightProperty());
		canvas.widthProperty().bind(plotPane.widthProperty());
	}
	
	public void drawFunction() throws StackUnderflowException, StackOverflowException {
		this.clearCanvas();
		gc.setLineWidth(1.5);
		gc.setStroke(color);
		double x1, x2, y1, y2;
		x1 = this.minX;
		y1 = f.evaluate(x1);
		
		double step = (this.maxX - this.minX)/this.steps;

		for(x2=this.minX+step; x2<this.maxX;x2=x2+step) {
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
		
		x2 = this.maxX;
		y2 = f.evaluate(x2);
		gc.strokeLine(this.convertX(x1), this.convertY(y1), this.convertX(x2), this.convertY(y2));
	}
	
	private void clearCanvas() {
		gc.clearRect(0, 0,canvas.getWidth(), canvas.getHeight());
	}
	
	private double convertX(double x) {
		x = x - this.minX;
		x = x /this.pixelWorthX;
		return x;
	}
	
	private double convertY(double y) {
		y = this.maxY - y;
		y = y/this.pixelWorthY;
		return y;
	}
	
	public void setSteps(int steps) {
		this.steps = steps;
	}


	public void setMinX(double minX) {
		this.minX = minX;
	}


	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}


	public void setMinY(double minY) {
		this.minY = minY;
	}


	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}


	public void setPixelWorthX(double pixelWorthX) {
		this.pixelWorthX = pixelWorthX;
	}


	public void setPixelWorthY(double pixelWorthY) {
		this.pixelWorthY = pixelWorthY;
	}

	public Canvas getCanvas() {
		return canvas;
	}	

}
