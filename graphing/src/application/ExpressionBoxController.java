package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import layer.ExplicitXFunctionCartesianLayer;
import layer.ExplicitYFunctionCartesianLayer;
import layer.Layer;
import structures.Expression;
import structures.NormalDistribution;

public class ExpressionBoxController implements Initializable {

	// attributes
	private int ID;
	private GraphicsContext gc;
	private boolean functionVisiblity = true;
	private Color color;
	private StringProperty functionText = new SimpleStringProperty();
	private InputPane inputPane;
	// FXML components
	@FXML
	private TextField inputTxt;
	@FXML
	private Button removeBtn;
	@FXML
	private Canvas showFunctionColour;

	// methods
	// empty constructor
	public ExpressionBoxController() {
	}

	@Override
	// called when initialized a pseudo constructor of sorts
	public void initialize(URL arg0, ResourceBundle arg1) {
		// bind the input to a string property in the class to manipulate
		this.functionText.set(inputTxt.textProperty().getValueSafe());
		this.functionText.bind(inputTxt.textProperty());
		// add a listener so that every time it changes the layer is updated
		this.functionText.addListener(event -> {
			try {
				changeLayer();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
				e.printStackTrace();
			}
		});
		// if the remove button is clicked call the remove method
		this.removeBtn.setOnAction(event -> {
			try {
				remove();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		// create the canvas to show the color of the function
		this.gc = this.showFunctionColour.getGraphicsContext2D();
		this.gc.setLineWidth(2);
		// when the canvas is clicked change its color by calling the change Color
		// method
		this.showFunctionColour.setOnMouseClicked(event -> {
			try {
				changeColor();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
				e.printStackTrace();
			}
		});
	}

	// update the layer
	private void changeLayer() throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		// only change the layer if is not hidden by the user
		if (functionVisiblity) {
			Layer l;
			String f = this.functionText.getValueSafe();
			// if the function is empty remove the layer
			if (f.length() == 0) {
				inputPane.removeLayer(ID);
				return;
			}
			// if it begins with normal create a normal distribution layer
			if (f.startsWith("Normal(")) {
				f = f.replaceAll("Normal\\(", "");
				f = f.replaceAll("\\)", "");
				String[] variables = f.split(",");
				// μ;
				double μ = 0;
				// σ2;
				double σ2 = 1;
				// get the values for the mean and variance
				μ = new Expression(variables[0], 'x').evaluate(0);
				σ2 = new Expression(variables[1], 'x').evaluate(0);
				// create the function
				NormalDistribution normalDist = null;
				normalDist = new NormalDistribution(μ, σ2);
				// create the layer with the specific color
				l = new ExplicitXFunctionCartesianLayer(normalDist);
				l.setColor(color);
				// add the layer to the shared layer access
				inputPane.putLayer(this.ID, l);
				// terminate this function
				return;
			}
			// determine the whether it is an explicit x or y function by counting their
			// respective instances
			// count the number of x instances
			int xInstances = f.length() - f.replaceAll("x", "").length();
			// count the number of y instances
			int yInstances = f.length() - f.replaceAll("y", "").length();
			if (xInstances > 0 && yInstances > 0) { // if it contains both x and y remove it since it is implicit
				inputPane.removeLayer(ID);
				return;
			} else if (yInstances > 0) { // if it contains only y create a explicit function in y
				l = new ExplicitYFunctionCartesianLayer(f);
				l.setColor(color);
				inputPane.putLayer(this.ID, l);
			} else { // if it contains only x create a explicit function in x
				l = new ExplicitXFunctionCartesianLayer(f);
				l.setColor(color);
				inputPane.putLayer(this.ID, l);
			}
		} else { // if the function is hidden then remove the layer
			inputPane.removeLayer(ID);
		}
	}
	
	// show/hide the function and update the visual identifier
	private void changeColor() throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		// clear the canvas
		gc.clearRect(0, 0, showFunctionColour.getWidth(), showFunctionColour.getHeight());
		if (!functionVisiblity) {
			// fill it in color if showing the function
			gc.fillRect(0, 0, showFunctionColour.getWidth(), showFunctionColour.getHeight());
		}
		// draw a black border so it is clickable
		gc.strokeRect(0, 0, showFunctionColour.getWidth(), showFunctionColour.getHeight());
		// negate the visibility since it has changed
		functionVisiblity = !functionVisiblity;
		// remove or add the layer to show/hide it
		changeLayer();
	}

	// remove itself from the input pane
	private void remove() throws IOException {
		this.inputPane.removeExpressionBox(this.ID);
	}

	// set the id
	public void setID(int iD) {
		ID = iD;
	}

	// get the id
	public int getID() {
		return ID;
	}

	// set the parent input pane
	public void setInputPane(InputPane inputPane) {
		this.inputPane = inputPane;
	}

	// set the color of the function
	public void setColor(Color color) {
		this.color = color;
		gc.setStroke(color);
		gc.setFill(color);
		gc.fillRect(0, 0, showFunctionColour.getWidth(), showFunctionColour.getHeight());
	}

}