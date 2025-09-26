package bbom;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import bbom.models.ThreadSafeModel;
import bbom.uis.SwingInterface;
import bbom.uis.CLIInterface;
import bbom.uis.SocketInterface;
import bbom.interfaces.AppController;
import bbom.interfaces.AppModel;

/**
 * Application launcher
 */
public class AppLauncher {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        // Thread-safe handling using an AtomicInteger in the model but probably it is not the point of this lab
        final AppModel model = new ThreadSafeModel();

        // The controller will be created lazily when the UI is initialized
        final Supplier<AppController> controllerSupplier = bbom.controllers.MyController::new;

        // Fixed at built time, no dynamic UI loading, may be enhanced by using a factory and some kind of external configuration/service
        // Launch multiple UIs to test MVC pattern and the closing of one UI does not affect the others
        new SwingInterface(controllerSupplier).init(model);
        new SwingInterface(controllerSupplier).init(model);
        new CLIInterface(controllerSupplier).init(model);
        new SocketInterface(controllerSupplier, 12345).init(model);
    }
}
