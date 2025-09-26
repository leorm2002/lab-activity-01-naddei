package bbom.controllers;

import bbom.interfaces.AppController;
import bbom.interfaces.AppModel;
import bbom.interfaces.ExternalInterface;

/**
 * A basic controller. It handles user interactions and updates the model
 * accordingly.
 */
public class MyController implements AppController {
    private AppModel model;
    private ExternalInterface ui;
    private boolean initialized = false;

    /**
     * @param model the model that this controller will interact with
     * @param ui    the UI that this controller have been spawned by
     */
    @Override
    public AppController initialize(AppModel model, ExternalInterface ui) {
        this.initialized = true;
        this.model = model;
        this.ui = ui;

        return this;
    }

    @Override
    /**
     * Implements the logic to handle the buton press event
     * 
     * @throws RuntimeException if the controller have not been initialized yet, it
     *                          means there is an error in the code
     */
    public void handleUpdate() {
        if (!initialized) {
            throw new RuntimeException("This controller have not been initialized yet");
        }
        model.update();
    }

    @Override
    public void handleGuiExit() {
        if (!initialized) {
            throw new RuntimeException("This controller have not been initialized yet");
        }
        model.unregisterUserInterface(ui);
    }

}
