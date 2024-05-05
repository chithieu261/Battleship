package networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import javafx.application.Platform;
import javafx.util.Callback;
import model.Player;
import model.Ship;
import views_controller.ViewManager;

public class BattleshipClient extends Listener {

	// Our client object.
	static Client client;
	// IP to connect to.
	private static String ip;
	private ViewManager vm;
	Player me;
	Player enemy;

	private static int port;

	// A boolean value.
	static boolean messageReceived = false;

	public void main() throws Exception {
		System.out.println("Connecting to the server...");
		// Create the client.
		client = new Client();

		// Register the packet object.
		client.getKryo().register(PacketMessage.class);
		client.getKryo().register(PlayerMessage.class);
		client.getKryo().register(ShotMessage.class);
		client.getKryo().register(model.Player.class);
		client.getKryo().register(model.Board.class);
		client.getKryo().register(Object[][].class);
		client.getKryo().register(Object[].class);
		client.getKryo().register(model.Ship.class);
		client.getKryo().register(int[].class);
		client.getKryo().register(java.util.ArrayList.class);
		client.getKryo().register(model.Player.PlayerType.class);

		// client.getKryo().register(javafx.beans.property.SimpleObjectProperty.class);

		// Start the client
		client.start();
		// The client MUST be started before connecting can take place.

		// Connect to the server - wait 5000ms before failing.
		client.connect(5000, ip, port, port);

		// Add a listener
		client.addListener(this);

		System.out.println("Connected! The client program is now waiting for a packet...\n");

		// This is here to stop the program from closing before we receive a message.
	}

	public BattleshipClient(int port, String ip, ViewManager vm) throws Exception {
		BattleshipClient.port = port;
		BattleshipClient.ip = ip;
		this.vm = vm;
		main();
	}

	public void shotMessage(ShotMessage sm) {
		client.sendTCP(sm);
	}

	public void shutDown() {
		// Close the client connection and stop the client processing
		if (client != null && client.isConnected()) {
			client.stop();
			System.out.println("Client connection closed.");
		}
	}

	public void disconnected() {
		Platform.runLater(() -> {
			vm.changeView(vm.getMultiplayerSetupView());
			vm.getMultiplayerSetupView().statusText.setText("Server Has Closed.");
			vm.getMultiplayerSetupView().showLeaveAlert();
			vm.getMultiplayerSetupView().attemptShutdown();
		});
	}

	// I'm only going to implement this method from Listener.class because I only
	// need to use this one.
	public void received(Connection c, Object p) {
		// Is the received packet the same class as PacketMessage.class?
		if (p instanceof PacketMessage) {
			// Cast it, so we can access the message within.
			PacketMessage packet = (PacketMessage) p;
			if (packet.message.contains("Start")) {
				Platform.runLater(() -> {
					vm.changeView(vm.getMPShipSelectionView());
					vm.getMPShipSelectionView().startButton.setOnAction(mouseEvent -> {
						me = vm.getMPShipSelectionView().getConstructedPlayer(vm.getBoardSize());
						if (me != null) {
							PlayerMessage playerMessage = vm.getMPShipSelectionView().getPlayerMessage();
							client.sendTCP(playerMessage);
							vm.getMPShipSelectionView().startButton.setDisable(true);
						}
					});
				});
			}
			if (packet.message.contains("Left!")) {
				disconnected();
			}
			if (packet.message.contains("GO")) {
				Platform.runLater(() -> {
					vm.changeView(vm.getMPGameView());
					vm.getMPGameView().setClient(this);
					vm.getMPGameView().setPlayer1(me);
					vm.getMPGameView().setPlayer2(enemy);
				});
			}
			if (packet.message.contains("TURN")) {
				Platform.runLater(() -> {
					vm.getMPGameView().allowShot(true);
				});
			}
			if (packet.message.contains("NOT")) {
				Platform.runLater(() -> {
					vm.getMPGameView().allowShot(false);
				});
			}
			System.out.println("received a message from the host: " + packet.message);
		}
		if (p instanceof ShotMessage) {
			ShotMessage sm = (ShotMessage) p;
			vm.getMPGameView().updateMyself(sm);
		}
		if (p instanceof PlayerMessage) {
			// Cast it, so we can access the message within.
			PlayerMessage pm = (PlayerMessage) p;
			enemy = new Player(pm.type, pm.size);
			enemy.getBoard().addShipToBoard(new Ship(pm.ship1[0], pm.ship1[1], pm.ship1[2], pm.ship1[3]));
			enemy.getBoard().addShipToBoard(new Ship(pm.ship2[0], pm.ship2[1], pm.ship2[2], pm.ship2[3]));
			enemy.getBoard().addShipToBoard(new Ship(pm.ship3[0], pm.ship3[1], pm.ship3[2], pm.ship3[3]));
			enemy.getBoard().addShipToBoard(new Ship(pm.ship4[0], pm.ship4[1], pm.ship4[2], pm.ship4[3]));
			enemy.getBoard().addShipToBoard(new Ship(pm.ship5[0], pm.ship5[1], pm.ship5[2], pm.ship5[3]));
			System.out.println("Added enemy board to local file!");
		}
	}
}
