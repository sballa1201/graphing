package application;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import javafx.application.Application;
import javafx.stage.Stage;
import structures.NormalDistribution;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	// methods
	@Override
	// a method to start the gui
	public void start(Stage primaryStage) {
		try {
			// load the main FXML file
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Main.fxml"));
			// create the scene with the borderpane as the root node
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass()
					.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			// make the window 1020 x 720 big
			primaryStage.setHeight(720);
			primaryStage.setWidth(1020);
			// show the window
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// run the program
	public static void main(String[] args) {
		// launch the gui
		launch(args);
	}
}