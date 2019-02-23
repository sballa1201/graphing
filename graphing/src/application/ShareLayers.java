package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import layer.Layer;

public class ShareLayers {

	// attributes
	private Map<Integer, Layer> layers;
	private BooleanProperty changeLayers;

	// methods
	// constructor
	public ShareLayers() {
		// initialize attributes
		layers = new HashMap<Integer, Layer>();
		changeLayers = new SimpleBooleanProperty(true);
	}

	// add a layer
	public void putLayer(int ID, Layer layer) {
		this.layers.put(ID, layer);
		this.changeLayers.set(!this.changeLayers.get());
	}

	// remove a layer
	public void removeLayer(int ID) {
		this.layers.remove(ID);
		this.changeLayers.set(!this.changeLayers.get());
	}

	// return a list of layers
	public ArrayList<Layer> getLayers() {
		return new ArrayList<Layer>(this.layers.values());
	}

	// return the property which notifies the plotpane
	public BooleanProperty getChangeLayers() {
		return changeLayers;
	}

}