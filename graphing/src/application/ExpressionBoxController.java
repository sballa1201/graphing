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
import layer.ExplicitPolarLayer;
import layer.ExplicitXFunctionCartesianLayer;
import layer.ExplicitYFunctionCartesianLayer;
import layer.Layer;
import structures.Expression;
import structures.NormalDistribution;

public class ExpressionBoxController implements Initializable {

	private int ID;

	@FXML
	private TextField inputTxt;

	@FXML
	private Button removeBtn;

	@FXML
	private Button settingsBtn;

	@FXML
	private Canvas visibleCanvas;
	private GraphicsContext gc;

	private boolean visible = true;

	private Color color;

	private StringProperty functionText = new SimpleStringProperty();

	private InputPane inputPane;

	public ExpressionBoxController() {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.functionText.set(inputTxt.textProperty().getValueSafe());
		this.functionText.bind(inputTxt.textProperty());
		this.functionText.addListener(event -> {
			try {
				changeLayer();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
				e.printStackTrace();
			}
		});

		this.removeBtn.setOnAction(event -> {
			try {
				remove();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		this.gc = this.visibleCanvas.getGraphicsContext2D();
		this.gc.setLineWidth(2);
		this.visibleCanvas.setOnMouseClicked(event -> {
			try {
				changeColor();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private void changeLayer() throws StackOverflowException, StackUnderflowException, UnequalBracketsException {

		if (visible) {

			Layer l;

			String f = this.functionText.getValueSafe();

			if (f.length() == 0) {
				inputPane.removeLayer(ID);
				return;
			}

			if (f.startsWith("Normal(")) {
				f = f.replaceAll("Normal\\(", "");
				f = f.replaceAll("\\)", "");
				String[] variables = f.split(",");
				// μ;
				double μ = 0;
				// σ2;
				double σ2 = 1;
				μ = new Expression(variables[0], 'x').evaluate(0);
				σ2 = new Expression(variables[1], 'x').evaluate(0);

				NormalDistribution normalDist = null;
				normalDist = new NormalDistribution(μ, σ2);

				l = new ExplicitXFunctionCartesianLayer(normalDist);
				l.setColor(color);

				inputPane.putLayer(this.ID, l);
				
				return;
			}

			int xInstances = f.length() - f.replaceAll("x", "").length();

			int yInstances = f.length() - f.replaceAll("y", "").length();

			int tInstances = f.length() - f.replaceAll("t", "").length();

			if (xInstances > 0 && yInstances > 0) {
				inputPane.removeLayer(ID);
				return;
			} else if (tInstances > 0) {
				l = new ExplicitPolarLayer(f);
				l.setColor(color);

				inputPane.putLayer(this.ID, l);

				System.out.println("update - " + l);
			} else if (yInstances > 0) {

				l = new ExplicitYFunctionCartesianLayer(f);
				l.setColor(color);

				inputPane.putLayer(this.ID, l);

				System.out.println("update - " + l);

			} else {
				l = new ExplicitXFunctionCartesianLayer(f);
				l.setColor(color);

				inputPane.putLayer(this.ID, l);

				System.out.println("update - " + l);

			}
		} else {
			inputPane.removeLayer(ID);
		}
	}

	private void changeColor() throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		gc.clearRect(0, 0, visibleCanvas.getWidth(), visibleCanvas.getHeight());
		if (!visible) {
			gc.fillRect(0, 0, visibleCanvas.getWidth(), visibleCanvas.getHeight());
		}
		gc.strokeRect(0, 0, visibleCanvas.getWidth(), visibleCanvas.getHeight());
		visible = !visible;
		changeLayer();
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

	public void setColor(Color color) {
		this.color = color;
		gc.setStroke(color);
		gc.setFill(color);
		gc.fillRect(0, 0, visibleCanvas.getWidth(), visibleCanvas.getHeight());
	}

}
