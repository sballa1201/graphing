package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	private VBox expressionBoxStore  = new VBox();
	
	public ShareLayers shareLayerStore;
	
	private int IDMaxCount = 0;
	
	private List<Integer> freeID = new ArrayList<Integer>();
	
	private Color[] colors = {Color.BLACK, Color.BLUE, Color.RED, Color.DARKGREEN, Color.MAGENTA, Color.GOLDENROD};
	
	public InputPane() throws IOException {
		
		this.setFitToHeight(true);
		this.setFitToWidth(true);
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setPrefWidth(300.0);

		
		System.out.println(colors[1]);
		
		this.expressionBoxStore = new VBox();
		
		this.setContent(this.expressionBoxStore);
		
		//this.setPannable(true);
		
		this.setupNewButton();
		
		this.addExpression();
		
		
		
		
		
	}
	
	
	private void addExpression() throws IOException {
		
		int insert = this.expressionBoxStore.getChildren().size() - 1;
		
		int ID;
		
		
		
		if(this.freeID.size() == 0) {
			ID = IDMaxCount;
			IDMaxCount++;
		} else {
			ID = this.freeID.remove(0);
			
		}
		
		
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(Main.class.getResource("ExpressionBox.fxml"));
		HBox expressionBox = loader.load();
		
		
		expressionBox.setId(Integer.toString(ID));
		
		ExpressionBoxController eBoxController = (ExpressionBoxController) loader.getController();
		
		eBoxController.setID(ID);
		eBoxController.setInputPane(this);
		
		eBoxController.setColor(this.getColor(ID % this.colors.length));
		
		this.expressionBoxStore.getChildren().add(insert,expressionBox);		
	}
	
	public void removeExpression(int ID) throws IOException {
		
		this.removeLayer(ID);
		
		for(int i=0; i < this.expressionBoxStore.getChildren().size() - 1; i++) {
			HBox box = (HBox) this.expressionBoxStore.getChildren().get(i);
			if(Integer.parseInt(box.getId()) == ID) {
				this.expressionBoxStore.getChildren().remove(i);
				this.freeID.add(ID);
				break;
			}
		}
		
		
		
		if(this.expressionBoxStore.getChildren().size() == 1) {
			this.freeID = new ArrayList<Integer>();
			this.IDMaxCount = 0;
			this.addExpression();
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
		Button newExpression = new Button("New Expression");
		
		newExpression.setOnAction(event -> {
			try {
				addExpression();
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
		
		//this.updateVbox();
		
	}


	public ShareLayers getShareLayerStore() {
		return shareLayerStore;
	}


	public void setShareLayerStore(ShareLayers shareLayerStore) {
		this.shareLayerStore = shareLayerStore;
		System.out.println(this.shareLayerStore);
	}
	
	public Color getColor(int ID) {
		
		System.out.println(ID % colors.length);
		
		return colors[ID % colors.length];
	}
	
}
