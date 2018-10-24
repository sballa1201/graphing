package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import structures.Expression;

public class PlotPaneController implements Initializable {
	
	
	@FXML
	private Pane plotPane;
	
	private ArrayList<Canvas> canvi = new ArrayList<Canvas>();
	
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	
	private GraphicsContext gc;
	
	
	
	
	private int steps = 1000000;
	private double minX = -10;
	private double maxX = 10;
	private double minY = -10;
	private double maxY = 10;
	private double pixelWorthX;
	private double pixelWorthY;
	
	public PlotPaneController() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		plotPane.setStyle("-fx-background-color: white");
		//Function f = new Function("x^2");
		String sE = Double.toString(Math.E);
		Layer g = new Layer(sE+"^x");
		Layer h = new Layer("(1/50)((1/(2.718281828^x -1)) - (x^7 +5x^6 + 9x^5 +(7/x)))");
		Layer i = new Layer("1/x^2");
		Layer j = new Layer("(-x+5)^(0.5)");
		Layer x = new Layer("1/("+sE+"^x-1)");
		//this.functions.add(f);
		//this.addLayer(g);
		//this.addLayer(h);
		//this.addLayer(i);
		//this.addLayer(j);
		this.addLayer(h);
		ChangeListener<Number> redrawListener = (observable, oldValue, newValue) -> {
			try {
				draw();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
				e.printStackTrace();
			}
		};
		plotPane.heightProperty().addListener(redrawListener);
		plotPane.widthProperty().addListener(redrawListener);

	}
	
	private void draw() throws StackOverflowException, StackUnderflowException, UnequalBracketsException { 
		this.updatePixelWorth();
		this.updateAttrib();
		
		for(Layer l : layers) {
			l.drawFunction();
		}
		
		plotPane.getChildren().clear();
		
		for(Layer l : layers) {
			plotPane.getChildren().add(l.getCanvas());
		}
		
	}
	
	private void addLayer(Layer l) {
		l.bindProperties(plotPane);
		this.layers.add(l);
	}
	
	private void updateAttrib() {
		for(Layer l : layers) {
			l.setMaxX(maxX);
			l.setMaxY(maxY);
			l.setMinX(minX);
			l.setMinY(minY);
			l.setPixelWorthX(pixelWorthX);
			l.setPixelWorthY(pixelWorthY);
			l.setSteps(steps);
		}
	}
	
	private void updatePixelWorth() {
		this.pixelWorthX = Math.abs((this.maxX - this.minX)/plotPane.getWidth());
		this.pixelWorthY = Math.abs((this.maxY - this.minY)/plotPane.getHeight());
	}
	


}
