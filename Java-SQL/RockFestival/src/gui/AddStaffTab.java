package gui;
import controller.Controller;
import entities.Band;
import entities.Stage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AddStaffTab extends JPanel{
	private Controller controller =  new Controller();
	private JLabel lblName = new JLabel("Namn");
	private JLabel lblBand = new JLabel("Band");
	private JLabel lblPnr = new JLabel("Personnummer");
	private JLabel lblPhoneNbr = new JLabel("Telefonnummer");
	private JLabel lblStartTime = new JLabel("Starttid");
	private JLabel lblEndTime = new JLabel("Sluttid");
	private JLabel lblDate = new JLabel("Dag");
	private JLabel lblSecurity = new JLabel("Säkerhetspersonal");
	private JLabel lblContact = new JLabel("Kontaktperson");
	private JLabel lblStage = new JLabel("Scen");
	
	private JTextField tfName = new JTextField();
	private JTextField tfPhoneNbr = new JTextField();
	private JTextField tfPnr = new JTextField();
	private JTextField tfStartTime= new JTextField();
	private JTextField tfEndTime = new JTextField();
	private JTextField tfDate = new JTextField();
	
	private JPanel pnlStaff = new JPanel();
	private JPanel pnlSecurity = new JPanel();
	private JPanel pnlContact = new JPanel();
	
	private JComboBox<Stage> cbStage = new JComboBox<Stage>();
	private JComboBox<Band> cbBand = new JComboBox<Band>();
	
	private JButton btnAdd = new JButton("Add");
	
	private JRadioButton rbSecurity = new JRadioButton();
	private JRadioButton rbContact = new JRadioButton();
	
	
	public AddStaffTab(){
		//Overall settings for panels
		setSize(new Dimension(1000,800));
		add(pnlStaff, BorderLayout.WEST);
		add(pnlSecurity, BorderLayout.CENTER);
		add(pnlContact, BorderLayout.EAST);
		pnlStaff.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlSecurity.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlContact.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlStaff.setPreferredSize(new Dimension(330,590));
		pnlSecurity.setPreferredSize(new Dimension(330,590));
		pnlContact.setPreferredSize(new Dimension(330,590));
		ButtonGroup group = new ButtonGroup();
		group.add(rbSecurity);
		group.add(rbContact);
	
		//Settings for pnlStaff
		pnlStaff.add(lblName);
		pnlStaff.add(tfName);
		pnlStaff.add(lblPnr);
		pnlStaff.add(tfPnr);
		pnlStaff.add(lblPhoneNbr);
		pnlStaff.add(tfPhoneNbr);
		pnlStaff.add(btnAdd);
		lblName.setPreferredSize(new Dimension(100, 25));
		tfName.setPreferredSize(new Dimension(200, 25));
		lblPnr.setPreferredSize(new Dimension(100, 25));
		tfPnr.setPreferredSize(new Dimension(200, 25));
		lblPhoneNbr.setPreferredSize(new Dimension(100, 25));
		tfPhoneNbr.setPreferredSize(new Dimension(200, 25));
		btnAdd.setPreferredSize(new Dimension(200, 25));
		btnAdd.addActionListener(actionListener);
		
		//Settings for pnlSecurity
		pnlSecurity.add(lblSecurity);
		pnlSecurity.add(rbSecurity);
		pnlSecurity.add(lblStage);
		pnlSecurity.add(cbStage);
		pnlSecurity.add(lblStartTime);
		pnlSecurity.add(lblEndTime);
		pnlSecurity.add(tfStartTime);
		pnlSecurity.add(tfEndTime);
		pnlSecurity.add(lblDate);
		pnlSecurity.add(tfDate);

		lblSecurity.setPreferredSize(new Dimension(280, 25));
		lblStage.setPreferredSize(new Dimension(300, 25));
		cbStage.setPreferredSize(new Dimension(300, 25));
		lblStartTime.setPreferredSize(new Dimension(150, 25));
		lblEndTime.setPreferredSize(new Dimension(150, 25));
		tfStartTime.setPreferredSize(new Dimension(150, 25));
		tfEndTime.setPreferredSize(new Dimension(150, 25));
		lblDate.setPreferredSize(new Dimension(300, 25));
		tfDate.setPreferredSize(new Dimension(300, 25));
		
		rbSecurity.addActionListener(actionListener);
		tfStartTime.setEditable(false);
		tfEndTime.setEditable(false);
		tfDate.setEditable(false);
		cbStage.setEditable(false);
		
		//Settings for pnlContact
		pnlContact.add(lblContact);
		pnlContact.add(rbContact);
		pnlContact.add(lblBand);
		pnlContact.add(cbBand);
		
		lblContact.setPreferredSize(new Dimension(280, 25));
		lblBand.setPreferredSize(new Dimension(300, 25));
		cbBand.setPreferredSize(new Dimension(300, 25));
	
		rbContact.addActionListener(actionListener);
		cbBand.setEditable(false);
		populateCBStage();
		populateCBBand();
	}
	
	private void populateCBBand() {
		List<Band> bandList;
		try {
			bandList = controller.getBands();
			for (Band band : bandList) {
				cbBand.addItem(band);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
    private void populateCBStage() {
        try {
            List<Stage> stages = controller.getStages();
            for (Stage s : stages) {
                cbStage.addItem(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	//ActionListener for checkboxes 
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {    	
        	if(e.getSource() == btnAdd){
        		if (rbContact.isSelected()== true){
        			try {
						controller.insertContactPerson(tfName.getText(), tfPhoneNbr.getText(), tfPnr.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			System.out.println("Skicka till kontakter dropdown");
        		}else{
        			System.out.println("Skicka till säkerhet dropdown");
        		}
        	}
        	
        	
          if (rbContact.isSelected()== true){
        	  cbBand.setEditable(true);
          }else{
        	  cbBand.setEditable(false);
          }
          if(rbSecurity.isSelected() == true){
        	  tfStartTime.setEditable(true);
        	  tfEndTime.setEditable(true);
        	  tfDate.setEditable(true);
        	  cbStage.setEditable(true); 
          }else{
        	  tfStartTime.setEditable(false);
        	  tfEndTime.setEditable(false);
        	  tfDate.setEditable(false);
        	  cbStage.setEditable(false);
          }
        }
    };
}
