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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import layer.AxesCartesianLayer;
import layer.ExplicitFunctionCartesianLayer;
import layer.InputCartesianLayer;
import layer.Layer;

public class PlotPane extends Pane {
	
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	
	private ExecutorService threadPool =
		    new ThreadPoolExecutor(1 , 10 , 60 ,  TimeUnit.SECONDS,
		    		new LinkedBlockingQueue<Runnable>(50));
	
	private InputCartesianLayer inputLayer;
	private AxesCartesianLayer axes;
	
	private IntegerProperty steps = new SimpleIntegerProperty(1000);
	
	private BooleanProperty changeViewport = new SimpleBooleanProperty(true);
	
	private DoubleProperty minX = new SimpleDoubleProperty(-10);
	private DoubleProperty maxX = new SimpleDoubleProperty(10);
	private DoubleProperty minY = new SimpleDoubleProperty(-10);
	private DoubleProperty maxY = new SimpleDoubleProperty(10);
	
	
	private DoubleProperty pixelWorthX = new SimpleDoubleProperty();
	private DoubleProperty pixelWorthY = new SimpleDoubleProperty();
	
	private ShareLayers shareLayerStore;
	
	private BooleanProperty changeLayers = new SimpleBooleanProperty(true);
	

	public PlotPane() {
		
		this.setupInputLayer();
		
		this.setStyle("-fx-background-color: rgb(255,255,255)");
		
		
		
//		//Function f = new Function("x^2");
//		String sE = Double.toString(Math.E);
//		ExplicitFunctionCartesianLayer g = new ExplicitFunctionCartesianLayer(sE+"^x");
//		ExplicitFunctionCartesianLayer h = new ExplicitFunctionCartesianLayer("x^2");
//		ExplicitFunctionCartesianLayer i = new ExplicitFunctionCartesianLayer("x^3");
//		ExplicitFunctionCartesianLayer j = new ExplicitFunctionCartesianLayer("x^4");
//		ExplicitFunctionCartesianLayer k = new ExplicitFunctionCartesianLayer("x^5");
//		//this.addLayer(g);
//		//this.addLayer(h);
//		//this.addLayer(i);
//		//this.addLayer(j);
//		//this.addLayer(k);

		ChangeListener<Object> redrawListener = (observable, oldValue, newValue) -> {
			try {
				drawAll();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		this.heightProperty().addListener(redrawListener);
		this.widthProperty().addListener(redrawListener);
		
		this.changeViewport.addListener(redrawListener);		//this.minX.addListener(redrawListener); USED TO DO THIS BUT THEN MINX LAGGED BEHIND
		
		
		
		//this.minX.addListener(redrawListener);
		
		this.axes = new AxesCartesianLayer();
		axes.bindProperties(this);
	}
	
	
	private void drawAll() throws StackOverflowException, StackUnderflowException, UnequalBracketsException, InterruptedException { 
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
				l.draw();
				latch.countDown();
			});
		}
		
		
		latch.await();
		
		//threadPool.shutdown();
		
		axes.draw();
		
		this.getChildren().clear();
		
		for(Layer l : layers) {
			this.getChildren().add(l.getCanvas());
		}
		this.getChildren().add(axes.getCanvas());
		this.getChildren().add(inputLayer.getCanvas());
		
	}
	
	private void setupInputLayer() {
		this.inputLayer = new InputCartesianLayer();
		this.inputLayer.bindProperties(this);
		
		this.minX.bind(inputLayer.getMinX());
		this.maxX.bind(inputLayer.getMaxX());
		this.minY.bind(inputLayer.getMinY());
		this.maxY.bind(inputLayer.getMaxY());
		
		this.changeViewport.bind(inputLayer.getChangeViewport());
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
		//this.pixelWorthX.set(Math.abs((this.maxX.doubleValue() - this.minX.doubleValue())/plotPane.getWidth()));
		//this.pixelWorthY.set(Math.abs((this.maxY.doubleValue() - this.minY.doubleValue())/plotPane.getHeight()));
		this.pixelWorthX.set((this.maxX.doubleValue() - this.minX.doubleValue())/this.getWidth());
		this.pixelWorthY.set((this.maxY.doubleValue() - this.minY.doubleValue())/this.getHeight());
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

	public BooleanProperty getChangeViewport() {
		return changeViewport;
	}

	public InputCartesianLayer getInputLayer() {
		return inputLayer;
	}

	public void setShareLayerStore(ShareLayers shareLayerStore) {
		this.shareLayerStore = shareLayerStore;
		this.changeLayers.set(this.shareLayerStore.getChangeLayers().get());
		this.changeLayers.bind(this.shareLayerStore.getChangeLayers());
		
		ChangeListener<Object> redrawListener = (observable, oldValue, newValue) -> {
			ArrayList<Layer> tempLayers = shareLayerStore.getLayers();
			
			System.out.println("update");
			
			layers = new ArrayList<Layer>();
			
			for(Layer l : tempLayers) {
				this.addLayer(l);
			}
			
			System.out.println(layers);
			try {
				drawAll();
			} catch (StackOverflowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (StackUnderflowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnequalBracketsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		
		this.changeLayers.addListener(redrawListener);
	}
	
}
