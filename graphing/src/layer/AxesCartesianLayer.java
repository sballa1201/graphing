package layer;

public class AxesCartesianLayer extends CartesianLayer {

	// constructor
	public AxesCartesianLayer() {
		// call the super to setup the canvas
		super();
	}

	@Override
	// draw the pair of axes
	public void draw() {
		// clear the canvas before drawing
		this.clearCanvas();
		// make the line width thinner then usual to remove emphasis from axes
		gc.setLineWidth(1.5);
		gc.setStroke(color);
		// draw the y-axis
		gc.strokeLine(this.convertX(0), this.convertY(this.minY.doubleValue()), this.convertX(0),
				this.convertY(this.maxY.doubleValue()));
		// draw the x-axis
		gc.strokeLine(this.convertX(this.minX.doubleValue()), this.convertY(0),
				this.convertX(this.maxX.doubleValue()), this.convertY(0));
	}

}