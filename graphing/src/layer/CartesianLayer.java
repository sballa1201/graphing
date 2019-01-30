package layer;

import application.PlotPane;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class CartesianLayer extends Layer {

	@Override
	// must be overriden since the parent class made it abstract
	// but keep it abstract so child class must override
	public abstract void draw();

	@Override
	// bind the needed properties from the input layer to itself
	public void bindProperties(PlotPane plotPane) {
		// initialize the properties with the same values as their partners
		this.steps = new SimpleIntegerProperty(plotPane.getInputLayer().getSteps().intValue());
		this.minX = new SimpleDoubleProperty(plotPane.getInputLayer().getMinX().doubleValue());
		this.maxX = new SimpleDoubleProperty(plotPane.getInputLayer().getMaxX().doubleValue());
		this.minY = new SimpleDoubleProperty(plotPane.getInputLayer().getMinY().doubleValue());
		this.maxY = new SimpleDoubleProperty(plotPane.getInputLayer().getMaxY().doubleValue());
		this.pixelWorthX = new SimpleDoubleProperty(plotPane.getInputLayer().getPixelWorthX().doubleValue());
		this.pixelWorthY = new SimpleDoubleProperty(plotPane.getInputLayer().getPixelWorthY().doubleValue());
		// bind the two properties together one-directionally
		// so these attributes change when plot pane changes, not vice versa
		this.steps.bind(plotPane.getInputLayer().getSteps());
		this.minX.bind(plotPane.getInputLayer().getMinX());
		this.maxX.bind(plotPane.getInputLayer().getMaxX());
		this.minY.bind(plotPane.getInputLayer().getMinY());
		this.maxY.bind(plotPane.getInputLayer().getMaxY());
		this.pixelWorthX.bind(plotPane.getInputLayer().getPixelWorthX());
		this.pixelWorthY.bind(plotPane.getInputLayer().getPixelWorthY());
		// make the canvas resize as its parent resizes by binding the associated properties
		canvas.heightProperty().bind(plotPane.heightProperty());
		canvas.widthProperty().bind(plotPane.widthProperty());
	}

	// convert x-Cartesian coordinate to x-Canvas coordinate
	protected double convertX(double x) {
		x = x - this.minX.doubleValue();
		x = x / this.pixelWorthX.doubleValue();
		return x;
	}

	// convert y-Cartesian coordinate to y-Canvas coordinate
	protected double convertY(double y) {
		y = this.maxY.doubleValue() - y;
		y = y / this.pixelWorthY.doubleValue();
		return y;
	}

}