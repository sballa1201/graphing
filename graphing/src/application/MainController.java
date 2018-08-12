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
import structures.Function;

public class MainController implements Initializable {
	
	@FXML
	private Pane canvasPane;
	
	@FXML
	private Canvas canvas;
	
	private GraphicsContext gc;
	private ArrayList<Function> functions = new ArrayList<Function>();
	
	private int steps = 1000;
	private double minX = -10;
	private double maxX = 10;
	private double minY = -10;
	private double maxY = 10;
	private double pixelWorthX;
	private double pixelWorthY;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gc = canvas.getGraphicsContext2D();
		canvasPane.setStyle("-fx-background-color: white");
		canvas.heightProperty().bind(canvasPane.heightProperty());
		canvas.widthProperty().bind(canvasPane.widthProperty());
		try {
			//Function f = new Function("x^2");
			Function g = new Function("1/x + 1/(x+2)");
			//this.functions.add(f);
			this.functions.add(g);
			
			
		} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
			e.printStackTrace();
		}
		ChangeListener<Number> redrawListener = (observable, oldValue, newValue) -> {
			try {
				draw();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
				e.printStackTrace();
			}
		};
		canvasPane.heightProperty().addListener(redrawListener);
		canvasPane.widthProperty().addListener(redrawListener);
		
	}
	
	@FXML
	private void draw() throws StackOverflowException, StackUnderflowException, UnequalBracketsException { 
		this.updatePixelWorth();
		gc.clearRect(0, 0,canvas.getWidth(), canvas.getHeight());
		
		for(Function f:this.functions) {
			this.drawFunction(f);
		}
		
		
	}
	
	private void drawFunction(Function f, Color color) throws StackUnderflowException, StackOverflowException {
		gc.setLineWidth(1.5);
		gc.setStroke(color);
		double x1, x2, y1, y2;
		x1 = this.minX;
		y1 = f.evaluate(x1);
		
		double step = (this.maxX - this.minX)/this.steps;

		for(x2=this.minX+step; x2<this.maxX;x2=x2+step) {
			try {
				y2 = f.evaluate(x2);
				gc.strokeLine(this.convertX(x1), this.convertY(y1), this.convertX(x2), this.convertY(y2));
				x1 = x2;
				y1 = y2;
			} catch(ArithmeticException e) {
				x1 = x2 + step;
				x2 = x1;
				y1 = f.evaluate(x1);
			}
			
		}
		
		x2 = this.maxX;
		y2 = f.evaluate(x2);
		gc.strokeLine(this.convertX(x1), this.convertY(y1), this.convertX(x2), this.convertY(y2));
	}
	
	private void drawFunction(Function f) throws StackUnderflowException, StackOverflowException {
		this.drawFunction(f, Color.BLACK);
	}
	
	//TODO
	private void drawAsymptote(double x) {

	}
	
	//TODO
	private void drawAxes() {
		
	}
	
	private void updatePixelWorth() {
		this.pixelWorthX = Math.abs((this.maxX - this.minX)/canvas.getWidth());
		this.pixelWorthY = Math.abs((this.maxY - this.minY)/canvas.getHeight());
		
	}
	
	private double convertX(double x) {
		x = x - this.minX;
		x = x /this.pixelWorthX;
		return x;
	}
	
	private double convertY(double y) {
		y = this.maxY - y;
		y = y/this.pixelWorthY;
		return y;
	}
	
	
}
