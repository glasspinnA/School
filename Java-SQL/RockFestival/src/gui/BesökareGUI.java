package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import controller.Controller;

public class BesökareGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTabbedPane tabs = new JTabbedPane();
	private JLabel logo = new JLabel();
	private BandInfo bandInfoTab = new BandInfo();
	private Schema schema = new Schema();

	public BesökareGUI(Controller controller) {

		setPreferredSize(new Dimension(1005, 845));
		setBackground(Color.BLACK);
		tabs.add("Band info", bandInfoTab);
		tabs.add("Schema", schema);
		createLogo();
		add(logo);
		add(tabs);
	}
	
	public void createLogo(){
		logo.setPreferredSize(new Dimension(1000,100));
		logo.setForeground(Color.WHITE);
		Font logoFont = new Font("Comic Sans MS", Font.BOLD, 40);
		logo.setText("Blomstermåla Rockfestival");
		logo.setFont(logoFont);
	}

	public static void main(String[] args) {
		Controller controller = new Controller();
		BesökareGUI gui = new BesökareGUI(controller);
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Rock Festival | KansliApplikation");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(gui);
				frame.pack();
				frame.setVisible(true);
	}
		});

}
}
