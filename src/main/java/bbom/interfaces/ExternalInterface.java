package bbom.interfaces;

/**
 * It models a simple communication interface contract also known as a View.
 * Every UI must be initialized with a model berfore being used.
 * Unsing a Interface instead of an abstract class to allow more flexibility to the implementators of this interface who may need to extend another class.
 * An abstract class may be used if we want to force initialization eagerly at construction time.
 */
public interface ExternalInterface {
    /**
     * Display the current state of the model. a guy may output this information in any way.
     * @param state the current state of the model
     */
    void displayState(int state) throws RuntimeException;

    /**
     * Initialize the UI with the given model.
     * @param model 
     * @throws RuntimeException initialization may fail for any reason this should be handled gracefully
     */
    void init(AppModel model) throws RuntimeException;

}
