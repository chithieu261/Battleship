package views_controller;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Player;
import model.Ship;
import model.Player.PlayerType;

public class ShipPlacementViewV2 extends BorderPane {
	
	    private ViewManager viewManager;

		private VBox ourVBox = new VBox();

		private Image background = new Image(new File("Tiles/background.png").toURI().toString());

		public Button easyButton = new Button("Start Easy Game");
		public Button normalButton = new Button("Start Normal Game");
		public Button hardButton = new Button("Start Hard Game");
		public Button backButton = new Button("Back");
		
		private Image ocean = new Image(new File("Tiles/Oceangif1.gif").toURI().toString());
		private ImageView[][] player1Grid;
		
		private TextField twoXCoord = new TextField();
		private TextField twoYCoord = new TextField();
		private TextField twoVert = new TextField();

		private TextField threeAXCoord = new TextField();
		private TextField threeAYCoord = new TextField();
		private TextField threeAVert = new TextField();

		private TextField threeBXCoord = new TextField();
		private TextField threeBYCoord = new TextField();
		private TextField threeBVert = new TextField();

		private TextField fourXCoord = new TextField();
		private TextField fourYCoord = new TextField();
		private TextField fourVert = new TextField();

		private TextField fiveXCoord = new TextField();
		private TextField fiveYCoord = new TextField();
		private TextField fiveVert = new TextField();

		public ShipPlacementViewV2(ViewManager viewManager) {
	        this.viewManager = viewManager;
	        int size = viewManager.getBoardSize();
			reset();
			initializePanel();
			easyButton.setOnAction(mouseEvent -> {
				viewManager.getDefaultView().setPlayer1(getConstructedPlayer(size));
				viewManager.getDefaultView().setPlayer2(new Player(PlayerType.AI_EASY, size));
				viewManager.changeView(viewManager.getDefaultView());
			});
			normalButton.setOnAction(mouseEvent -> {
				viewManager.getDefaultView().setPlayer1(getConstructedPlayer(size));
				viewManager.getDefaultView().setPlayer2(new Player(PlayerType.AI_NORMAL, size));
				viewManager.changeView(viewManager.getDefaultView());
			});
			hardButton.setOnAction(mouseEvent -> {
				viewManager.getDefaultView().setPlayer1(getConstructedPlayer(size));
				viewManager.getDefaultView().setPlayer2(new Player(PlayerType.AI_HARD, size));
				viewManager.changeView(viewManager.getDefaultView());
			});
			backButton.setOnAction(mouseEvent -> {
				viewManager.changeView(viewManager.getStartView());
			});
			
		}
		
		public void reset() {		
			twoXCoord.setText("");
			twoYCoord.setText("");
			twoVert.setText("");
			threeAXCoord.setText("");
			threeAYCoord.setText("");
			threeAVert.setText("");
			threeBXCoord.setText("");
			threeBYCoord.setText("");
			threeBVert.setText("");
			fourXCoord.setText("");
			fourYCoord.setText("");
			fourVert.setText("");
			fiveXCoord.setText("");
			fiveYCoord.setText("");
			fiveVert.setText("");
		}

		private void initializePanel() {
			
			ourVBox.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));
			ourVBox.setBorder(new Border(new BorderStroke(null, null, null, null, new Insets(10, 10, 10, 10))));
			Label explanation1 = new Label("Add your ships: ");
			Label explanation2 = new Label("Verticality takes 0 for horizontal or 1 for vertical");
			Label pLabel = new Label("\nPatrol Boat (length of 2): ");
			Label sLabel = new Label("\nSubmarine (length of 3): ");
			Label dLabel = new Label("\nDestroyer (length of 3): ");
			Label bLabel = new Label("\nBattleship (length of 4): ");
			Label cLabel = new Label("\nCarrier (length of 5): ");
			explanation1.setStyle("-fx-font-size: 20; -fx-font-family: Verdana");
			explanation2.setStyle("-fx-font-size: 15; -fx-font-family: Verdana");
			pLabel.setStyle("-fx-font-size: 15; -fx-font-family: Verdana");
			sLabel.setStyle("-fx-font-size: 15; -fx-font-family: Verdana");
			dLabel.setStyle("-fx-font-size: 15; -fx-font-family: Verdana");
			bLabel.setStyle("-fx-font-size: 15; -fx-font-family: Verdana");
			cLabel.setStyle("-fx-font-size: 15; -fx-font-family: Verdana");
			
			

			GridPane patrol = new GridPane();
			twoXCoord.setMaxWidth(50);
			twoYCoord.setMaxWidth(50);
			twoVert.setMaxWidth(50);
			patrol.add(new Label("\tx coord: "), 1, 0);
			patrol.add(new Label("\ty coord: "), 3, 0);
			patrol.add(new Label("\tverticality: "), 5, 0);
			patrol.add(twoXCoord, 2, 0);
			patrol.add(twoYCoord, 4, 0);
			patrol.add(twoVert, 6, 0);

			GridPane submarine = new GridPane();
			threeAXCoord.setMaxWidth(50);
			threeAYCoord.setMaxWidth(50);
			threeAVert.setMaxWidth(50);
			submarine.add(new Label("\tx coord: "), 1, 0);
			submarine.add(new Label("\ty coord: "), 3, 0);
			submarine.add(new Label("\tverticality: "), 5, 0);
			submarine.add(threeAXCoord, 2, 0);
			submarine.add(threeAYCoord, 4, 0);
			submarine.add(threeAVert, 6, 0);

			GridPane destroyer = new GridPane();
			threeBXCoord.setMaxWidth(50);
			threeBYCoord.setMaxWidth(50);
			threeBVert.setMaxWidth(50);
			destroyer.add(new Label("\tx coord: "), 1, 0);
			destroyer.add(new Label("\ty coord: "), 3, 0);
			destroyer.add(new Label("\tverticality: "), 5, 0);
			destroyer.add(threeBXCoord, 2, 0);
			destroyer.add(threeBYCoord, 4, 0);
			destroyer.add(threeBVert, 6, 0);

			GridPane battleship = new GridPane();
			fourXCoord.setMaxWidth(50);
			fourYCoord.setMaxWidth(50);
			fourVert.setMaxWidth(50);
			battleship.add(new Label("\tx coord: "), 1, 0);
			battleship.add(new Label("\ty coord: "), 3, 0);
			battleship.add(new Label("\tverticality: "), 5, 0);
			battleship.add(fourXCoord, 2, 0);
			battleship.add(fourYCoord, 4, 0);
			battleship.add(fourVert, 6, 0);

			GridPane carrier = new GridPane();
			fiveXCoord.setMaxWidth(50);
			fiveYCoord.setMaxWidth(50);
			fiveVert.setMaxWidth(50);
			carrier.add(new Label("\tx coord: "), 1, 0);
			carrier.add(new Label("\ty coord: "), 3, 0);
			carrier.add(new Label("\tverticality: "), 5, 0);
			carrier.add(fiveXCoord, 2, 0);
			carrier.add(fiveYCoord, 4, 0);
			carrier.add(fiveVert, 6, 0);

			GridPane buttons = new GridPane();
			buttons.add(easyButton, 2, 0);
			buttons.add(normalButton, 7, 0);
			buttons.add(hardButton, 12, 0);
			backButton.setStyle("-fx-font-size: 10px; -fx-min-width: 40px; -fx-min-height: 20px;");
			buttons.add(backButton, 2, 2);
			buttons.setHgap(10);
			buttons.setVgap(10);

			ourVBox.getChildren().addAll(explanation1, explanation2, pLabel, patrol, sLabel, submarine, dLabel, destroyer,
					bLabel, battleship, cLabel, carrier, new Label("\n"), buttons);
			this.setCenter(ourVBox);
		}

		public Player getConstructedPlayer(int size) {
			try {
				Player player = new Player(PlayerType.HUMAN, size);
				player.getBoard().addShipToBoard(new Ship(Integer.parseInt(twoXCoord.getText()),
						Integer.parseInt(twoYCoord.getText()), Integer.parseInt(twoVert.getText()), 2));

				player.getBoard().addShipToBoard(new Ship(Integer.parseInt(threeAXCoord.getText()),
						Integer.parseInt(threeAYCoord.getText()), Integer.parseInt(threeAVert.getText()), 3));

				player.getBoard().addShipToBoard(new Ship(Integer.parseInt(threeBXCoord.getText()),
						Integer.parseInt(threeBYCoord.getText()), Integer.parseInt(threeBVert.getText()), 3));

				player.getBoard().addShipToBoard(new Ship(Integer.parseInt(fourXCoord.getText()),
						Integer.parseInt(fourYCoord.getText()), Integer.parseInt(fourVert.getText()), 4));

				player.getBoard().addShipToBoard(new Ship(Integer.parseInt(fiveXCoord.getText()),
						Integer.parseInt(fiveYCoord.getText()), Integer.parseInt(fiveVert.getText()), 5));
				return player;
			} catch (Exception e) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Ship placement is not valid!");
				errorAlert.setContentText("Make sure all your ships are properly placed and all fields are filled out.");
				errorAlert.showAndWait();
			}
			return null;
		}
	}

