package application;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public interface ChangeScene {
	
	public default void noAnimation(ActionEvent event, String name) throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource("/" + name + ".fxml"));
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(pane));
	}
	
	public default void fadeIn(ActionEvent event, String name) throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource("/" + name+ ".fxml"));
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		FadeTransition ft = new FadeTransition(Duration.millis(1000), pane);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();
		
	    stage.setScene(new Scene(pane));
	}
	
	public default void fadeOut(ActionEvent event, String name) throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource("/" + name+ ".fxml"));
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		FadeTransition ft = new FadeTransition(Duration.millis(1000), pane);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);
		ft.play();
	    
		stage.setScene(new Scene(pane));
	}
	
	public default void translate(ActionEvent event, String name, int seconds) throws IOException, InterruptedException {
		Pane pane = FXMLLoader.load(getClass().getResource("/" + name+ ".fxml"));
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		
		TranslateTransition transOut = new TranslateTransition(Duration.seconds(seconds), (Pane)((Node)event.getSource()).getParent());
        //trans.setFromX(((Node)event.getSource()).getScene().getWidth());
        transOut.setToX(-1.0 * ((Node)event.getSource()).getScene().getWidth());
        transOut.setCycleCount(1);
        
        
        transOut.setOnFinished(new EventHandler<ActionEvent>(){
        	 
            @Override
            public void handle(ActionEvent arg0) {
            	stage.setScene(new Scene(pane));
            }
        });
        

        transOut.play();

	}
	
	
	public default void openNewStage(String name, boolean lock, boolean resizable) throws IOException {
		Pane root = FXMLLoader.load(getClass().getResource("/ShowScores.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        if(lock) {
        	stage.initModality(Modality.APPLICATION_MODAL);
        }
        stage.setResizable(resizable);
        stage.show();
	}
	
	//TODO new translate method not yet working 
	public default void WIP(ActionEvent event, String name, int seconds) throws IOException, InterruptedException {
		Pane pane1 = (Pane) ((Node)event.getSource()).getParent();
		Pane pane2 = FXMLLoader.load(getClass().getResource("/" + name + ".fxml"));
		
		//load this later
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		//stage.setScene(new Scene(pane2));
		
		WritableImage img1 = pane1.snapshot(new SnapshotParameters(), null);
		WritableImage img2 = pane2.snapshot(new SnapshotParameters(), null);
		
		ImageView iv1 = new ImageView(img1);
		ImageView iv2 = new ImageView(img2);
		
		
		
		//Thread.sleep(1000);
		
		StackPane tPane = new StackPane();
		tPane.getChildren().add(iv1);
		tPane.getChildren().add(iv2);
		
		stage.setScene(new Scene(tPane));
		
		TranslateTransition transOut = new TranslateTransition(Duration.seconds(seconds), tPane.getChildren().get(0));
        //trans.setFromX(((Node)event.getSource()).getScene().getWidth());
        transOut.setToX(-1.0 * ((Node)event.getSource()).getScene().getWidth());
        transOut.setCycleCount(1);
        
        
        transOut.setOnFinished(new EventHandler<ActionEvent>(){
        	 
            @Override
            public void handle(ActionEvent arg0) {
            	stage.setScene(new Scene(pane2));
            }
        });
        

        transOut.play();
		
		
	}
	
}
