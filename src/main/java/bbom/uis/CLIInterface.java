package bbom.uis;

import java.util.function.Supplier;

import bbom.interfaces.AppController;
import bbom.interfaces.AppModel;
import bbom.interfaces.ExternalInterface;

public class CLIInterface implements ExternalInterface {
    private final Supplier<AppController> controllerSupplier;

    public CLIInterface(Supplier<AppController> controllerSupplier) {
        this.controllerSupplier = controllerSupplier;
    }

    @Override
    public void displayState(int state) {
        System.out.println("Current state: " + state);
    }

    @Override
    public void init(AppModel model) throws RuntimeException {
    
        // Initialize the controller
        AppController controller = controllerSupplier.get().initialize(model, this);
        model.registerUserInterface(this);

        // Spawn a thread to read from the terminal
        spawnTerminalThread(controller, model);
    }
    

    static void spawnTerminalThread(AppController controller, AppModel model) {
        // We create a thread to read from the terminal
        var readerThread = new Thread(() -> {
            var reader = new java.util.Scanner(System.in);
            while (true) {
                System.out.println("Press Enter to update the model, or type 'exit' to quit");
                var line = reader.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                }
                controller.handleUpdate();
            }
            reader.close();
        });
        readerThread.setDaemon(true);
        readerThread.start();
    }

}
