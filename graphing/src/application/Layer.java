package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Layer {
	
	protected Canvas canvas;
	protected GraphicsContext gc;
	protected Color color = Color.BLACK;
	
	protected int steps = 1000;
	protected double minX = -10;
	protected double maxX = 10;
	protected double minY = -10;
	protected double maxY = 10;
	protected double pixelWorthX;
	protected double pixelWorthY;
	
	public abstract void drawFunction();
	
	public void bindProperties(Pane plotPane) {
		canvas.heightProperty().bind(plotPane.heightProperty());
		canvas.widthProperty().bind(plotPane.widthProperty());
	}
	
	protected void clearCanvas() {
		gc.clearRect(0, 0,canvas.getWidth(), canvas.getHeight());
	}
	
	protected double convertX(double x) {
		x = x - this.minX;
		x = x /this.pixelWorthX;
		return x;
	}
	
	protected double convertY(double y) {
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
