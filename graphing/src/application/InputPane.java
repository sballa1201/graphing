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

	private VBox expressionBoxStore;
	
	private ShareLayers shareLayerStore;

	private int nextID = 0;

	private final Color[] colors = { Color.BLACK, Color.BLUE, Color.RED, Color.DARKGREEN, Color.MAGENTA, Color.GOLDENROD };

	public InputPane() throws IOException {
		
		this.nextID = 0;
		
		this.setFitToHeight(true);
		this.setFitToWidth(true);
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setPrefWidth(245.0);
		
		this.expressionBoxStore = new VBox();

		this.setContent(this.expressionBoxStore);

		// this.setPannable(true);

		this.setupNewButton();

		this.addExpressionBox();

	}

	private void addExpressionBox() throws IOException {

		int insert = this.expressionBoxStore.getChildren().size() - 1;

		FXMLLoader loader = new FXMLLoader();

		loader.setLocation(Main.class.getResource("ExpressionBox.fxml"));
		HBox expressionBox = loader.load();

		expressionBox.setId(Integer.toString(nextID));

		ExpressionBoxController eBoxController = (ExpressionBoxController) loader.getController();

		eBoxController.setID(nextID);
		eBoxController.setInputPane(this);

		eBoxController.setColor(this.getColor(nextID % this.colors.length));

		this.expressionBoxStore.getChildren().add(insert, expressionBox);
		
		nextID = nextID + 1;
	}

	public void removeExpressionBox(int ID) throws IOException {

		this.removeLayer(ID);

		for (int i = 0; i < this.expressionBoxStore.getChildren().size() - 1; i++) {
			HBox box = (HBox) this.expressionBoxStore.getChildren().get(i);
			if (Integer.parseInt(box.getId()) == ID) {
				this.expressionBoxStore.getChildren().remove(i);
				break;
			}
		}

		if (this.expressionBoxStore.getChildren().size() == 1) {
			this.nextID = 0;
			this.addExpressionBox();
		}

	}

	public void putLayer(int ID, Layer layer) {
		System.out.println("putting layer");
		shareLayerStore.putLayer(ID, layer);
	}

	public void removeLayer(int ID) {
		shareLayerStore.removeLayer(ID);
	}

	private void setupNewButton() {
		Button newExpression = new Button("New Function");

		newExpression.setOnAction(event -> {
			try {
				addExpressionBox();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		HBox buttonHolder = new HBox();

		Region fillerSpace1 = new Region();
		Region fillerSpace2 = new Region();

		buttonHolder.getChildren().addAll(fillerSpace1, newExpression, fillerSpace2);

		HBox.setHgrow(fillerSpace1, Priority.ALWAYS);
		HBox.setHgrow(fillerSpace2, Priority.ALWAYS);

		this.expressionBoxStore.getChildren().add(buttonHolder);

		// this.updateVbox();

	}


	public void setShareLayerStore(ShareLayers shareLayerStore) {
		this.shareLayerStore = shareLayerStore;
	}

	public Color getColor(int ID) {
		return colors[ID % colors.length];
	}

}
