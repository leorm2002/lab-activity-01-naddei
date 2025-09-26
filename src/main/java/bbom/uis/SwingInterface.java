package bbom.uis;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import bbom.controllers.MyController;
import bbom.interfaces.AppController;
import bbom.interfaces.AppModel;
import bbom.interfaces.ExternalInterface;

/**
 * A simple Swing-based GUI for the BBoM application. the simplicity of this app
 * does not require to register it to the controller
 */
public class SwingInterface extends JFrame implements ExternalInterface, ActionListener {

    private AppController controller;
    private final Supplier<AppController> controllerSupplier;


    public SwingInterface( Supplier<AppController> controllerSupplier) {
        // Not very nice this is eageryly initialized instead laziyly when init is called but we can't do much about it
        super("My BBoM App");

        setSize(300, 70);
        setResizable(false);

        var updateButton = new JButton("Update");
        updateButton.addActionListener(this);

        var panel = new JPanel();
        panel.add(updateButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                handleClose();
            }
        });

        this.controllerSupplier = controllerSupplier;
    }

    @Override
    public void init(AppModel model) {
        // Register this UI with the model
        this.controller =new MyController().initialize(model, this);
        model.registerUserInterface(this);

        // Try to show the GUI, if this fail we throw a specific runtime exception
        // wrapping the original one
        try {
            SwingUtilities.invokeAndWait(() -> {
                this.setVisible(true);
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize swing module", e);
        }

    }

    @Override
    public void displayState(int state) {
        // Originally logged to the console
        //System.out.println("Current state: " + state);
        // Now we can show it in the GUI so we don't use the console
        this.setTitle("Current state: " + state);
    }

    // Method to handle window closing event
    public void handleClose() {
        this.dispose();
        controller.handleGuiExit();
    }

    // Handle the click of the button
    @Override
    public void actionPerformed(ActionEvent e) {
        controller.handleUpdate();
    }
}
