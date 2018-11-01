package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class InputLayer extends Layer {
	
	private IntegerProperty changeViewport = new SimpleIntegerProperty();
	private double pressedX;
	private double pressedY;
	private double previousX;
	private double previousY;
	
	public InputLayer() {
		this.canvas = new Canvas();
		
		
		//this.canvas.setOnMousePressed(event -> mousePressed(event));
		//this.canvas.setOnMouseDragged(event -> pan(event));
		//this.canvas.setOnMouseMoved(event -> pan(event));
		
		
		this.canvas.setOnMousePressed(event -> dragEntered(event));
		this.canvas.setOnMouseDragged(event -> pan(event));
		
		
		this.canvas.setOnScroll(event -> zoom(event));		
	}
	
	private void dragEntered(MouseEvent event) {
		
		previousX = event.getX();
		previousY = event.getY();
		
		//System.out.println("entered drag");
		
		
		
		event.setDragDetect(true);
		
		this.canvas.startFullDrag();
		
		event.consume();
		
		//System.out.println("mouse pressed at - (" + pressedX + "," + pressedY + ")");    
        
	}
	
	private void pan(MouseEvent event) {
		
		double panFactor = 1;
		
		pressedX = event.getX();
		pressedY = event.getY();
		
		double xTrans = (previousX - pressedX);
		double yTrans = (previousY - pressedY);
		
		previousX = pressedX;
		previousY = pressedY;
		
		//System.out.println("dragged by vector - (" + xTrans + "," + yTrans + ")");
		//System.out.println(this.minX.doubleValue() + " - " + this.minY.doubleValue());
		
		this.maxX.set((this.pixelWorthX.doubleValue() * xTrans/panFactor) + this.maxX.doubleValue());
		this.minX.set((this.pixelWorthX.doubleValue() * xTrans/panFactor) + this.minX.doubleValue());
		this.maxY.set(-(this.pixelWorthY.doubleValue() * yTrans/panFactor) + this.maxY.doubleValue());
		this.minY.set(-(this.pixelWorthY.doubleValue() * yTrans/panFactor) + this.minY.doubleValue());
		this.changeViewport.set(-this.changeViewport.intValue());
		
		
		event.consume();
		
	}
	
	private void zoom(ScrollEvent event) {
		
		double zoomFactor = 1.05;
        double deltaY = event.getDeltaY();
                
        if (deltaY > 0){
            zoomFactor = 1/zoomFactor;
        }
        
        double minXVal = this.minX.doubleValue() * zoomFactor;
        double maxXVal = this.maxX.doubleValue() * zoomFactor;
        double minYVal = this.minY.doubleValue() * zoomFactor;
        double maxYVal = this.maxY.doubleValue() * zoomFactor;
        
        this.minX.set(minXVal);
        this.maxX.set(maxXVal);
        this.minY.set(minYVal);
        this.maxY.set(maxYVal);
        
        this.changeViewport.set(-this.changeViewport.intValue());
        
        event.consume();
        
        
//        this.minX.set(Math.pow(zoomFactor,this.minX.doubleValue()));
//        this.maxX.set(Math.pow(zoomFactor,this.maxX.doubleValue()));
//        this.minY.set(Math.pow(zoomFactor,this.minY.doubleValue()));
//        this.maxY.set(Math.pow(zoomFactor,this.maxY.doubleValue()));
//     
        
	}
	
	


	@Override
	public void drawFunction() {}
	
	@Override
	public void bindProperties(PlotPaneController controller) {

		
		this.steps = new SimpleIntegerProperty(controller.getSteps().intValue());
		this.minX = new SimpleDoubleProperty(controller.getMinX().doubleValue());
		this.maxX = new SimpleDoubleProperty(controller.getMaxX().doubleValue());
		this.minY = new SimpleDoubleProperty(controller.getMinY().doubleValue());
		this.maxY = new SimpleDoubleProperty(controller.getMaxY().doubleValue());
		this.pixelWorthX = new SimpleDoubleProperty(controller.getPixelWorthX().doubleValue());
		this.pixelWorthY = new SimpleDoubleProperty(controller.getPixelWorthY().doubleValue());
		
		this.pixelWorthX.bind(controller.getPixelWorthX());
		this.pixelWorthY.bind(controller.getPixelWorthY());
		
		
		canvas.heightProperty().bind(controller.getPlotPane().heightProperty());
		canvas.widthProperty().bind(controller.getPlotPane().widthProperty());
		
		changeViewport = new SimpleIntegerProperty(controller.getChangeViewport().intValue());
		
	}
	
	
	public DoubleProperty getMinX() {
		return minX;
	}

	public DoubleProperty getMaxX() {
		return maxX;
	}

	public DoubleProperty getMinY() {
		return minY;
	}

	public DoubleProperty getMaxY() {
		return maxY;
	}

	public IntegerProperty getChangeViewport() {
		return changeViewport;
	}
	


}