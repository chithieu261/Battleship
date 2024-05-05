package views_controller;

import java.io.File;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import model.*;
import model.Board.ShotResult;
import model.Player.PlayerType;
import views_controller.DefaultGameView.ShotListener;

public class PirateGameView extends BorderPane {
	private ViewManager viewManager;

	private int turnCounter;
	private HBox ourHBox = new HBox();
	private Player player1;
	private Player player2;
	private ImageView[][] player1Grid;
	private ImageView[][] player2Grid;
	private GridPane player1GridPane = new GridPane();
	private GridPane player2GridPane = new GridPane();
	public Button winnerButton = new Button("Next");
	public Button loserButton = new Button("Next");

	private Image shipha = new Image(new File("Tiles/shipha.gif").toURI().toString());
	private Image shiphb = new Image(new File("Tiles/shiphb.gif").toURI().toString());
	private Image shiphc = new Image(new File("Tiles/shiphc.gif").toURI().toString());
	private Image shipva = new Image(new File("Tiles/shipva.gif").toURI().toString());
	private Image shipvb = new Image(new File("Tiles/shipvb.gif").toURI().toString());
	private Image shipvc = new Image(new File("Tiles/shipvc.gif").toURI().toString());

	private Image hitha = new Image(new File("Tiles/hitha.gif").toURI().toString());
	private Image hithb = new Image(new File("Tiles/hithb.gif").toURI().toString());
	private Image hithc = new Image(new File("Tiles/hithc.gif").toURI().toString());
	private Image hitva = new Image(new File("Tiles/hitva.gif").toURI().toString());
	private Image hitvb = new Image(new File("Tiles/hitvb.gif").toURI().toString());
	private Image hitvc = new Image(new File("Tiles/hitvc.gif").toURI().toString());

	private Image sunkha = new Image(new File("Tiles/sunkha.png").toURI().toString());
	private Image sunkhb = new Image(new File("Tiles/sunkhb.png").toURI().toString());
	private Image sunkhc = new Image(new File("Tiles/sunkhc.png").toURI().toString());
	private Image sunkva = new Image(new File("Tiles/sunkva.png").toURI().toString());
	private Image sunkvb = new Image(new File("Tiles/sunkvb.png").toURI().toString());
	private Image sunkvc = new Image(new File("Tiles/sunkvc.png").toURI().toString());

	private Image ocean = new Image(new File("Tiles/Oceangif1.gif").toURI().toString());
	private Image miss = new Image(new File("Tiles/pmiss.gif").toURI().toString());
	private Image sunk = new Image(new File("Tiles/psunk.gif").toURI().toString());
	private Image hit = new Image(new File("Tiles/phit.gif").toURI().toString());
	private Image background = new Image(new File("Tiles/background.png").toURI().toString());
	private Image winImage = new Image(new File("Tiles/VICTORY.png").toURI().toString());
	private Image loseImage = new Image(new File("Tiles/LOSS.png").toURI().toString());

	private static final Media SHOOT_NOISE = new Media(new File("soundeffects/shoot.mp3").toURI().toString());
	private static final Media HIT_NOISE = new Media(new File("soundeffects/hit.mp3").toURI().toString());
	private static final Media MISS_NOISE = new Media(new File("soundeffects/miss.mp3").toURI().toString());
	private static final Media GOOD_NOISE = new Media(new File("soundeffects/good.mp3").toURI().toString());
	private static final Media BAD_NOISE = new Media(new File("soundeffects/bad.mp3").toURI().toString());
	private Sounds soundEffect = new Sounds();

	public PirateGameView(ViewManager viewManager) {
		this.viewManager = viewManager;
		this.player1 = new Player(PlayerType.HUMAN, 10);
		this.player2 = new Player(PlayerType.HUMAN, 10);
		initializePanel();
		winnerButton.setOnAction(mouseEvent -> {
			soundEffect.clearQueue();
			viewManager.getGameEndView().endImg.setImage(winImage);
			viewManager.changeView(viewManager.getGameEndView());
		});
		loserButton.setOnAction(mouseEvent -> {
			soundEffect.clearQueue();
			viewManager.getGameEndView().endImg.setImage(loseImage);
			viewManager.changeView(viewManager.getGameEndView());
		});
	}

	public void setPlayer1(Player player) {
		this.player1 = player;
		initializePanel();
	}

	public void setPlayer2(Player player) {
		this.player2 = player;
		initializePanel();
	}

	private void initializePanel() {
		int boardSize = player1.getBoardSize();
		turnCounter = 0;
		player1Grid = new ImageView[boardSize][boardSize];
		player2Grid = new ImageView[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				player1Grid[i][j] = new ImageView(userImageChoice(player1.getBoard().playerToStringCoord(i, j)));
				player1Grid[i][j].setFitWidth(40);
				player1Grid[i][j].setFitHeight(40);
				player2Grid[i][j] = new ImageView(ocean);
				player2Grid[i][j].setFitWidth(40);
				player2Grid[i][j].setFitHeight(40);
				player2Grid[i][j].setOnMouseClicked(new ShotListener());
				player2GridPane.add(player2Grid[i][j], i, j);
				player1GridPane.add(player1Grid[i][j], i, j);
			}
		}
		player2GridPane.setHgap(2);
		player2GridPane.setVgap(2);
		player1GridPane.setHgap(2);
		player1GridPane.setVgap(2);
		ourHBox.getChildren().clear();
		ourHBox.getChildren().add(new Label("Opponent Board: "));
		ourHBox.getChildren().add(player2GridPane);
		ourHBox.getChildren().add(new Label("\tUser Board: "));
		ourHBox.getChildren().add(player1GridPane);
		ourHBox.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));
		ourHBox.setBorder(new Border(new BorderStroke(null, null, null, null, new Insets(10, 10, 10, 10))));
		ourHBox.getChildren().add(winnerButton);
		ourHBox.getChildren().add(loserButton);
		winnerButton.setVisible(false);
		loserButton.setVisible(false);
		this.setCenter(ourHBox);
	}

	/**
	 * Determines images for opponent grid
	 * 
	 * @param stat string of ship type at that location
	 * @return Image for that location
	 */
	private Image opponentImageChoice(String stat) {
		if (stat.equals("H")) {
			return hit;
		} else if (stat.equals("M")) {
			return miss;
		} else if (stat.equals("Sunk")) {
			return sunk;
		} else if (stat.equals("S")) {
			return hit;
		} else if (stat.equals("_")) {
			return ocean;
		}
		return null;

	}

	/**
	 * Determines images for player grid
	 * 
	 * @param stat string of ship type at that location
	 * @return Image for that location
	 */
	private Image userImageChoice(String stat) {
		if (stat.equals("M")) {
			return miss;
		} else if (stat.equals("VA")) {
			return shipva;
		} else if (stat.equals("VB")) {
			return shipvb;
		} else if (stat.equals("VC")) {
			return shipvc;
		} else if (stat.equals("HA")) {
			return shipha;
		} else if (stat.equals("HB")) {
			return shiphb;
		} else if (stat.equals("HC")) {
			return shiphc;
		} else if (stat.equals("HVA")) {
			return hitva;
		} else if (stat.equals("HVB")) {
			return hitvb;
		} else if (stat.equals("HVC")) {
			return hitvc;
		} else if (stat.equals("HHA")) {
			return hitha;
		} else if (stat.equals("HHB")) {
			return hithb;
		} else if (stat.equals("HHC")) {
			return hithc;
		} else if (stat.equals("SVA")) {
			return sunkva;
		} else if (stat.equals("SVB")) {
			return sunkvb;
		} else if (stat.equals("SVC")) {
			return sunkvc;
		} else if (stat.equals("SHA")) {
			return sunkha;
		} else if (stat.equals("SHB")) {
			return sunkhb;
		} else if (stat.equals("SHC")) {
			return sunkhc;
		}
		return ocean;
	}

	private boolean didWin() {
		int boardSize = player1.getBoardSize();
		if (!player1.isAlive() || !player2.isAlive()) {
			for (int k = 0; k < boardSize; k++) {
				for (int l = 0; l < boardSize; l++) {
					player1Grid[k][l].setImage(userImageChoice(player1.getBoard().playerToStringCoord(k, l)));
					player2Grid[k][l].setImage(userImageChoice(player2.getBoard().playerToStringCoord(k, l)));
				}
			}
			if (!player1.isAlive())
				loserButton.setVisible(true);
			if (!player2.isAlive())
				winnerButton.setVisible(true);
			return true;
		}
		return false;
	}

	private void aiTurn() {
		turnCounter = 0;
		int boardSize = player1.getBoardSize();
		for (int n = 0; n < player2.getBoard().getShipsLeft(); n++) {
			player2.takeShot(player1);
			for (int i = 0; i < boardSize; i++)
				for (int j = 0; j < boardSize; j++)
					player1Grid[i][j].setImage(userImageChoice(player1.getBoard().playerToStringCoord(i, j)));
			didWin();
		}
	}

	public class ShotListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent arg0) {
			if (!didWin()) {
				int boardSize = player1.getBoardSize();
				ImageView ImageClicked = (ImageView) arg0.getSource();
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (player2Grid[i][j] == ImageClicked) {
							turnCounter++;
							ShotResult player1Outcome = player1.takeShot(player2, i, j);
							if (player1Outcome == ShotResult.GAME_WON) {
								soundEffect.queueSound(SHOOT_NOISE);
								soundEffect.queueSound(GOOD_NOISE);
							} else if (player1Outcome == ShotResult.HIT) {
								soundEffect.queueSound(SHOOT_NOISE);
								soundEffect.queueSound(HIT_NOISE);
							} else if (player1Outcome == ShotResult.MISS) {
								soundEffect.queueSound(SHOOT_NOISE);
								soundEffect.queueSound(MISS_NOISE);
							} else if (player1Outcome == ShotResult.REPEAT_ATTEMPT) {
								turnCounter--;
								soundEffect.queueSound(BAD_NOISE);
							} else if (player1Outcome == ShotResult.SUNK) {
								soundEffect.queueSound(SHOOT_NOISE);
								soundEffect.queueSound(SHOOT_NOISE);
							}
							player2Grid[i][j].setImage(opponentImageChoice(player2.getBoard().toStringCoord(i, j)));
							if (player2Grid[i][j].getImage() == sunk)
								for (int k = 0; k < boardSize; k++)
									for (int l = 0; l < boardSize; l++)
										if (player2.getBoard().toStringCoord(k, l) == "Sunk")
											player2Grid[k][l].setImage(sunk);
						}
						didWin();
					}
				}
				if (turnCounter == player1.getBoard().getShipsLeft())
					aiTurn();
			}
		}
	}
}
