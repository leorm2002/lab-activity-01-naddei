package bbom;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@Deprecated
/**
 * The original GUI application for the BBoM lab activity.
 */
public class MyApp extends JFrame  implements ActionListener {

	private int state;
	
	public MyApp() {		
		super("My BBoM App");

		state = 0;
		
		setSize(300, 70);
		setResizable(false);
		
		var updateButton = new JButton("Update");
		updateButton.addActionListener(this);

		var panel = new JPanel();		
		panel.add(updateButton);		
		
		setLayout(new BorderLayout());
	    add(panel,BorderLayout.NORTH);
	    	    		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(-1);
			}
		});
		log("State value: " + state);
	}

	/* INPUT PROCESSING */
	
	public void actionPerformed(ActionEvent ev) {
		
		/* BUSINESS LOGIC */		
		state++;		
		
		/* OUTPUT PROCESSING */
		log("State value: " + state);
	}	
	
	private void log(String msg) {
		System.out.println("[Output] " + msg);
	}

    static public void main(String[] args) throws Exception {			  
    	MyApp app = new MyApp();
		SwingUtilities.invokeAndWait(() -> {
			app.setVisible(true);
		});
	}	
}
