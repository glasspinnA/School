
package assignment1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The GUI for assignment 1, DualThreads
 */
public class GUIDualThreads
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JFrame frame;		// The Main window
	private JButton btnDisplay;	// Start thread moving display
	private JButton btnDStop;	// Stop moving display thread
	private JButton btnTriangle;// Start moving graphics thread
	private JButton btnTStop;	// Stop moving graphics thread
	private JPanel pnlMove;		// The panel to move display in
	private JPanel pnlRotate;	// The panel to move graphics in
	private JLabel lblText = new JLabel("Flume");
	private DisplayTextThread display;
	private RotateImage rotateImage;
	public BufferedImage image;
	private JLabel picLabel;
	
	/**
	 * Constructor
	 */
	public GUIDualThreads()
	{

	}
	
	
	
	
	/**
	 * Starts the application
	 * @throws IOException 
	 */
	public void Start() throws IOException
	{
		frame = new JFrame();
		frame.setBounds(0, 0, 494, 332);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Multiple Thread Demonstrator");
		InitializeGUI();					// Fill in components
		buttonListeners();
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen
	}
	
	/**
	 * Sets up the GUI with components
	 * @throws IOException 
	 */
	private void InitializeGUI() throws IOException
	{
		// The moving display outer panel
		JPanel pnlDisplay = new JPanel();
		Border b2 = BorderFactory.createTitledBorder("Display Thread");
		pnlDisplay.setBorder(b2);
		pnlDisplay.setBounds(12, 12, 222, 269);
		pnlDisplay.setLayout(null);
		
		// Add buttons and drawing panel to this panel
		btnDisplay = new JButton("Start Display");
		btnDisplay.setBounds(10, 226, 121, 23);
		pnlDisplay.add(btnDisplay);
		
		btnDStop = new JButton("Stop");
		btnDStop.setBounds(135, 226, 75, 23);
		pnlDisplay.add(btnDStop);
		
		pnlMove = new JPanel();
		pnlMove.setBounds(10,  19,  200,  200);
		Border b21 = BorderFactory.createLineBorder(Color.black);
		pnlMove.setBorder(b21);
		pnlDisplay.add(pnlMove);
		pnlMove.add(lblText);
		// Then add this to main window
		frame.add(pnlDisplay);
		
		// The moving graphics outer panel
		JPanel pnlTriangle = new JPanel();
		Border b3 = BorderFactory.createTitledBorder("Triangle Thread");
		pnlTriangle.setBorder(b3);
		pnlTriangle.setBounds(240, 12, 222, 269);
		pnlTriangle.setLayout(null);
		
		// Add buttons and drawing panel to this panel
		btnTriangle = new JButton("Start Rotate");
		btnTriangle.setBounds(10, 226, 121, 23);
		pnlTriangle.add(btnTriangle);
		
		
		
		btnTStop = new JButton("Stop");
		btnTStop.setBounds(135, 226, 75, 23);
		pnlTriangle.add(btnTStop);
		
		pnlRotate = new JPanel();
		pnlRotate.setBounds(10,  19,  200,  200);
		Border b31 = BorderFactory.createLineBorder(Color.black);
		pnlRotate.setBorder(b31);
		pnlTriangle.add(pnlRotate);
		
		BufferedImage myPicture = ImageIO.read(new File("C:/Users/Oscar/Desktop/pic.png"));
		picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setLocation(0,0);
		pnlRotate.add(picLabel);
		
		// Add this to main window
	
		frame.add(pnlTriangle);
	}
	
	/**
	 * Metod som startar tråd för att flytta texten när man klickar på knapp
	 */
	public void buttonListeners(){
		btnDisplay.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				display = new DisplayTextThread(lblText);
				display.start();
			}
		});
		
		/**
		 * Metod som stoppar tråd när man klickar på knappen
		 */
		btnDStop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				display.stopRunning();
			}
		});
		
		/**
		 * Metod som startar tråd för att flytta bilden när man klickar på knapp
		 */
		btnTriangle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				rotateImage = new RotateImage(picLabel);
				rotateImage.start();
			}
		});
		
		//Metod som stoppar tråd
		btnTStop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rotateImage.stopRunning();
			}
		});
		
	}

}
