package layer;

import javafx.scene.canvas.Canvas;

public class AxesLayer extends Layer {
	
	public AxesLayer() {
		this.canvas = new Canvas();
		this.gc = canvas.getGraphicsContext2D();
		
	}
	
	@Override
	public void draw() {
		this.clearCanvas();
		gc.setLineWidth(1.5);
		gc.setStroke(color);
		
		gc.strokeLine(this.convertX(0), this.convertY(this.minY.doubleValue()), this.convertX(0), this.convertY(this.maxY.doubleValue()));
		
		gc.strokeLine(this.convertX(this.minX.doubleValue()), this.convertY(0), this.convertX(this.maxX.doubleValue()), this.convertY(0));
	}

}
