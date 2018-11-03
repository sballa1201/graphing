package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ExpressionBox extends HBox {

	private int ID;
	
	private Label label;
	
	private TextField input;
	
	private Button removeButton;
	
	private StringProperty functionText = new SimpleStringProperty();
	
	private InputPaneController rootController;
	
	public ExpressionBox(int ID, InputPaneController rootController) {
		
		this.ID = ID;
		
		this.rootController = rootController;
		
		
		
		label = new Label("Label");
		
		label.setAlignment(Pos.CENTER_LEFT);
		
	
		
		input = new TextField();
		input.setAlignment(Pos.CENTER_LEFT);
		
		this.functionText.set(input.textProperty().getValueSafe());
		this.functionText.bind(input.textProperty());
		this.functionText.addListener(event -> changeLayer());
		
		removeButton = new Button("X");
		
		removeButton.setAlignment(Pos.CENTER_LEFT);
		
		removeButton.setOnAction(event -> remove());
		
		Region fillerSpace1 = new Region();
		Region fillerSpace2 = new Region();
		
		this.getChildren().addAll(label, fillerSpace1, input, fillerSpace2, removeButton);
		
		HBox.setMargin(removeButton, new Insets(0, 5, 0, 5));
		
		HBox.setMargin(label, new Insets(0, 5, 0, 5));
		
		HBox.setMargin(input, new Insets(0, 5, 0, 5));
		
		
		
		HBox.setHgrow(fillerSpace1, Priority.ALWAYS);
		HBox.setHgrow(fillerSpace2, Priority.ALWAYS);
		
		
		
		
		this.setPadding(new Insets(10, 0, 10, 0));
	}
	
	
	
	private void changeLayer() {
		
		String f = this.functionText.getValueSafe();;
		
		
		if(f.length() == 0) {
			rootController.removeLayer(ID);
			return;
		}
		
		try {
			ExplicitFunctionLayer l = new ExplicitFunctionLayer(f);
			
			rootController.putLayer(this.ID, l);
			
			System.out.println("update - "+l);
			
		} catch(NullPointerException e) {
			System.out.println("share doesnt exist");
		} catch(Exception e) {
			System.out.println("other prob");
		}
	}



	private void remove() {
		this.rootController.removeExpression(this.ID);
	}



	public int getID() {
		return ID;
	}



	public StringProperty getFunctionText() {
		return functionText;
	}



}
