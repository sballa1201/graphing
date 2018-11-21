package layer;

import java.text.DecimalFormat;

import application.PlotPane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class InputCartesianLayer extends Layer {
	
	private BooleanProperty changeViewport = new SimpleBooleanProperty();
	private double pressedX;
	private double pressedY;
	private double previousX;
	private double previousY;
	
	public InputCartesianLayer() {
		super();
		
		//this.canvas.setOnMousePressed(event -> mousePressed(event));
		//this.canvas.setOnMouseDragged(event -> pan(event));
		//this.canvas.setOnMouseMoved(event -> pan(event));
		
		
		this.canvas.setOnMousePressed(event -> dragEntered(event));
		this.canvas.setOnMouseDragged(event -> pan(event));
		
		
		this.canvas.setOnScroll(event -> zoom(event));
		
		this.canvas.setOnMouseMoved(event -> drawCoords(event));
	}
	
	private void drawCoords(MouseEvent event) {
		clearCanvas();
		
		double x = (event.getX()*this.pixelWorthX.doubleValue()) + this.minX.doubleValue();
		double y = this.maxY.doubleValue() - (event.getY()*this.pixelWorthY.doubleValue());
		
		//double y = ((event.getY()*this.pixelWorthY.doubleValue()) + this.minY.doubleValue());
		
		//System.out.println(x+","+y);
		
		//String sX = Double.toString(x);
		String sY = new DecimalFormat("#.##").format(y);
		
		String sX = new DecimalFormat("#.##").format(x);
		
		String out = "(" + sX + "," + sY + ")";
		
		//sX = dX.format(X);
		
		gc.setLineWidth(1);
		
		gc.strokeText(out, 0, 10);
		
	}

	private void dragEntered(MouseEvent event) {
		
		if(event.isPrimaryButtonDown()) {
			previousX = event.getX();
			previousY = event.getY();
			
			//System.out.println("entered drag");
			
			
			
			event.setDragDetect(true);
			
			this.canvas.startFullDrag();
			
			event.consume();
			
			//System.out.println("mouse pressed at - (" + pressedX + "," + pressedY + ")");    
		}
	}
	
	private void pan(MouseEvent event) {
		
		if(event.isPrimaryButtonDown()) {
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
			this.changeViewport.set(!this.changeViewport.get());
			
			
			event.consume();
		}
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
        
        this.changeViewport.set(!this.changeViewport.get());
        
        event.consume();
        
        
//        this.minX.set(Math.pow(zoomFactor,this.minX.doubleValue()));
//        this.maxX.set(Math.pow(zoomFactor,this.maxX.doubleValue()));
//        this.minY.set(Math.pow(zoomFactor,this.minY.doubleValue()));
//        this.maxY.set(Math.pow(zoomFactor,this.maxY.doubleValue()));
//     
        
	}
	
	


	@Override
	public void draw() {}
	
	@Override
	public void bindProperties(PlotPane plotPane) {

		
		this.steps = new SimpleIntegerProperty(plotPane.getSteps().intValue());
		this.minX = new SimpleDoubleProperty(plotPane.getMinX().doubleValue());
		this.maxX = new SimpleDoubleProperty(plotPane.getMaxX().doubleValue());
		this.minY = new SimpleDoubleProperty(plotPane.getMinY().doubleValue());
		this.maxY = new SimpleDoubleProperty(plotPane.getMaxY().doubleValue());
		this.pixelWorthX = new SimpleDoubleProperty(plotPane.getPixelWorthX().doubleValue());
		this.pixelWorthY = new SimpleDoubleProperty(plotPane.getPixelWorthY().doubleValue());
		
		this.pixelWorthX.bind(plotPane.getPixelWorthX());
		this.pixelWorthY.bind(plotPane.getPixelWorthY());
		
		
		canvas.heightProperty().bind(plotPane.heightProperty());
		canvas.widthProperty().bind(plotPane.widthProperty());
		
		changeViewport = new SimpleBooleanProperty(plotPane.getChangeViewport().get());
		
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

	public BooleanProperty getChangeViewport() {
		return changeViewport;
	}
	


}
