package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;

public class InputLayer extends Layer {
	
	private DoubleProperty changeViewport = new SimpleDoubleProperty(0);
	
	public InputLayer() {
		this.canvas = new Canvas();
		this.gc = canvas.getGraphicsContext2D();
		
		this.canvas.setOnScroll(event -> zoom(event));
	}
	
	private void zoom(ScrollEvent event) {
		
		double zoomFactor = 1.5;
        double deltaY = event.getDeltaY();
                
        if (deltaY > 0){
            zoomFactor = 1.0 / zoomFactor;
        }
        
        double minXVal = this.minX.doubleValue();
        double maxXVal = this.maxX.doubleValue();
        double minYVal = this.minY.doubleValue();
        double maxYVal = this.maxY.doubleValue();
        
        this.minX.set(zoomFactor*minXVal);
        this.maxX.set(zoomFactor*maxXVal);
        this.minY.set(zoomFactor*minYVal);
        this.maxY.set(zoomFactor*maxYVal);
        
        this.changeViewport.set(minX.intValue());
        
//        
//        this.minX.set(Math.pow(zoomFactor,this.minX.doubleValue()));
//        this.maxX.set(Math.pow(zoomFactor,this.maxX.doubleValue()));
//        this.minY.set(Math.pow(zoomFactor,this.minY.doubleValue()));
//        this.maxY.set(Math.pow(zoomFactor,this.maxY.doubleValue()));
//     
        
	}
	
	@Override
	public void drawFunction() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void bindProperties(PlotPaneController controller) {
		canvas.heightProperty().bind(controller.getPlotPane().heightProperty());
		canvas.widthProperty().bind(controller.getPlotPane().widthProperty());
		
		this.steps.bind(controller.getSteps());
		
		this.pixelWorthX.bind(controller.getPixelWorthX());
		this.pixelWorthY.bind(controller.getPixelWorthY());
		
		this.minX.bindBidirectional(controller.getMinX());
		this.maxX.bindBidirectional(controller.getMaxX());
		this.minY.bindBidirectional(controller.getMinY());
		this.maxY.bindBidirectional(controller.getMaxY());
		
		this.changeViewport.bindBidirectional(controller.getChangeViewport());
		

	}
	
	


}
