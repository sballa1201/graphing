package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	@FXML
	private BorderPane rootPane;
	
	private ShareLayers shareLayerStore;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		this.shareLayerStore = new ShareLayers();
		
		System.out.println(shareLayerStore);
		
		
		
		PlotPane plotPane = new PlotPane();
		
		plotPane.setShareLayerStore(shareLayerStore);
		
		rootPane.setCenter(plotPane);
		
		
		
		
		InputPane inputPane = new InputPane();
		inputPane.setShareLayerStore(shareLayerStore);
		
		rootPane.setLeft(inputPane);
		
		//rootPane.setLeft(inputPane);
	}
	
	
}
