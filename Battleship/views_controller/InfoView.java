package views_controller;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class InfoView extends BorderPane {
    private ViewManager viewManager; // our local viewManager
    
    private VBox vBox = new VBox();
    private Label infoText = new Label("Welcome to Battleship Game!");
    private Label description = new Label("Battleship is a strategic game where players place ships on a grid and take turns guessing where the opponent's ships are located. The goal is to sink all of the opponent's ships before they sink yours.");
    private Label aboutBattleship = new Label("PIRATE MODE:");
    private Label battleshipInfo = new Label("Pirate Mode is a custom gamemode where each player makes one shot for every ship they have that is not sunk. The next player then does the same. The game ends when one player has all their ships sunk.");
    private Label howToPlay = new Label("How to Play:");
    private Label howToPlayInfo = new Label("1. Each player places their ships on their grid.\n2. Players take turns clicking coordinates to guess the opponent's ship locations.\n3. If the guessed coordinate has an opponent's ship, it's a hit. Otherwise, it's a miss.\n4. The game continues until one player sinks all of the opponent's ships.");
    public Button backButton = new Button("Back");
    private Image background = new Image(new File("Tiles/background.png").toURI().toString());

    public InfoView(ViewManager viewManager) {
        this.viewManager = viewManager; // allows us to change the program view from here
        
        infoText.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-font-family: Arial;");
        description.setStyle("-fx-font-size: 16; -fx-font-family: Arial;");
        aboutBattleship.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-font-family: Arial;");
        battleshipInfo.setStyle("-fx-font-size: 14; -fx-font-family: Arial;");
        howToPlay.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-font-family: Arial;");
        howToPlayInfo.setStyle("-fx-font-size: 14; -fx-font-family: Arial;");
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.CENTER);
        battleshipInfo.setWrapText(true);
        battleshipInfo.setTextAlignment(TextAlignment.CENTER);

        vBox.getChildren().addAll(infoText, description, aboutBattleship, battleshipInfo, howToPlay, howToPlayInfo, backButton);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), Insets.EMPTY)));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        vBox.setPadding(new Insets(20));
        vBox.setMaxWidth(670);
        vBox.setMaxHeight(400);
        this.setCenter(vBox);
        this.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));
        
		backButton.setOnAction(mouseEvent -> {
			// changes the program view to the already created start view.
			viewManager.changeView(viewManager.getStartView());
		});
    }
}
