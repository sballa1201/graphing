package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController implements Initializable {
	
	@FXML
	private BorderPane rootPane;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Pane plotPane = null;
		try {
			plotPane = FXMLLoader.load(getClass().getResource("PlotPane.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		rootPane.setCenter(plotPane);
		
		ScrollPane inputPane = null;
		try {
			inputPane = FXMLLoader.load(getClass().getResource("InputPane.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//rootPane.setLeft(inputPane);
	}
	
	
}
