package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class InputPaneController implements Initializable {
	
	@FXML
	private ScrollPane inputPane;
	
	@FXML
	private VBox expressionBox;
	
	private int IDMaxCount = 0;
	
	private List<Integer> freeID = new ArrayList<Integer>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
		
		
		this.setupNewButton();
		
		addExpression();
		
	}
	
	
	private void addExpression() {
		
		int insert = this.expressionBox.getChildren().size() - 1;
		
		int ID;
		
		
		
		if(this.freeID.size() == 0) {
			ID = IDMaxCount;
			IDMaxCount++;
		} else {
			ID = this.freeID.remove(0);
			
		}
		
		ExpressionBox box = new ExpressionBox(ID,this);
		this.expressionBox.getChildren().add(insert,box);
		
		
	}
	
	public void removeExpression(int ID) {
		
		for(int i=0; i < this.expressionBox.getChildren().size() - 1; i++) {
			ExpressionBox box = (ExpressionBox) this.expressionBox.getChildren().get(i);
			if(box.getID() == ID) {
				this.expressionBox.getChildren().remove(i);
				this.freeID.add(ID);
				break;
			}
		}
		
		
		
		if(this.expressionBox.getChildren().size() == 1) {
			this.freeID = new ArrayList<Integer>();
			this.IDMaxCount = 0;
			this.addExpression();
		}
		
	}
	
	private void setupNewButton() {
		Button newExpression = new Button("New Expression");
		
		newExpression.setOnAction(event -> addExpression());
		
		
		//newExpression.setPrefWidth(8000);		
		GridPane buttonHolder = new GridPane();
		
		buttonHolder.add(newExpression, 0, 0);
			
		
		this.expressionBox.getChildren().add(buttonHolder);
	}
	
}
