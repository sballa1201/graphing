package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class PlotPaneController implements Initializable {
	
	
	@FXML
	private Pane plotPane;
	
	private ArrayList<Canvas> canvi = new ArrayList<Canvas>();
	
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	
	private ExecutorService threadPool =
		    new ThreadPoolExecutor(1 , 10 , 60 ,  TimeUnit.SECONDS,
		    		new LinkedBlockingQueue<Runnable>(50));
	
	private InputLayer inputLayer;
	
	private IntegerProperty steps = new SimpleIntegerProperty(1000);
	
	private DoubleProperty changeViewport = new SimpleDoubleProperty(0);
	
	private DoubleProperty minX = new SimpleDoubleProperty(-10);
	private DoubleProperty maxX = new SimpleDoubleProperty(10);
	private DoubleProperty minY = new SimpleDoubleProperty(-10);
	private DoubleProperty maxY = new SimpleDoubleProperty(10);
	
	
	private DoubleProperty pixelWorthX = new SimpleDoubleProperty();
	private DoubleProperty pixelWorthY = new SimpleDoubleProperty();
	
	
	public PlotPaneController() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		plotPane.setStyle("-fx-background-color: black");
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
		
		this.changeViewport.addListener(redrawListener);		//this.minX.addListener(redrawListener); USED TO DO THIS BUT THEN MINX LAGGED BEHIND
		
		this.inputLayer = new InputLayer();

		this.addLayer(inputLayer);
	}
	
	private void draw() throws StackOverflowException, StackUnderflowException, UnequalBracketsException, InterruptedException { 
		this.updatePixelWorth();

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
		l.bindProperties(this);
		this.layers.add(l);
	}
	/*
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
	}*/
	
	private void updatePixelWorth() {
		this.pixelWorthX.set(Math.abs((this.maxX.doubleValue() - this.minX.doubleValue())/plotPane.getWidth()));
		this.pixelWorthY.set(Math.abs((this.maxY.doubleValue() - this.minY.doubleValue())/plotPane.getHeight()));
	}

	public Pane getPlotPane() {
		return plotPane;
	}

	public IntegerProperty getSteps() {
		return steps;
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

	public DoubleProperty getPixelWorthX() {
		return pixelWorthX;
	}

	public DoubleProperty getPixelWorthY() {
		return pixelWorthY;
	}

	public DoubleProperty getChangeViewport() {
		return changeViewport;
	}
	
	


}
