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

	// attributes
	// drawing tools
	protected Canvas canvas;
	protected GraphicsContext gc;
	protected Color color = Color.BLACK;
	// drawing parameters
	protected IntegerProperty steps = new SimpleIntegerProperty(0);
	protected DoubleProperty minX = new SimpleDoubleProperty(0);
	protected DoubleProperty maxX = new SimpleDoubleProperty(0);
	protected DoubleProperty minY = new SimpleDoubleProperty(0);
	protected DoubleProperty maxY = new SimpleDoubleProperty(0);
	protected DoubleProperty pixelWorthX = new SimpleDoubleProperty(0);
	protected DoubleProperty pixelWorthY = new SimpleDoubleProperty(0);

	// methods
	// constructor
	protected Layer() {
		// instantiate the canvas and create the OpenGL context
		this.canvas = new Canvas();
		this.gc = canvas.getGraphicsContext2D();
	}

	// will draw onto the canvas, will be overridden
	public abstract void draw();

	// will connect this layer to a PlotPane instance, will be overridden
	public abstract void bindProperties(PlotPane plotPane);

	// clear the canvas to be redrawn
	protected void clearCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	// getter for the canvas
	public Canvas getCanvas() {
		return canvas;
	}

	// change the color of the function drawn
	public void setColor(Color color) {
		this.color = color;
	}

}