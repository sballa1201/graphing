package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import layer.Layer;

public class InputPane extends ScrollPane {

	// attributes
	private VBox expressionBoxStore;
	private ShareLayers shareLayerStore;
	private int nextID = 0;
	private final Color[] colors = 
	{ Color.BLACK, Color.BLUE, Color.RED, Color.DARKGREEN, Color.MAGENTA, Color.GOLDENROD };

	// methods
	// constructor
	public InputPane() throws IOException {
		// set up the input pane attributes
		this.nextID = 0;
		this.setFitToHeight(true);
		this.setFitToWidth(true);
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setPrefWidth(245.0);
		this.expressionBoxStore = new VBox();
		this.setContent(this.expressionBoxStore);
		// set up the button
		this.setupNewButton();
		// add the first expression box
		this.addExpressionBox();
	}

	// add a new expression box
	private void addExpressionBox() throws IOException {
		// index at which to insert the new box, one before the button
		int insert = this.expressionBoxStore.getChildren().size() - 1;
		// load and initialize the expression box
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("ExpressionBox.fxml"));
		HBox expressionBox = loader.load();
		// get the controller class and set the ID, input pane and color attributes
		ExpressionBoxController eBoxController = (ExpressionBoxController) loader.getController();
		expressionBox.setId(Integer.toString(nextID));
		eBoxController.setID(nextID);
		eBoxController.setInputPane(this);
		eBoxController.setColor(colors[nextID % this.colors.length]);
		// add the expression box to the vbox
		this.expressionBoxStore.getChildren().add(insert, expressionBox);
		// increment the ID so the next box has a different ID
		nextID = nextID + 1;
	}

	// remove an expression box
	public void removeExpressionBox(int ID) throws IOException {
		// remove the layer that the expression box is linked to
		this.removeLayer(ID);
		// find the expression box and remove it
		for (int i = 0; i < this.expressionBoxStore.getChildren().size() - 1; i++) {
			HBox box = (HBox) this.expressionBoxStore.getChildren().get(i);
			if (Integer.parseInt(box.getId()) == ID) {
				this.expressionBoxStore.getChildren().remove(i);
				break;
			}
		}
		// if there are no expression boxes left, reset the ID counter and add a new one
		if (this.expressionBoxStore.getChildren().size() == 1) {
			this.nextID = 0;
			this.addExpressionBox();
		}
	}

	// add a layer to the plot pane through the shared layer access
	public void putLayer(int ID, Layer layer) {
		shareLayerStore.putLayer(ID, layer);
	}

	// remove a layer from the plot pane through the shared layer access
	public void removeLayer(int ID) {
		shareLayerStore.removeLayer(ID);
	}

	// set up the new box
	private void setupNewButton() {
		// initialize the button with the display text "New Function"
		Button newExpression = new Button("New Function");
		// when it is pressed call the addExpressionBox() function
		newExpression.setOnAction(event -> {
			try {
				addExpressionBox();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		// various UI components needed to make the button fit in with the UI
		// hbox to hold the components
		HBox buttonHolder = new HBox();
		// filler space to center the button horizontally
		Region fillerSpace1 = new Region();
		Region fillerSpace2 = new Region();
		// add the filler space and button to the hbox
		buttonHolder.getChildren().addAll(fillerSpace1, newExpression, fillerSpace2);
		HBox.setHgrow(fillerSpace1, Priority.ALWAYS);
		HBox.setHgrow(fillerSpace2, Priority.ALWAYS);
		// add the button to the vbox
		this.expressionBoxStore.getChildren().add(buttonHolder);
	}

	// set the shared layer access attribute
	public void setShareLayerStore(ShareLayers shareLayerStore) {
		this.shareLayerStore = shareLayerStore;
	}

}