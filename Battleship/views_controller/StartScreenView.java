package views_controller;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class StartScreenView extends BorderPane{
    private ViewManager viewManager;

    private VBox ourVBox = new VBox();
    public Button startButton = new Button("Start");
    public Button infoButton = new Button("Info");
    public Button multiplayerButton = new Button("Multiplayer");
    private Image titleImage = new Image(new File("Tiles/background.gif").toURI().toString());
    private Image backgroundImage = new Image(new File("Tiles/background.png").toURI().toString());
    private Label credits = new Label("By: Kade, Chi, Danilo, and Ethan");

    private ImageView titleView = new ImageView(titleImage);

    public StartScreenView(ViewManager viewManager) {
        this.viewManager = viewManager;
        initializePanel();
		startButton.setOnAction(mouseEvent -> {
			viewManager.changeView(viewManager.getShipSelectionView());
		});
		infoButton.setOnAction(mouseEvent -> {
			viewManager.changeView(viewManager.getInfoView());
		});
		multiplayerButton.setOnAction(mouseEvent -> {
			viewManager.changeView(viewManager.getMultiplayerSetupView());
		});
    }

    private void initializePanel() {
        titleView.setFitWidth(400); // Adjust the width as needed
        titleView.setPreserveRatio(true);

        ourVBox.getChildren().addAll(titleView, credits, startButton, infoButton, multiplayerButton);
        ourVBox.setAlignment(Pos.CENTER);
        ourVBox.setSpacing(20);
        ourVBox.setPadding(new Insets(50));
        
        BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        ourVBox.setBackground(new Background(backgroundImg));

        setCenter(ourVBox);
    }

}
