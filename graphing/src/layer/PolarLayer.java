package layer;

import application.PlotPane;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class PolarLayer extends Layer {

	@Override
	public abstract void draw();

	@Override
	public void bindProperties(PlotPane plotPane) {
		this.steps = new SimpleIntegerProperty(plotPane.getSteps().intValue());
		this.minX = new SimpleDoubleProperty(plotPane.getInputLayer().getMinX().doubleValue());
		this.maxX = new SimpleDoubleProperty(plotPane.getInputLayer().getMaxX().doubleValue());
		this.minY = new SimpleDoubleProperty(plotPane.getInputLayer().getMinY().doubleValue());
		this.maxY = new SimpleDoubleProperty(plotPane.getInputLayer().getMaxY().doubleValue());
		this.pixelWorthX = new SimpleDoubleProperty(plotPane.getPixelWorthX().doubleValue());
		this.pixelWorthY = new SimpleDoubleProperty(plotPane.getPixelWorthY().doubleValue());

		canvas.heightProperty().bind(plotPane.heightProperty());
		canvas.widthProperty().bind(plotPane.widthProperty());

		this.steps.bind(plotPane.getSteps());
		this.minX.bind(plotPane.getInputLayer().getMinX());
		this.maxX.bind(plotPane.getInputLayer().getMaxX());
		this.minY.bind(plotPane.getInputLayer().getMinY());
		this.maxY.bind(plotPane.getInputLayer().getMaxY());
		this.pixelWorthX.bind(plotPane.getPixelWorthX());
		this.pixelWorthY.bind(plotPane.getPixelWorthY());
	}

	protected double convertX(double r, double t) {
		double x = r * Math.cos(t);
		x = x - this.minX.doubleValue();
		x = x / this.pixelWorthX.doubleValue();
		return x;
	}

	protected double convertY(double r, double t) {
		double y = r * Math.sin(t);
		y = this.maxY.doubleValue() - y;
		y = y / this.pixelWorthY.doubleValue();
		return y;
	}

}
