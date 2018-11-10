package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import layer.ExplicitXFunctionCartesianLayer;
import layer.ExplicitYFunctionCartesianLayer;

public class ExpressionBoxController implements Initializable {

	private int ID;
	
	@FXML
	private TextField inputTxt;
	
	@FXML
	private Button removeBtn;
	
	@FXML
	private Button settingsBtn;
	
	@FXML
	private CheckBox visibleCBox;
	
	private StringProperty functionText = new SimpleStringProperty();
	
	private InputPane inputPane;
	
	public ExpressionBoxController() {
			
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.functionText.set(inputTxt.textProperty().getValueSafe());
		this.functionText.bind(inputTxt.textProperty());
		this.functionText.addListener(event -> changeLayer());
		
		
		this.removeBtn.setOnAction(event -> {
			try {
				remove();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		this.visibleCBox.setOnAction(event -> changeLayer());
		
	}



	private void changeLayer() {
		
		if(visibleCBox.isSelected()) {
			String f = this.functionText.getValueSafe();
			
			
			if(f.length() == 0) {
				inputPane.removeLayer(ID);
				return;
			}
			
			int xInstances = f.length() - f.replaceAll("x","").length();
			
			int yInstances = f.length() - f.replaceAll("y","").length();
			
			
			if(xInstances > 0 && yInstances > 0) {
				inputPane.removeLayer(ID);
				return;
			} else if(yInstances > 0) {
			
				try {
					ExplicitYFunctionCartesianLayer l = new ExplicitYFunctionCartesianLayer(f);
					
					inputPane.putLayer(this.ID, l);
					
					System.out.println("update - "+l);
					
				} catch(NullPointerException e) {
					System.out.println("share doesnt exist");
				} catch(Exception e) {
					System.out.println("other prob");
				}
			} else {
				try {
					ExplicitXFunctionCartesianLayer l = new ExplicitXFunctionCartesianLayer(f);
					
					inputPane.putLayer(this.ID, l);
					
					System.out.println("update - "+l);
					
				} catch(NullPointerException e) {
					System.out.println("share doesnt exist");
				} catch(Exception e) {
					System.out.println("other prob");
				}
			}
		} else {
			inputPane.removeLayer(ID);
		}
	}



	private void remove() throws IOException {
		this.inputPane.removeExpression(this.ID);
	}



	public void setID(int iD) {
		ID = iD;
	}
	
	
	public int getID() {
		return ID;
	}


	public void setInputPane(InputPane inputPane) {
		this.inputPane = inputPane;
	}


	public StringProperty getFunctionText() {
		return functionText;
	}

	

}
