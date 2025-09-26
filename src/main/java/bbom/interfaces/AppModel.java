package bbom.interfaces;

/**
 * The interface for the application model. It defines the methods that the model must implement
 * to allow interaction with the user interfaces.
 */
public interface AppModel {

    /**
     * Update the internal state and notify all registered UIs
     */
    void update();

    /**
     * Register a new UI to be notified on state changes
     * @param ui
     */
    void registerUserInterface(ExternalInterface ui);

    /**
     * Multi UI handling, we only exit the app when the last UI is closed
     * @param ui
     */
    void unregisterUserInterface(ExternalInterface ui);

}