package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.ServerHandler.ServerComms;

public class ClientHandler {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Board gameBoard;
    private boolean running;
    private MessageCallback messageCallback; // Callback interface reference

    public ClientHandler(String serverAddress, int port, MessageCallback callback) throws Exception {
        System.out.print("Starting client handler...");
        socket = new Socket(serverAddress, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        running = true;
        this.messageCallback = callback; // Assign the callback instance
        startGameLoop();
    }

    private void startGameLoop() {
        try {
        	send(ServerComms.READY);
            while (running) {
                Object incomingObject = input.readObject(); // Read incoming object
                if (incomingObject != null) {
                    System.out.println("\nReceived object: " + incomingObject.toString()); // Display incoming object
                    notifyCallback(incomingObject); // Notify the callback with the received object
                } else {
                    // Handle null or end of stream if necessary
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void send(Object msg) {
        try {
            output.writeObject(msg);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyCallback(Object object) {
        if (messageCallback != null) {
            messageCallback.onObjectReceived(object); // Notify the callback with the received object
        }
    }

    public void stopClient() {
        running = false;
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Interface for callback
    public interface MessageCallback {
        void onObjectReceived(Object object);
    }
}
