package bbom;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * A basic TCP client implemented in java to test the {@link bbom.uis.CLIInterface}.
 * Connects to a server, sends commands, and prints server responses.
 */
public class TestClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";   // indirizzo del server
        int port = 12345;             // stessa porta configurata nel server

        try (Socket socket = new Socket(host, port);
             PrintWriter sockOut = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader sockIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner keyboard = new Scanner(System.in)) {

            System.out.println("Connesso a " + host + ":" + port);
            System.out.println("Digita comandi: UPDATE | EXIT");
            System.out.println("Il server invierà messaggi di stato (STATE: n).");

            // Thread che stampa i messaggi provenienti dal server
            Thread listener = new Thread(() -> {
                try {
                    String line;
                    while ((line = sockIn.readLine()) != null) {
                        System.out.println("[SERVER] " + line);
                    }
                } catch (Exception e) {
                    System.out.println("Connection closed: " + e.getMessage());
                }
            });

            listener.setDaemon(true);
            listener.start();

            // Legge i comandi da tastiera e li invia al server
            while (true) {
                String cmd = keyboard.nextLine().trim();
                sockOut.println(cmd);
                if ("EXIT".equalsIgnoreCase(cmd)) {
                    break;
                }
            }

            System.out.println("Client terminated.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
