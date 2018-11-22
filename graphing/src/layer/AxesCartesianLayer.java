package layer;

public class AxesCartesianLayer extends CartesianLayer {

	public AxesCartesianLayer() {
		super();
	}

	@Override
	public void draw() {
		this.clearCanvas();
		gc.setLineWidth(1.5);
		gc.setStroke(color);

		gc.strokeLine(this.convertX(0), this.convertY(this.minY.doubleValue()), this.convertX(0),
				this.convertY(this.maxY.doubleValue()));

		gc.strokeLine(this.convertX(this.minX.doubleValue()), this.convertY(0), this.convertX(this.maxX.doubleValue()),
				this.convertY(0));
	}

}
