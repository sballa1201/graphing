package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Layer {
	
	protected Canvas canvas;
	protected GraphicsContext gc;
	protected Color color = Color.WHITE;
	
	protected IntegerProperty steps = new SimpleIntegerProperty(1000);
	protected DoubleProperty minX = new SimpleDoubleProperty(-10);
	protected DoubleProperty maxX = new SimpleDoubleProperty(10);
	protected DoubleProperty minY = new SimpleDoubleProperty(-10);
	protected DoubleProperty maxY = new SimpleDoubleProperty(10);
	protected DoubleProperty pixelWorthX = new SimpleDoubleProperty();
	protected DoubleProperty pixelWorthY = new SimpleDoubleProperty();
	
	public abstract void drawFunction();
	
	public void bindProperties(PlotPaneController controller) {
		canvas.heightProperty().bind(controller.getPlotPane().heightProperty());
		canvas.widthProperty().bind(controller.getPlotPane().widthProperty());
		this.steps.bind(controller.getSteps());
		this.minX.bind(controller.getMinX());
		this.maxX.bind(controller.getMaxX());
		this.minY.bind(controller.getMinY());
		this.maxX.bind(controller.getMaxY());
		this.pixelWorthX.bind(controller.getPixelWorthX());
		this.pixelWorthY.bind(controller.getPixelWorthY());
	}
	
	protected void clearCanvas() {
		gc.clearRect(0, 0,canvas.getWidth(), canvas.getHeight());
	}
	
	protected double convertX(double x) {
		x = x - this.minX.doubleValue();
		x = x /this.pixelWorthX.doubleValue();
		return x;
	}
	
	protected double convertY(double y) {
		y = this.maxY.doubleValue() - y;
		y = y/this.pixelWorthY.doubleValue();
		return y;
	}
	
/*	public void setSteps(int steps) {
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
	}*/

	public Canvas getCanvas() {
		return canvas;
	}	
}
