package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;

import controller.Controller;
import entities.Band;
import entities.BandMedlem;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Test extends JPanel {
	private JTextField fldRoll;
	private Controller controller;
	private JComboBox<Band> cbBand;
	private JComboBox<BandMedlem> cbBandMember;
	private JLabel lblRoll;

	/**
	 * Create the panel.
	 */
	public Test() {
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 30, 30, 30, 30, 30 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		cbBand = new JComboBox<Band>();
		GridBagConstraints gbc_cbBand = new GridBagConstraints();
		gbc_cbBand.insets = new Insets(0, 0, 5, 0);
		gbc_cbBand.gridwidth = 6;
		gbc_cbBand.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbBand.gridx = 0;
		gbc_cbBand.gridy = 0;
		add(cbBand, gbc_cbBand);

		cbBandMember = new JComboBox<BandMedlem>();
		cbBandMember.setEditable(true);
		GridBagConstraints gbc_cbBandMember = new GridBagConstraints();
		gbc_cbBandMember.insets = new Insets(0, 0, 5, 0);
		gbc_cbBandMember.gridwidth = 6;
		gbc_cbBandMember.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbBandMember.gridx = 0;
		gbc_cbBandMember.gridy = 1;
		add(cbBandMember, gbc_cbBandMember);

		lblRoll = new JLabel("Roll:");
		lblRoll.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		GridBagConstraints gbc_lblRoll = new GridBagConstraints();
		gbc_lblRoll.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoll.gridx = 0;
		gbc_lblRoll.gridy = 2;
		add(lblRoll, gbc_lblRoll);

		fldRoll = new JTextField();
		GridBagConstraints gbc_fldRoll = new GridBagConstraints();
		gbc_fldRoll.insets = new Insets(0, 0, 5, 0);
		gbc_fldRoll.gridwidth = 5;
		gbc_fldRoll.fill = GridBagConstraints.HORIZONTAL;
		gbc_fldRoll.gridx = 1;
		gbc_fldRoll.gridy = 2;
		add(fldRoll, gbc_fldRoll);
		fldRoll.setColumns(10);

		JButton btnSkapaBandmedlemm = new JButton("Skapa Bandmedlemm");
		btnSkapaBandmedlemm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO : Skapa en BandMedlem
				String name = cbBandMember.getSelectedItem().toString();
				String role = fldRoll.getText().toString();
				int bandId = ((Band)cbBand.getSelectedItem()).getId();
				try {
					controller.insertBandMedlem(name, bandId, role);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnSkapaBandmedlemm = new GridBagConstraints();
		gbc_btnSkapaBandmedlemm.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSkapaBandmedlemm.gridwidth = 4;
		gbc_btnSkapaBandmedlemm.insets = new Insets(0, 0, 5, 5);
		gbc_btnSkapaBandmedlemm.gridx = 0;
		gbc_btnSkapaBandmedlemm.gridy = 3;
		add(btnSkapaBandmedlemm, gbc_btnSkapaBandmedlemm);
		
		JButton btnNewButton_1 = new JButton("Ta bort bandmedlem");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.removeBandMemberFromBand((BandMedlem)cbBandMember.getSelectedItem());
					populateCbMembers();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.gridwidth = 3;
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 3;
		add(btnNewButton_1, gbc_btnNewButton_1);
	}

	public Test(Controller controller) {
		this();
		this.controller = controller;
		try {
			populateCbBand();
			populateCbMembers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void populateCbBand() throws SQLException {
		List<Band> bandList = controller.getBands();
		for (Band band : bandList) {
			cbBand.addItem(band);
		}
		cbBand.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				populateCbMembers();
			}
		});
	}

	private void populateCbMembers() {
		List<BandMedlem> list;
		try {
			list = controller.getBandMembersFromBand((Band) cbBand.getSelectedItem());
			// Tar bort alla element ur comboboxen
			cbBandMember.removeAllItems();
			for (BandMedlem bandMedlem : list) {
				cbBandMember.addItem(bandMedlem);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Controller controller = new Controller();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.getContentPane().add(new Test(controller));
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

}
