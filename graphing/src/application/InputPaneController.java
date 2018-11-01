package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class InputPaneController implements Initializable {
	
	@FXML
	private ScrollPane inputPane;
	
	@FXML
	private VBox expressionBox; 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.inputPane.setFitToHeight(true);
		this.inputPane.setFitToWidth(true);
		
		
		Node root = (Region) this.inputPane.getParent();
		
		
		
		
		this.inputPane.heightProperty().addListener(event -> {
			//inputPane.setPrefHeight();
		});
		
		
		for(int i=0; i<30; i++) {
			Pane pane = null;
			try {
				pane = FXMLLoader.load(getClass().getResource("ExpressionPane.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			pane.setUserData(i);
			expressionBox.getChildren().add(pane);
		}
	}

}
