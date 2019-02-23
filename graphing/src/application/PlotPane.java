package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
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
		// create a right click menu
		ContextMenu contextMenu = new ContextMenu();
		MenuItem save = new MenuItem("Save as Picture");
		contextMenu.getItems().addAll(save);
		// set an action to call the savePlot() method when clicking the save button
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				savePlot();
			}
		});
		// when right clicking open the menu
		this.setOnContextMenuRequested(
				event -> contextMenu.show(this.getScene().getWindow(), event.getScreenX(), event.getScreenY()));
		this.addLayer(new ExplicitXFunctionCartesianLayer("10((1/256)x^3-(1/8)x)"));
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

	// save picture of plot
	private void savePlot() {
		// create the file navigator object
		FileChooser fileChooser = new FileChooser();
		// set extension filter
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
		// set extension filter (make the image of format png)
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {	//if the user chose to save then snapshot the pane
			try {
				// remove input layer canvas, the top node, to not show the coords
				this.getChildren().remove(this.getChildren().size() - 1);
				// create the snapshot of the pane
				WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);
				RenderedImage renderedImage = SwingFXUtils.fromFXImage(snapshot, null);
				// write the snapshot to the chosen file
				ImageIO.write(renderedImage, "png", file);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				// readd the input layer
				this.getChildren().add(inputLayer.getCanvas());
			}
		}
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
