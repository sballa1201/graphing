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
	private double pressedX;
	private double pressedY;
	private double previousX;
	private double previousY;
	
	// methods
	// constructor
	public InputLayer() {
		// call the super constructor
		super();
		//update pixel worth when the canvas changes size
		this.canvas.heightProperty().addListener(event -> updatePixelWorth());
		this.canvas.widthProperty().addListener(event -> updatePixelWorth());

//		this.canvas.setOnMousePressed(event -> dragEntered(event));
//		this.canvas.setOnMouseDragged(event -> pan(event));
//		this.canvas.setOnScroll(event -> zoom(event));
//		this.canvas.setOnMouseMoved(event -> drawCoords(event));
	}

	private void drawCoords(MouseEvent event) {
		clearCanvas();
		this.updatePixelWorth();
		double x = (event.getX() * this.pixelWorthX.doubleValue()) + this.minX.doubleValue();
		double y = this.maxY.doubleValue() - (event.getY() * this.pixelWorthY.doubleValue());

		// double y = ((event.getY()*this.pixelWorthY.doubleValue()) +
		// this.minY.doubleValue());

		// System.out.println(x+","+y);

		// String sX = Double.toString(x);
		String sY = new DecimalFormat("#.##").format(y);

		String sX = new DecimalFormat("#.##").format(x);

		String out = "(" + sX + "," + sY + ")";

		// sX = dX.format(X);

		gc.setLineWidth(1);

		gc.strokeText(out, 0, 10);

	}

	private void dragEntered(MouseEvent event) {

		if (event.isPrimaryButtonDown()) {
			previousX = event.getX();
			previousY = event.getY();

			// System.out.println("entered drag");
			event.setDragDetect(true);
			this.canvas.startFullDrag();

			event.consume();

			// System.out.println("mouse pressed at - (" + pressedX + "," + pressedY + ")");
		}
	}

	private void pan(MouseEvent event) {

		if (event.isPrimaryButtonDown()) {
			double panFactor = 1;

			pressedX = event.getX();
			pressedY = event.getY();

			double xTrans = (previousX - pressedX);
			double yTrans = (previousY - pressedY);

			previousX = pressedX;
			previousY = pressedY;

			// System.out.println("dragged by vector - (" + xTrans + "," + yTrans + ")");
			// System.out.println(this.minX.doubleValue() + " - " +
			// this.minY.doubleValue());

			this.maxX.set((this.pixelWorthX.doubleValue() * xTrans / panFactor) + this.maxX.doubleValue());
			this.minX.set((this.pixelWorthX.doubleValue() * xTrans / panFactor) + this.minX.doubleValue());
			this.maxY.set(-(this.pixelWorthY.doubleValue() * yTrans / panFactor) + this.maxY.doubleValue());
			this.minY.set(-(this.pixelWorthY.doubleValue() * yTrans / panFactor) + this.minY.doubleValue());
			this.changeViewport.set(!this.changeViewport.get());

			event.consume();
		}
	}

	private void zoom(ScrollEvent event) {

		double zoomFactor = 1.05;
		double deltaY = event.getDeltaY();

		if (deltaY > 0) {
			zoomFactor = 1 / zoomFactor;
		}

		double minXVal = this.minX.doubleValue() * zoomFactor;
		double maxXVal = this.maxX.doubleValue() * zoomFactor;
		double minYVal = this.minY.doubleValue() * zoomFactor;
		double maxYVal = this.maxY.doubleValue() * zoomFactor;

		this.minX.set(minXVal);
		this.maxX.set(maxXVal);
		this.minY.set(minYVal);
		this.maxY.set(maxYVal);
		this.updatePixelWorth();
		this.changeViewport.set(!this.changeViewport.get());

		event.consume();
	}

	@Override
	// this canvas will not draw anything so this method is empty
	// it must still be implemented since the method was abstract in the parent class
	public void draw() {}
	
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
		// make the canvas resize as its parent resizes by binding the associated properties
		this.canvas.heightProperty().bind(plotPane.heightProperty());
		this.canvas.widthProperty().bind(plotPane.widthProperty());
	}
	
	//return minX
	public DoubleProperty getMinX() {
		return minX;
	}
	
	//return minX
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
