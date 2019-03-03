package layer;

import java.text.DecimalFormat;

import application.PlotPane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class InputLayer extends Layer {

	// attributes
	private BooleanProperty changeViewport = new SimpleBooleanProperty(true);
	private double currentX;
	private double currentY;
	private double previousX;
	private double previousY;

	// methods
	// constructor
	public InputLayer() {
		// call the super constructor
		super();
		// update pixel worth when the canvas changes size
		this.canvas.heightProperty().addListener(event -> updatePixelWorth());
		this.canvas.widthProperty().addListener(event -> updatePixelWorth());
		// bind the mouse events to their respective actions
		this.canvas.setOnMousePressed(event -> dragEntered(event));
		this.canvas.setOnMouseDragged(event -> pan(event));
		this.canvas.setOnScroll(event -> zoom(event));
		this.canvas.setOnMouseMoved(event -> drawCoords(event));
	}

	@Override
	// this canvas will not draw anything so this method is empty
	// it must still be implemented since the method was abstract in the parent
	// class
	public void draw() {
	}

	// update the pixelworth
	private void updatePixelWorth() {
		this.pixelWorthX.set((this.maxX.doubleValue() - this.minX.doubleValue()) / this.canvas.getWidth());
		this.pixelWorthY.set((this.maxY.doubleValue() - this.minY.doubleValue()) / this.canvas.getHeight());
	}

	@Override
	// initialize the properties required so they can be bound later
	public void bindProperties(PlotPane plotPane) {
		// initialize the properties
		this.steps = new SimpleIntegerProperty(400);
		this.minX = new SimpleDoubleProperty(-10);
		this.maxX = new SimpleDoubleProperty(10);
		this.minY = new SimpleDoubleProperty(-10);
		this.maxY = new SimpleDoubleProperty(10);
		this.pixelWorthX = new SimpleDoubleProperty(1);
		this.pixelWorthY = new SimpleDoubleProperty(1);
		// make the canvas resize as its parent resizes by binding the associated
		// properties
		this.canvas.heightProperty().bind(plotPane.heightProperty());
		this.canvas.widthProperty().bind(plotPane.widthProperty());
	}

	// draw the coordinates
	private void drawCoords(MouseEvent event) {
		// clear the previous coordinates
		clearCanvas();
		// get the coordinates
		double x = (event.getX() * this.pixelWorthX.doubleValue()) + this.minX.doubleValue();
		double y = this.maxY.doubleValue() - (event.getY() * this.pixelWorthY.doubleValue());
		// round the coordinates to 2 decimal places
		String sX = new DecimalFormat("#.##").format(x);
		String sY = new DecimalFormat("#.##").format(y);
		String out = "(" + sX + "," + sY + ")";
		// draw the coordinates to the canvas
		gc.setLineWidth(1);
		gc.strokeText(out, 0, 10);
	}

	// when drag starts initiate the full-drag sequence
	private void dragEntered(MouseEvent event) {
		// only initiate the drag if its the left mouse button
		if (event.isPrimaryButtonDown()) {
			// need to initialize the values of previousX and previousY
			previousX = event.getX();
			previousY = event.getY();
			// start full drag
			event.setDragDetect(true);
			this.canvas.startFullDrag();
		}
	}

	// pan around the plotpane
	private void pan(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			// get the current mouse coordinates
			currentX = event.getX();
			currentY = event.getY();
			// get the vector of the mouse movement
			double xTrans = (previousX - currentX);
			double yTrans = (previousY - currentY);
			// update the previous values to be the current ones
			previousX = currentX;
			previousY = currentY;
			// translate the viewport
			this.maxX.set((this.pixelWorthX.doubleValue() * xTrans) + this.maxX.doubleValue());
			this.minX.set((this.pixelWorthX.doubleValue() * xTrans) + this.minX.doubleValue());
			this.maxY.set(-(this.pixelWorthY.doubleValue() * yTrans) + this.maxY.doubleValue());
			this.minY.set(-(this.pixelWorthY.doubleValue() * yTrans) + this.minY.doubleValue());
			this.changeViewport.set(!this.changeViewport.get());
		}
	}

	// zoom in and out
	private void zoom(ScrollEvent event) {
		double zoomFactor = 1.05;
		// this is whether it is scrolling up or down
		double deltaY = event.getDeltaY();
		// get the coordinates
		double x = (event.getX() * this.pixelWorthX.doubleValue()) + this.minX.doubleValue();
		double y = this.maxY.doubleValue() - (event.getY() * this.pixelWorthY.doubleValue());
		// if scrolling down shrink the viewport instead
		if (deltaY > 0) {
			zoomFactor = 1 / zoomFactor;
		}
		// translate the viewport to the origin, scale the viewport,
		// translate the viewport back and set the new values for the viewport
		this.minX.set((this.minX.doubleValue() - x) * zoomFactor + x);
		this.maxX.set((this.maxX.doubleValue() - x) * zoomFactor + x);
		this.minY.set((this.minY.doubleValue() - y) * zoomFactor + y);
		this.maxY.set((this.maxY.doubleValue() - y) * zoomFactor + y);
		// update and notify the plotpane
		this.updatePixelWorth();
		this.changeViewport.set(!this.changeViewport.get());
	}

	// return minX
	public DoubleProperty getMinX() {
		return minX;
	}

	// return minX
	public DoubleProperty getMaxX() {
		return maxX;
	}

	// return minX
	public DoubleProperty getMinY() {
		return minY;
	}

	// return minX
	public DoubleProperty getMaxY() {
		return maxY;
	}

	// return the worth of each pixel in the x-direction
	public DoubleProperty getPixelWorthX() {
		return pixelWorthX;
	}

	// return the worth of each pixel in the y-direction
	public DoubleProperty getPixelWorthY() {
		return pixelWorthY;
	}

	// return the number of draw steps
	public IntegerProperty getSteps() {
		return steps;
	}

	// return the property that notifies when it is time to redraw layers
	public BooleanProperty getChangeViewport() {
		return changeViewport;
	}

}