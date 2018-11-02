package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ExpressionBox extends HBox {

	private int ID;
	
	private Label label;
	
	private TextField input;
	
	private Button removeButton;
	
	private InputPaneController rootController;
	
	public ExpressionBox(int ID, InputPaneController rootController) {
		label = new Label("Label");
		
		input = new TextField();
		
		removeButton = new Button("X");
		
		removeButton.setOnAction(event -> remove());
		
		this.getChildren().addAll(label, input, removeButton);
		
		this.ID = ID;
		
		this.rootController = rootController;
	}
	
	
	
	private void remove() {
		this.rootController.removeExpression(this.ID);
	}



	public int getID() {
		return ID;
	}
	
	

}
