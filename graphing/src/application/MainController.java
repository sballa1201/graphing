package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

	// attributes
	@FXML
	private BorderPane rootPane;

	private ShareLayers shareLayerStore;

	// methods
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// initialize the shared layer store
		this.shareLayerStore = new ShareLayers();
		// initialize the plotpane
		PlotPane plotPane = new PlotPane();
		// initialize the input pane
		InputPane inputPane = null;
		try {
			inputPane = new InputPane();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// set the shared layer stores to both the panes
		plotPane.setShareLayerStore(shareLayerStore);
		inputPane.setShareLayerStore(shareLayerStore);
		// add the panes to the root pane
		rootPane.setCenter(plotPane);
		rootPane.setLeft(inputPane);
	}

}