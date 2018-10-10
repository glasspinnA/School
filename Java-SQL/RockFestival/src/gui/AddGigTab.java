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
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddGigTab extends JPanel {
	private Controller controller =  new Controller();
    private JLabel lblStartTime = new JLabel("Starttid");
    private JLabel lblEndTime = new JLabel("Sluttid");

	private static final long serialVersionUID = 1L;
    private JLabel lblDate = new JLabel("Dag");
    private JComboBox<String> cbDay = new JComboBox<String>(); 
    private JTextField tfEndTime = new JTextField("End");
    private JTextField tfStartTime = new JTextField("hh:mm");

    private JLabel lblStage = new JLabel("Välj en schen");
    private JLabel lblChooseBand = new JLabel("Välj ett band");
    private JButton btnChoose = new JButton("Välj");
    private JComboBox<Stage> cbStage = new JComboBox<Stage>();
    private JComboBox<Band> cbChooseBand = new JComboBox<Band>();

    private JPanel pnlStage = new JPanel();
    private JPanel pnlDate = new JPanel();

    public AddGigTab() {
        this.setPreferredSize(new Dimension(1000, 800));
        initElements();
        add(pnlStage, BorderLayout.WEST);
        add(pnlDate, BorderLayout.EAST);
    }

    private void initElements() {
        pnlStage.setPreferredSize(new Dimension(490, 790));
        pnlStage.setLayout(new FlowLayout());
        pnlStage.add(lblStage);
        pnlStage.add(cbStage);
        pnlStage.add(lblChooseBand);
        pnlStage.add(cbChooseBand);
        pnlStage.add(btnChoose);
        lblStage.setPreferredSize(new Dimension(150, 30));
        cbStage.setPreferredSize(new Dimension(320, 20));
        lblChooseBand.setPreferredSize(new Dimension(150, 30));
        cbChooseBand.setPreferredSize(new Dimension(320, 20));

        pnlDate.setPreferredSize(new Dimension(490, 790));
        pnlDate.setLayout(new FlowLayout());
        pnlDate.add(lblDate);
        pnlDate.add(cbDay);
        pnlDate.add(lblStartTime);
        pnlDate.add(tfStartTime);
        pnlDate.add(lblEndTime);
        pnlDate.add(tfEndTime);
		cbDay.addItem("Fredag");
		cbDay.addItem("Lördag");
		cbDay.addItem("Söndag");
        lblDate.setPreferredSize(new Dimension(150, 20));
        lblStartTime.setPreferredSize(new Dimension(150, 20));
        cbDay.setPreferredSize(new Dimension(300, 25));
        lblEndTime.setPreferredSize(new Dimension(150, 20));
        tfEndTime.setPreferredSize(new Dimension(300, 25));
        tfStartTime.setPreferredSize(new Dimension(300, 25));

        btnChoose.addActionListener(actionListener);
        
        pnlStage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnlDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        populateCBBand();
        populateCBStage();
    }
    
	private void populateCBBand() {
		List<Band> bandList;
		try {
			bandList = controller.getBands();
			for (Band band : bandList) {
				cbChooseBand.addItem(band);
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
    
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {    	
        	if(e.getSource() == btnChoose){
        		int idprovider = ((Band) cbChooseBand.getSelectedItem()).getId();
        		try {
					controller.addGig(cbDay.getSelectedItem().toString(), tfStartTime.getText(), tfEndTime.getText(), idprovider, ((Stage) (cbStage.getSelectedItem())).getId());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        }
    };
}
