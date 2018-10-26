package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
	
	private ExecutorService threadPool =
		    new ThreadPoolExecutor(1 , 10 , 60 ,  TimeUnit.SECONDS,
		    		new LinkedBlockingQueue<Runnable>(50));
	
	private InputLayer inputLayer;
	
	private int steps = 1000;
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
		ExplicitFunctionLayer g = new ExplicitFunctionLayer("x");
		ExplicitFunctionLayer h = new ExplicitFunctionLayer("x^2");
		ExplicitFunctionLayer i = new ExplicitFunctionLayer("x^3");
		ExplicitFunctionLayer j = new ExplicitFunctionLayer("x^4");
		ExplicitFunctionLayer k = new ExplicitFunctionLayer("x^5");
		this.addLayer(g);
		this.addLayer(h);
		this.addLayer(i);
		this.addLayer(j);
		this.addLayer(k);
		ChangeListener<Number> redrawListener = (observable, oldValue, newValue) -> {
			try {
				draw();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		plotPane.heightProperty().addListener(redrawListener);
		plotPane.widthProperty().addListener(redrawListener);

	}
	
	private void draw() throws StackOverflowException, StackUnderflowException, UnequalBracketsException, InterruptedException { 
		this.updatePixelWorth();
		this.updateAttrib();
		
		/*
		Thread[] threads = new Thread[layers.size()];
		
		for(int i=0; i< layers.size(); i++) {
			
			Layer l = layers.get(i);
			
			Runnable r = () -> {
				try {
					l.drawFunction();
				} catch (StackUnderflowException | StackOverflowException e) {
					e.printStackTrace();
				}
			};
			
			Thread t = new Thread(r);
			
			threads[i] = t;
		}
		
		for(Thread t : threads) {
			t.start();
		}
		
		for(Thread t : threads) {
			t.join();
		}
		*/
		
		
		
		CountDownLatch latch = new CountDownLatch(layers.size());
		
		for(Layer l: layers) {
			threadPool.execute(() -> {
				l.drawFunction();
				latch.countDown();
			});
		}
		
		
		latch.await();
		
		//threadPool.shutdown();
		
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
