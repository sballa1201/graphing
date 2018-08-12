package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import structures.Function;

public class MainController implements Initializable {
	
	@FXML
	private Pane canvasPane;
	
	@FXML
	private Canvas canvas;
	
	private GraphicsContext gc;
	private ArrayList<Function> functions = new ArrayList<Function>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		canvasPane.setStyle("-fx-background-color: white");
		canvas.heightProperty().bind(canvasPane.heightProperty());
		canvas.widthProperty().bind(canvasPane.widthProperty());
	}
	
	@FXML
	private void draw() {
		System.out.println(canvas.getWidth());
		System.out.println(canvas.getHeight());
		System.out.println();
		gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0,canvas.getWidth(), canvas.getHeight());
		gc.setLineWidth(2.0);
		gc.setFill(Color.RED);
		gc.strokeLine(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	
}
