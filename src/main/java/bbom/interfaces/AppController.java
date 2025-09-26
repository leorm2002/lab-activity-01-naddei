package bbom.interfaces;

public interface AppController {

    /**
     * @param model
     */
    AppController initialize(AppModel model, ExternalInterface ui);

    /**
     * Implements the logic to handle the buton press event
     * @throws RuntimeException if the controller have not been initialized yet, it means there is an error in the code
     */
    void handleUpdate();

    /** 
     * Implements the logic to handle the GUI exit event
     * @throws RuntimeException if the controller have not been initialized yet, it means there is an error in the code
     */
    void handleGuiExit();

}