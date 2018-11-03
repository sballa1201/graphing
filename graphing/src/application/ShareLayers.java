package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ShareLayers {
	
	private Map<Integer, Layer> layers;
	
	private BooleanProperty changeLayers;
	
	
	public ShareLayers() {
		layers = new HashMap<Integer, Layer>();
		changeLayers = new SimpleBooleanProperty(true);
	}
	
	public void putLayer(int ID, Layer layer) {
		this.layers.put(ID, layer);		
		this.changeLayers.set(!this.changeLayers.get());
		System.out.println("current layers - "+layers.values().toString());
		//System.out.println(layers.values().toString());
	}

	public void removeLayer(int ID) {
		this.layers.remove(ID);
		this.changeLayers.set(!this.changeLayers.get());
		//System.out.println(layers.values().toString());
	}

	public ArrayList<Layer> getLayers() {
		return new ArrayList<Layer>(this.layers.values());
	}
	
	public BooleanProperty getChangeLayers() {
		return changeLayers;
	}
	
}
