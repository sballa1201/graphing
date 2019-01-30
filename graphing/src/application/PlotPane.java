package application;

import java.util.ArrayList;

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
import javafx.scene.layout.Pane;
import layer.AxesCartesianLayer;
import layer.InputLayer;
import layer.Layer;

public class PlotPane extends Pane {

	private ArrayList<Layer> layers = new ArrayList<Layer>();

	private InputLayer inputLayer;
	private AxesCartesianLayer axes;


	private BooleanProperty changeViewport = new SimpleBooleanProperty(true);

	private ShareLayers shareLayerStore;

	private BooleanProperty changeLayers = new SimpleBooleanProperty(true);

	public PlotPane() {
		
		this.setHeight(1);
		this.setWidth(1);
		this.inputLayer = new InputLayer(this);
		this.changeViewport.bind(this.inputLayer.getChangeViewport());
		
		this.setStyle("-fx-background-color: rgb(255,255,255)");
//			 Function f = new Function("x^2");
//			 String sE = Double.toString(Math.E);
//			 ExplicitFunctionCartesianLayer g = new
//			 ExplicitFunctionCartesianLayer(sE+"^x");
//			 ExplicitFunctionCartesianLayer h = new ExplicitFunctionCartesianLayer("x^2");
//			 ExplicitFunctionCartesianLayer i = new ExplicitFunctionCartesianLayer("x^3");
//			 ExplicitFunctionCartesianLayer j = new ExplicitFunctionCartesianLayer("x^4");
//			 ExplicitFunctionCartesianLayer k = new ExplicitFunctionCartesianLayer("x^5");
//			 this.addLayer(g);
//			 this.addLayer(h);
//			 this.addLayer(i);
//			 this.addLayer(j);
//			 this.addLayer(k);
		ChangeListener<Object> redrawListener = (observable, oldValue, newValue) -> {
			try {
				drawAll();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException
					| InterruptedException e) {
				e.printStackTrace();
			}
		};
		this.heightProperty().addListener(redrawListener);
		this.widthProperty().addListener(redrawListener);

		this.changeViewport.addListener(redrawListener); // this.minX.addListener(redrawListener); USED TO DO THIS BUT
															// THEN MINX LAGGED BEHIND

		// this.minX.addListener(redrawListener);

		this.axes = new AxesCartesianLayer();
		axes.bindProperties(this);

	}

	private void drawAll()
			throws StackOverflowException, StackUnderflowException, UnequalBracketsException, InterruptedException {

		for (Layer l : layers) {
			l.draw();
		}

		axes.draw();

		this.getChildren().clear();

		for (Layer l : layers) {
			this.getChildren().add(l.getCanvas());
		}
		this.getChildren().add(axes.getCanvas());
		this.getChildren().add(inputLayer.getCanvas());

	}

	private void addLayer(Layer l) {
		l.bindProperties(this);
		this.layers.add(l);
	}

	public BooleanProperty getChangeViewport() {
		return changeViewport;
	}

	public InputLayer getInputLayer() {
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

			for (Layer l : tempLayers) {
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
