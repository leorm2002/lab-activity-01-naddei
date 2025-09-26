package bbom.models;

import java.util.List;

import bbom.interfaces.AppModel;
import bbom.interfaces.ExternalInterface;

/**
 * A simple implementation of the AppModel interface.
 * It maintains an internal state (an integer) and a list of registered UIs.
 * When the state is updated, all registered UIs are notified.
 * When all UIs are closed, the application exits.
 */
public class SimpleModel implements AppModel {
    private int state = 0;

    // Only appends and sequential access, so LinkedList is fine
    private final List<ExternalInterface> uis = new java.util.LinkedList<>();

      /**
     * Update the internal state and notify all registered UIs
     */
    @Override
    public void update() {
        state++;
        
        // Notify all UIs about the state change
        uis.forEach(ui -> ui.displayState(state));
    }

    /**
     * Register a new UI to be notified on state changes
     * @param ui
     */
    @Override
    public void registerUserInterface(ExternalInterface ui) {
        uis.add(ui);
    }

  
    /**
     * Multi UI handling, we only exit the app when the last UI is closed
     * @param ui
     */
    @Override
    public void unregisterUserInterface(ExternalInterface ui) {
        uis.remove(ui);
        // If no UIs are registered anymore, exit the application
        if (uis.isEmpty()) {
            System.out.println("No more UIs registered, exiting.");
            System.exit(0);
        }
    }

}
