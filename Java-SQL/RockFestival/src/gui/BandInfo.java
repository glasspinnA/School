package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Controller;
import entities.Band;

public class BandInfo extends JPanel {
	private Controller controller = new Controller();
	private JPanel pnlList = new JPanel();
	private JPanel pnlInfo = new JPanel();
	private JPanel pnlBio = new JPanel();
	private Font lblFont = new Font("Comic Sans MS", Font.BOLD, 20);

	private JList<Object> listBand;
	private JLabel lblBand = createLabel("  Choose Band: ");
	private JLabel lblName = createLabel("  Band name: ");
	private JLabel lblGenre = createLabel("  Genre: ");
	private JLabel lblBio = new JLabel("Biography");
	private JLabel lblCountry = createLabel("  Country: ");
	private JLabel lblUpdateCountry = createUpdateLabel();
	private JLabel lblUpdatename = createUpdateLabel();
	private JLabel lblUpdateGenre = createUpdateLabel();

	private JTextArea taBio = new JTextArea();
	
	public BandInfo(){
		try {
			listBand =  new JList<Object>(controller.getBands().toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		setPreferredSize(new Dimension(1000,800));
		setBackground(Color.BLACK);
		initElems();
		
		listBand.addListSelectionListener(new ListSelectionListener(){
			@Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                	Band band = (Band)listBand.getSelectedValue();
                	lblUpdatename.setText(band.getName());
                	lblUpdateCountry.setText(band.getCountry());
                	try {
						lblUpdateGenre.setText(band.getGenreAsText());
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
                	taBio.setText(band.getBio());
                	
                }
            }
        });
		
		add(pnlList);
		add(pnlInfo);
	}
	
	public void initElems(){
		pnlList.setPreferredSize(new Dimension(300,700));
		pnlList.setBackground(Color.BLACK);
		pnlList.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		listBand.setPreferredSize(new Dimension(250,500));
		pnlList.add(lblBand);
		pnlList.add(listBand);
		
		pnlInfo.setPreferredSize(new Dimension(600,700));
		pnlInfo.setBackground(Color.BLACK);
		pnlInfo.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		lblBio.setPreferredSize(new Dimension(500,40));
		lblBio.setForeground(Color.WHITE);
		lblBio.setFont(lblFont);
		pnlInfo.add(lblName);
		pnlInfo.add(lblUpdatename);
		pnlInfo.add(lblGenre);
		pnlInfo.add(lblUpdateGenre);
		pnlInfo.add(lblCountry);
		pnlInfo.add(lblUpdateCountry);
		pnlInfo.add(lblBio);
		taBio.setPreferredSize(new Dimension(300,300));
		taBio.setEditable(false);
		pnlInfo.add(taBio);
		
		
		
	}
	
	public void setLabelText(){
		Band band = (Band)listBand.getSelectedValue();
		
		lblName.setText(band.getName());
		lblCountry.setText(band.getCountry());
	}
	

	
	
	private JLabel createLabel(String name){
		JLabel lbl = new JLabel();
		Dimension size = new Dimension(170,40);
		lbl.setPreferredSize(size);
		lbl.setForeground(Color.WHITE);
		lbl.setText(name);
		lbl.setFont(lblFont);
		return lbl;
	}
	
	private JLabel createUpdateLabel(){
		JLabel lbl = new JLabel();
		Dimension size = new Dimension(370,25);
		lbl.setPreferredSize(size);
		lbl.setForeground(Color.CYAN);
		lbl.setFont(lblFont);
		return lbl;
	}
	
	
}
