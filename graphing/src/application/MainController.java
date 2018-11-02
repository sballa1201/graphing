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
	
	@FXML
	private Canvas canvas;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*
		gc = canvas.getGraphicsContext2D();
		canvasPane.setStyle("-fx-background-color: white");
		canvas.heightProperty().bind(canvasPane.heightProperty());
		canvas.widthProperty().bind(canvasPane.widthProperty());
		try {
			//Function f = new Function("x^2");
			Function g = new Function("1/x + 1/(x+2)");
			//this.functions.add(f);
			this.functions.add(g);
			
			
		} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
			e.printStackTrace();
		}
		ChangeListener<Number> redrawListener = (observable, oldValue, newValue) -> {
			try {
				draw();
			} catch (StackOverflowException | StackUnderflowException | UnequalBracketsException e) {
				e.printStackTrace();
			}
		};
		canvasPane.heightProperty().addListener(redrawListener);
		canvasPane.widthProperty().addListener(redrawListener);
		*/
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
		rootPane.setLeft(inputPane);
	}
	
	
}
