package bbom.uis;

import bbom.controllers.MyController;
import bbom.interfaces.AppController;
import bbom.interfaces.AppModel;
import bbom.interfaces.ExternalInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.function.Supplier;

public class SocketInterface implements ExternalInterface {
    private final Supplier<AppController> controllerSupplier;
    private final int port;
    private volatile Socket clientSocket; // ultimo client collegato
    private volatile PrintWriter out; // stream di uscita verso il client
    private boolean initialized = false;


    public SocketInterface(Supplier<AppController> controllerSupplier, int port) {
        this.controllerSupplier = controllerSupplier;
        this.port = port;
    }


    @Override
    public void init(AppModel model) throws RuntimeException {
        model.registerUserInterface(this);
        final AppController controller = new MyController().initialize(model, this);
        new Thread(() -> startServer(port, controller, model)).start();
        this.initialized = true;
    }

    @Override
    public void displayState(int state) {
        if (!initialized) {
            throw new RuntimeException("This UI has not been initialized yet");
        }
        if (out != null) {
            try {
                out.println("STATE: " + state);
                out.flush();
            } catch (Exception e) {
                throw new RuntimeException("Failed to send state to client", e);
            }
        }
    }

    /**
     * Avvia un server TCP che accetta un solo client alla volta
     */
    private void startServer(int port, AppController controller, AppModel model) {
        System.out.println("Starting TCP server on port " + port);
        boolean running = true;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                // Accetta un nuovo client (bloccante finché non arriva una connessione)
                clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    // Salva lo stream per displayState
                    this.out = clientOut;

                    String line;
                    while ((line = in.readLine()) != null) {
                        String message = line.trim();
                        System.out.println("SOCKET-CLI Received: " + message);
                        switch (message.toUpperCase()) {
                            case "UPDATE":
                                controller.handleUpdate();
                                break;
                            case "EXIT":
                                running = false;
                                break;
                            default:
                                out.println("Unknown command");
                                out.flush();
                                break;
                        }
                        if (!running)
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("SOCKET-CLI Client disconnected " + e.getMessage());
                } finally {
                    this.out = null;
                    if (clientSocket != null && !clientSocket.isClosed()) {
                        try {
                            clientSocket.close();
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
            controller.handleGuiExit();
        } catch (Exception e) {
            System.out.println("SOCKET-SERVER Error starting TCP server on port " + port);
            throw new RuntimeException("Socket server error", e);
        }
    }
}
