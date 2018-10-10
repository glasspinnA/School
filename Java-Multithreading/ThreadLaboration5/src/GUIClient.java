import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class GUIClient {
    /**
     * These are the components you need to handle.
     * You have to add listeners and/or code
     */
    private JFrame frame;                // The Main window
    private JTextField txt;                // Input for text to send
    private JButton btnSend;            // Send text in txt
    private JTextArea lstMsg;            // The logger listbox
    private Controller controller;
    private Client client;

    /**
     * Constructor
     */
    public GUIClient() {
  
      
    }

    /**
     * Starts the application
     */
    public void Start(Controller controller, Client client) {
        frame = new JFrame();
        frame.setBounds(100, 100, 300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Klient");            // Change to "Multi Chat Server" on server part and vice versa
        InitializeGUI();                    // Fill in components
        frame.setVisible(true);
        frame.setResizable(false);            // Prevent user from change size
    	this.controller = controller;
    	this.client = client;
    	
        buttonListener();
    }

    /**
     * Sets up the GUI with components
     */
    private void InitializeGUI() {
        txt = new JTextField();
        txt.setBounds(13, 13, 177, 23);
        frame.add(txt);
        btnSend = new JButton("Send");
        btnSend.setBounds(197, 13, 75, 23);
        frame.add(btnSend);
        lstMsg = new JTextArea();
        lstMsg.setEditable(false);
        JScrollPane pane = new JScrollPane(lstMsg);
        pane.setBounds(12, 51, 260, 199);
        pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.add(pane);
    }

    /**
     * Metod som updaterar display med ny text 
     * @param message
     */
    public void updateDisplay(String message) {
        lstMsg.append(message + "\n");
    }
    
	public void buttonListener() {
		btnSend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.messageFromClientGUI(txt.getText(),client);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}

}
