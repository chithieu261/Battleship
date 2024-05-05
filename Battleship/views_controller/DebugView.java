package views_controller;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import model.ClientHandler;
import model.ServerHandler;

public class DebugView extends BorderPane implements ClientHandler.MessageCallback {
	private ViewManager vm;

	private TextArea debugTextArea;
	public Button backButton = new Button("Back");

	public DebugView(ViewManager vm) {
		this.vm = vm;

		debugTextArea = new TextArea();
		debugTextArea.setEditable(false); // Make it non-editable
		debugTextArea.setWrapText(true); // Allow text wrapping
		debugTextArea.setStyle("-fx-font-family: Arial; -fx-font-size: 14px; -fx-text-fill: black;");

		setCenter(debugTextArea);
		setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), Insets.EMPTY)));
		setPadding(new Insets(20));
		appendText("Waiting for game to start");
	}

	// Method to append text to the debug view
	public void appendText(String text) {
		debugTextArea.appendText(text + "\n");
	}

	// Implementing the onObjectReceived method from ClientHandler.MessageCallback
	@Override
	public void onObjectReceived(Object object) {
		if (object == ServerHandler.ServerComms.PLAYER_1) {
			appendText("I am player one!");
			appendText("Starting game!");
			Platform.runLater(() -> {
				vm.changeView(vm.getMPShipSelectionView());
			});
			//

		}
		if (object == ServerHandler.ServerComms.PLAYER_2) {
			appendText("I am player two!");
			appendText("Starting game!");
			Platform.runLater(() -> {
				vm.changeView(vm.getMPShipSelectionView());
			});
		}
	}
}
