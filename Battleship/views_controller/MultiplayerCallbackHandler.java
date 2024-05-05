package views_controller;

import javafx.application.Platform;
import model.ClientHandler;
import model.Player;
import model.ServerHandler.ServerComms;

public class MultiplayerCallbackHandler implements ClientHandler.MessageCallback {
	private ViewManager vm;
	private ClientHandler ch;

	public MultiplayerCallbackHandler(ViewManager viewManager) {
		this.vm = viewManager;
	}

	public void setClientHandler(ClientHandler ch) {
		this.ch = ch;
		Platform.runLater(() -> {
			//vm.getMPShipSelectionView().setClientHandler(ch);
		});
	}

	// Implementing the onObjectReceived method from ClientHandler.MessageCallback
	@Override
	public void onObjectReceived(Object object) {
		if (object instanceof ServerComms) {
			ServerComms message = (ServerComms) object;
			Platform.runLater(() -> {
				switch (message) {
				case PLAYER_1:
					vm.changeView(vm.getMPShipSelectionView());
				case PLAYER_2:
					vm.changeView(vm.getMPShipSelectionView());
				default:
					break;
				}
			});
		}
	}
}
