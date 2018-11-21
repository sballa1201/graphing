package layer;

import application.PlotPane;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Layer {
	
	protected Canvas canvas;
	protected GraphicsContext gc;
	protected Color color = Color.BLACK;

	protected IntegerProperty steps = new SimpleIntegerProperty(0);
	protected DoubleProperty minX = new SimpleDoubleProperty(0);
	protected DoubleProperty maxX = new SimpleDoubleProperty(0);
	protected DoubleProperty minY = new SimpleDoubleProperty(0);
	protected DoubleProperty maxY = new SimpleDoubleProperty(0);
	protected DoubleProperty pixelWorthX = new SimpleDoubleProperty(0);
	protected DoubleProperty pixelWorthY = new SimpleDoubleProperty(0);
	
	
	protected Layer() {
		this.canvas = new Canvas();
		this.gc = canvas.getGraphicsContext2D();
	}
	
	public abstract void draw();
	
	public abstract void bindProperties(PlotPane plotPane);
	
	protected void clearCanvas() {
		gc.clearRect(0, 0,canvas.getWidth(), canvas.getHeight());
	}
	
	
	public Canvas getCanvas() {
		return canvas;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
