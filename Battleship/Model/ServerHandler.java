package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ServerHandler {
	private ServerSocket serverSocket;
	private List<ServerClient> clients = new ArrayList<>();
	private CountDownLatch readyLatch;

	public enum ServerComms {
		START_GAME, PLAYER_1, PLAYER_2, READY;
	}

	public ServerHandler(int port) throws IOException {
		System.out.println("Starting server listener..");
		try {
			serverSocket = new ServerSocket(port);

			readyLatch = new CountDownLatch(2); // Two players need to signal readiness

			// Start listening for incoming connections
			while (clients.size() < 2) {
				Socket clientSocket = serverSocket.accept();
				ServerClient serverClient = new ServerClient(clientSocket, this);
				clients.add(serverClient);
				serverClient.start();
			}

			waitForReadySignal();
			startGame();
		} catch (IOException e) {
			throw e;
		}
	}

	private void waitForReadySignal() {
		try {
			// Wait for both players to signal readiness
			readyLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void playerReady() {
		readyLatch.countDown(); // Decrement the latch count
	}

	private void startGame() {
		System.out.println("game is goin!");
		clients.get(1).send(ServerComms.PLAYER_2);
		clients.get(0).send(ServerComms.PLAYER_1);
	}

	public void stopServer() {
		try {
			clients.get(0).stopSocket();
			clients.get(1).stopSocket();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Other methods to manage game logic, sessions, etc.
}
