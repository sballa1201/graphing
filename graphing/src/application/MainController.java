package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController implements Initializable {
	
	@FXML
	private BorderPane rootPane;
	
	private ShareLayers shareLayerStore;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		this.shareLayerStore = new ShareLayers();
		
		System.out.println(shareLayerStore);
		
		
		FXMLLoader loader = null;
		
		
		
		PlotPane plotPane = new PlotPane();
		
		plotPane.setShareLayerStore(shareLayerStore);
		
		rootPane.setCenter(plotPane);
		
		
		
		
		ScrollPane inputPane = null;
		try {
			loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("InputPane.fxml"));
            inputPane = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputPaneController inputPaneController = (InputPaneController) loader.getController();
		
		inputPaneController.setShareLayerStore(shareLayerStore);
		
		rootPane.setLeft(inputPane);
	}
	
	
}
