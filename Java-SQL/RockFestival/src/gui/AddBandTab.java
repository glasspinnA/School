package gui;

import controller.Controller;
import entities.Band;
import entities.Genre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddBandTab extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlBand = new JPanel();
	private Controller controller;
	private Dimension tfSize = new Dimension(300, 20);
	private Dimension lblSize = new Dimension(150, 30);
	private JTextField tfBandname = createTextField(tfSize);
	private JLabel lblBandname = createLabel(lblSize, "Band name: ");
	private JTextArea taBio = new JTextArea();
	private JLabel lblBio = new JLabel("  Band biography: ");
	private JComboBox<Genre> cbGenre = createComboBox(tfSize);
	private JLabel lblGenre = createLabel(lblSize, "Choose genre: ");
	private JComboBox<String> cbCountries = createComboBox(tfSize);
	private JLabel lblCountries = createLabel(lblSize, "Choose country: ");
	private JComboBox<String> cbContact = createComboBox(tfSize);
	private JLabel lblContact = createLabel(lblSize, "Choose a contact person: ");
	private JButton btnAddBand = new JButton("Add band");

	private JPanel pnlBandMember;

	private JComboBox<Band> cbBand = createComboBox(tfSize);
	

	private ImageIcon background = new ImageIcon("files/bandbackground.jpg");

	private ButtonListener btnListener = new ButtonListener();

	public AddBandTab(Controller controller) {
		this.controller = controller;
		setPreferredSize(new Dimension(1000, 800));
		setLayout(new FlowLayout());
		pnlBandMember = new Test(controller);
		pnlBand.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		pnlBand.setOpaque(false);
		pnlBandMember.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		pnlBandMember.setOpaque(false);
		add(pnlBand, BorderLayout.WEST);
		add(pnlBandMember, BorderLayout.EAST);
		initElems();
	}

	public JTextField createTextField(Dimension size) {
		JTextField txtField = new JTextField();
		txtField.setPreferredSize(size);
		return txtField;

	}

	public <T> JComboBox<T> createComboBox(Dimension size) {
		JComboBox<T> comboBox = new JComboBox<T>();
		comboBox.setPreferredSize(size);
		return comboBox;
	}

	public JLabel createLabel(Dimension size, String name) {
		JLabel lbl = new JLabel();
		lbl.setPreferredSize(size);
		lbl.setForeground(Color.WHITE);
		lbl.setText(name);
		return lbl;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);

	}

	public void initElems() {
		pnlBand.setPreferredSize(new Dimension(490, 760));
		pnlBand.setLayout(new FlowLayout());
		pnlBand.add(lblBandname);
		pnlBand.add(tfBandname);
		lblBio.setPreferredSize(new Dimension(470, 30));
		lblBio.setForeground(Color.WHITE);
		pnlBand.add(lblBio);
		taBio.setPreferredSize(new Dimension(400, 300));
		taBio.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		pnlBand.add(taBio);
		pnlBand.add(lblGenre);
		cbGenre.setSize(tfSize);
		pnlBand.add(cbGenre);
		pnlBand.add(lblCountries);
		pnlBand.add(cbCountries);
		pnlBand.add(lblContact);
		pnlBand.add(cbContact);
		btnAddBand.setPreferredSize(tfSize);
		pnlBand.add(btnAddBand);

		
		pnlBandMember.setPreferredSize(new Dimension(490, 760));
		
		 btnAddBand.addActionListener(this.btnListener);
		 
		 populateCBContact();
		 populateCBCountries();
		 populateCBGenre();

	}

	private void populateCBCountries() {
		try {
			List<String> countries = controller.getCountries();
			for (String c : countries) {
				cbCountries.addItem(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void populateCBContact() {
		try {
			List<String> persons = controller.getPersons();
			for (String p : persons) {
				cbContact.addItem(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void populateCBGenre() {
		List<Genre> genreList;
		try {
			genreList = controller.getGenres();
			for (Genre genre : genreList) {
				cbGenre.addItem(genre);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAddBand) {
				try {
					controller.insertBand(tfBandname.getText(), taBio.getText(),
							((Genre) cbGenre.getSelectedItem()).getId(), cbCountries.getSelectedItem().toString(),
							cbContact.getSelectedItem().toString());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}
