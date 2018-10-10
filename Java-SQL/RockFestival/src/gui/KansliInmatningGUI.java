package gui;

import controller.Controller;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class KansliInmatningGUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTabbedPane tabs = new JTabbedPane();

	private AddBandTab bandTab;
	private AddGigTab gigTab = new AddGigTab();
	private AddStaffTab staffTab = new AddStaffTab();
	
	private Schema schema = new Schema();

	public KansliInmatningGUI(Controller controller) {
        this.bandTab = new AddBandTab(controller);
		setPreferredSize(new Dimension(1105, 905));
		setBackground(Color.BLACK);
		tabs.add("Add band", bandTab);
		tabs.add("Add gig", gigTab);
		tabs.add("Add Staff", staffTab);
		tabs.add("Schema", schema);
		add(tabs);
	}
}
