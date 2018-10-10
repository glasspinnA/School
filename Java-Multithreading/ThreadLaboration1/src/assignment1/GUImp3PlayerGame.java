
package assignment1;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import javax.swing.border.*;

import ThreadTestJava.Main;

/**
 * The GUI for assignment 1
 */
public class GUImp3PlayerGame
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JFrame frame;		// The Main window
	private JButton btnOpen;	// Open sound file button
	private JButton btnPlay;	// Play selected file button
	private JButton btnStop;	// Stop music button
	private JButton btnGo;	    // Start game
	private JTextField txtHits;	// Display hits
	private JComboBox cbSkill;
	private JLabel lblPlaying;	// Hidden, shown after start of music
	private JLabel lblPlayURL;	// The sound file path
	private JLabel lblSkill;	// Static labels
	private JLabel lblInfo;
	private JLabel lblInfo2;
	private JLabel lblHits;
	private JLabel lblResult;
	private JPanel pnlGame;		// The panel for game
	private String[] skills;

	/**
	 * Constructor
	 */
	public GUImp3PlayerGame()
	{
		skills = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	}
	
	/**
	 * Starts the application
	 */
	public void Start()
	{
		frame = new JFrame();
		frame.setBounds(0, 0, 560, 602);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Multiple Thread Demonstrator");
		InitializeGUI();
		buttonListener(); // Fill in components
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen
	}
	
	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{
		// The play panel
		JPanel pnlSound = new JPanel();
		Border b1 = BorderFactory.createTitledBorder("Music Player");
		pnlSound.setBorder(b1);
		pnlSound.setBounds(12, 12, 523, 100);
		pnlSound.setLayout(null);
		
		// Add the buttons and labels to this panel
		btnOpen = new JButton("Open");
		btnOpen.setBounds(6, 71, 75, 23);
		pnlSound.add(btnOpen);
		
		btnPlay = new JButton("Play");
		btnPlay.setBounds(88, 71, 75, 23);
		pnlSound.add(btnPlay);
		
		btnStop = new JButton("Stop");
		btnStop.setBounds(169, 71, 75, 23);
		pnlSound.add(btnStop);
		
		lblPlaying = new JLabel("Now Playing...",JLabel.CENTER);
		lblPlaying.setFont(new Font("Serif", Font.BOLD, 20));
		lblPlaying.setBounds(128, 16, 120, 30);
		pnlSound.add(lblPlaying);
		
		lblPlayURL = new JLabel("Music url goes here");
		lblPlayURL.setBounds(10, 44, 115, 13);
		pnlSound.add(lblPlayURL);
		// Then add this to main window
		frame.add(pnlSound);
		
		// The game groupbox
		JPanel pnlDisplay = new JPanel();
		Border b2 = BorderFactory.createTitledBorder("Catch me");
		pnlDisplay.setBorder(b2);
		pnlDisplay.setBounds(100, 118, 435, 443);
		pnlDisplay.setLayout(null);
				
		pnlGame = new JPanel();
		pnlGame.setBounds(10,  19,  416,  416);
		Border b21 = BorderFactory.createLineBorder(Color.black);
		pnlGame.setBorder(b21);
		pnlDisplay.add(pnlGame);
		// Then add this to main window
		frame.add(pnlDisplay);
				
		// Add GO button
		btnGo = new JButton("GO");
		btnGo.setBounds(12, 285, 75, 54);
		frame.add(btnGo);
		
		// Add static labels 
		lblSkill = new JLabel("Skill:");
		lblSkill.setBounds(18, 132, 29, 13);
		frame.add(lblSkill);
		lblInfo = new JLabel("Hit images");
		lblInfo.setBounds(18, 356, 66, 13);
		frame.add(lblInfo);
		lblInfo2 = new JLabel("with mouse.");
		lblInfo2.setBounds(18, 371, 86, 13);
		frame.add(lblInfo2);
		lblHits = new JLabel("Hits:");
		lblHits.setBounds(18, 436, 29, 13);
		frame.add(lblHits);
		lblResult = new JLabel("Result:");
		lblResult.setBounds(18, 493, 78, 13);
		frame.add(lblResult);
		
		// Add the hit text field
		txtHits = new JTextField();
		txtHits.setBounds(18, 455, 53, 20);
		frame.add(txtHits);

		// Add the skill combo box
		cbSkill = new JComboBox(skills);
		cbSkill.setBounds(18, 151, 60, 21);
		cbSkill.setSelectedIndex(4);
		frame.add(cbSkill);
	}

	public void buttonListener(){
		btnPlay.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread t3 = new Thread(new mp3());
				t3.start();
			}
		});
	}
	
	private class mp3 implements Runnable{

		@Override
		public void run() {
			AudioClip click;
			String path = "‪C:/Users/Oscar/Downloads/tennis.wav";
		    URL urlClick = Main.class.getResource(path);
		    click = Applet.newAudioClip(urlClick);
		    click.play();
		}
	}
}
	
