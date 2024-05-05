// Kade Dean
package views_controller;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import model.*;

public class BattleshipGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private ViewManager viewManager;

	private static final Media BACKGROUND = new Media(new File("soundeffects/shanty.mp3").toURI().toString());
	private Sounds soundEffect = new Sounds();

	public BorderPane window;
	public static final int width = 1200;
	public static final int height = 600;

	public void start(Stage stage) {
		viewManager = new ViewManager(this);
		stage.setOnCloseRequest(event -> {
			viewManager.getMultiplayerSetupView().attemptShutdown();
			Platform.exit(); // Exit the application when the window is closed
		});
		soundEffect.loop(BACKGROUND);
		stage.setTitle("Battleship");
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);

		// New view manager implementation
		// CHANGE ME BACK
		viewManager.changeView(viewManager.getStartView());
	}

	public void setViewTo(Node newView) {
		window.setCenter(null);
		window.setCenter((Node) newView);
	}

}
