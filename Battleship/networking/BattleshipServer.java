package networking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import model.Player;
import model.ServerClient;
import model.Ship;

public class BattleshipServer extends Listener {

	// Server object
	static Server server;

	private static int port;
	private List<Connection> clients = new ArrayList<>();
	private Player player1;
	private Player player2;
	
	public void main() throws Exception {
		System.out.println("Creating the server...");
		// Create the server
		server = new Server();

		// Register a packet class.
		server.getKryo().register(PacketMessage.class);
		server.getKryo().register(PlayerMessage.class);
		server.getKryo().register(ShotMessage.class);
		server.getKryo().register(model.Player.class);
		server.getKryo().register(model.Board.class);
		server.getKryo().register(Object[][].class);
		server.getKryo().register(Object[].class);
		server.getKryo().register(model.Ship.class);
		server.getKryo().register(int[].class);
		server.getKryo().register(java.util.ArrayList.class);
		server.getKryo().register(model.Player.PlayerType.class);
		// server.getKryo().register(javafx.beans.property.SimpleObjectProperty.class);
		// We can only send objects as packets if they are registered.
	    String ipAddress = "your_desired_ip_address"; // Replace this with your desired IP address

		// Bind to a port
		server.bind(port, port);

		// Start the server
		server.start();

		// Add the listener
		server.addListener(this);

		System.out.println("Server is operational!");
	}

	public BattleshipServer(int port) throws Exception {
		BattleshipServer.port = port;
		main();
	}

	// This is run when a connection is received!
	public void connected(Connection c) {
		System.out.println("Received a connection from " + c.getRemoteAddressTCP().getHostString());

		// Create a message packet.
		if (clients.size() < 2) {
			System.out.println("Adding client to game!");
			clients.add(c);
		}
		if (clients.size() == 2) {
			System.out.println("Starting game!");
			PacketMessage packetMessage = new PacketMessage();
			// Assign the message text.
			packetMessage.message = "Start game!";

			// Send the message
			clients.get(0).sendTCP(packetMessage);
			clients.get(1).sendTCP(packetMessage);
		}
	}

	// This method shuts down the server gracefully
	public void shutDown() {
		if (server != null) {
			// Close all connections and stop the server
			PacketMessage packetMessage = new PacketMessage();
			// Assign the message text.
			packetMessage.message = "Left!";
			if (clients.size()==2) {
				clients.get(1).sendTCP(packetMessage);
			}
			server.close();
			server.stop();
			System.out.println("Server is shut down.");
		}
	}

	// This is run when we receive a packet.
	public void received(Connection c, Object p) {
		if (p instanceof PlayerMessage) {
			// PlayerMessage playerMessage = (PlayerMessage) p;
			PlayerMessage pm = (PlayerMessage) p;
			if (clients.get(0).equals(c)) {
				player1 = new Player(pm.type, pm.size);
				player1.getBoard().addShipToBoard(new Ship(pm.ship1[0], pm.ship1[1], pm.ship1[2], pm.ship1[3]));
				player1.getBoard().addShipToBoard(new Ship(pm.ship2[0], pm.ship2[1], pm.ship2[2], pm.ship2[3]));
				player1.getBoard().addShipToBoard(new Ship(pm.ship3[0], pm.ship3[1], pm.ship3[2], pm.ship3[3]));
				player1.getBoard().addShipToBoard(new Ship(pm.ship4[0], pm.ship4[1], pm.ship4[2], pm.ship4[3]));
				player1.getBoard().addShipToBoard(new Ship(pm.ship5[0], pm.ship5[1], pm.ship5[2], pm.ship5[3]));
				System.out.println("Adding player 1 board!");
				System.out.println(pm.ship1.toString());
				clients.get(1).sendTCP(pm);

			} else {
				player2 = new Player(pm.type, pm.size);
				player2.getBoard().addShipToBoard(new Ship(pm.ship1[0], pm.ship1[1], pm.ship1[2], pm.ship1[3]));
				player2.getBoard().addShipToBoard(new Ship(pm.ship2[0], pm.ship2[1], pm.ship2[2], pm.ship2[3]));
				player2.getBoard().addShipToBoard(new Ship(pm.ship3[0], pm.ship3[1], pm.ship3[2], pm.ship3[3]));
				player2.getBoard().addShipToBoard(new Ship(pm.ship4[0], pm.ship4[1], pm.ship4[2], pm.ship4[3]));
				player2.getBoard().addShipToBoard(new Ship(pm.ship5[0], pm.ship5[1], pm.ship5[2], pm.ship5[3]));
				System.out.println(pm.ship1.toString());
				System.out.println("Adding player 2 board!");
				clients.get(0).sendTCP(pm);
			}
			if (player1 != null && player2 != null) {
				PacketMessage goMsg = new PacketMessage();
				PacketMessage notMsg = new PacketMessage();
				PacketMessage shootMsg = new PacketMessage();
				// Assign the message text.
				goMsg.message = "GO!";
				notMsg.message = "NOT";
				shootMsg.message = "TURN";
				clients.get(0).sendTCP(goMsg);
				clients.get(1).sendTCP(goMsg);
				clients.get(0).sendTCP(shootMsg);
				clients.get(1).sendTCP(notMsg);
			}
		}
		if (p instanceof ShotMessage) {
			PacketMessage notMsg = new PacketMessage();
			PacketMessage shootMsg = new PacketMessage();
			notMsg.message = "NOT";
			shootMsg.message = "TURN";
			ShotMessage sm = (ShotMessage) p;
			if (clients.get(0).equals(c)) {
				player1.takeShot(player2, sm.x, sm.y);
				clients.get(1).sendTCP(sm);
				clients.get(0).sendTCP(notMsg);
				clients.get(1).sendTCP(shootMsg);
			} else {
				player2.takeShot(player1, sm.x, sm.y);
				clients.get(0).sendTCP(sm);
				clients.get(1).sendTCP(notMsg);
				clients.get(0).sendTCP(shootMsg);
			}
		}
	}

	// This is run when a client has disconnected.
	public void disconnected(Connection c) {
		clients.remove(c);
		if (clients.size() > 0) {
			PacketMessage packetMessage = new PacketMessage();
			// Assign the message text.
			packetMessage.message = "Someone Left!";
			clients.get(0).sendTCP(packetMessage);
		}
		System.out.println("A client disconnected!");
	}
}
