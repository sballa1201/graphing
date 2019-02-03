package application;

import java.util.ArrayList;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Pane;
import layer.AxesCartesianLayer;
import layer.ExplicitXFunctionCartesianLayer;
import layer.InputLayer;
import layer.Layer;

public class PlotPane extends Pane {

	// attributes
	// layers
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private InputLayer inputLayer;
	private AxesCartesianLayer axesLayer;
	private ShareLayers shareLayerStore;
	// properties
	private BooleanProperty changeViewport = new SimpleBooleanProperty(true);
	private BooleanProperty changeLayers = new SimpleBooleanProperty(true);

	// methods
	// constructor
	public PlotPane() {
		// initialize the input layer and bind its properties
		this.inputLayer = new InputLayer();
		this.inputLayer.bindProperties(this);
		// bind the plotpane's properties to it
		this.changeViewport.bind(this.inputLayer.getChangeViewport());
		// make the background of the plotpane white
		this.setStyle("-fx-background-color: rgb(255,255,255)");
		// this is portion of code which can be bound to a property
		// and will run when that property changes
		// this listener will redraw the layers
		ChangeListener<Object> redrawListener = (observable, oldValue, newValue) -> {
			try {
				drawAll();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException
					| InterruptedException e) {
				// error when drawing functions
				e.printStackTrace();
			}
		};
		// add the redraw listener to the size properties
		// so when the plotpane changes size the layers are redrawn
		this.heightProperty().addListener(redrawListener);
		this.widthProperty().addListener(redrawListener);
		// add the redraw listener to the changeViewport property
		// so when the input layer notifies to draw, the layers are redrawn
		this.changeViewport.addListener(redrawListener);
		// initialize the axes layer and bind its properties
		this.axesLayer = new AxesCartesianLayer();
		axesLayer.bindProperties(this);
//
//		ExplicitXFunctionCartesianLayer g = new ExplicitXFunctionCartesianLayer("e^x");
//		ExplicitXFunctionCartesianLayer h = new ExplicitXFunctionCartesianLayer("1/x");
//		ExplicitXFunctionCartesianLayer i = new ExplicitXFunctionCartesianLayer("x^3");
//		ExplicitXFunctionCartesianLayer j = new ExplicitXFunctionCartesianLayer("x^4");
//		ExplicitXFunctionCartesianLayer k = new ExplicitXFunctionCartesianLayer("x^5");
//		this.addLayer(g);
//		this.addLayer(h);
//		this.addLayer(i);
//		this.addLayer(j);
//		this.addLayer(k);
	}

	private void drawAll()
			throws StackOverflowException, StackUnderflowException, UnequalBracketsException, InterruptedException {
		// loop through every layer and draw it
		for (Layer l : layers) {
			l.draw();
		}
		// draw the axes
		axesLayer.draw();
		// remove all the canvases in the plotpane
		this.getChildren().clear();
		// readd all the canvases in the plotpane
		for (Layer l : layers) {
			this.getChildren().add(l.getCanvas());
		}
		// add the input and axes canvases again
		// with the input layer on top so that it can receive input
		this.getChildren().add(axesLayer.getCanvas());
		this.getChildren().add(inputLayer.getCanvas());
	}

	// add a layer
	private void addLayer(Layer l) {
		// bind the properties to this plotpane
		l.bindProperties(this);
		// add the layer to the list
		this.layers.add(l);
	}

	// return the inputlayer to access its properties
	public InputLayer getInputLayer() {
		return inputLayer;
	}

	public void setShareLayerStore(ShareLayers shareLayerStore) {
		this.shareLayerStore = shareLayerStore;
		this.changeLayers.set(this.shareLayerStore.getChangeLayers().get());
		this.changeLayers.bind(this.shareLayerStore.getChangeLayers());

		ChangeListener<Object> redrawListener = (observable, oldValue, newValue) -> {
			ArrayList<Layer> tempLayers = shareLayerStore.getLayers();

			layers = new ArrayList<Layer>();

			for (Layer l : tempLayers) {
				this.addLayer(l);
			}

			try {
				drawAll();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException
					| InterruptedException e) {
				// error in drawing a function
				e.printStackTrace();
			}

		};

		this.changeLayers.addListener(redrawListener);
	}

}
