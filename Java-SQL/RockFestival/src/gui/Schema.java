package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller;
import entities.Band;
import entities.Stage;

public class Schema extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller =  new Controller();
	private JPanel pnlSchema = new JPanel();
	private static JPanel pnlShowSchema = new JPanel();
	private JLabel lblSelectDay = new JLabel("Välj dag");
	private JLabel lblSelectStage = new JLabel("Välj scen");
	private JComboBox<String> cbDay = new JComboBox<String>(); 
	private JComboBox<Stage> cbStage = new JComboBox<Stage>(); 
	private JButton btnShow = new JButton("Visa schema");
	
	private static JLabel lblTime = new JLabel();
	private static JLabel lblName = new JLabel();
	private static JLabel lblGenre = new JLabel();
	
	public Schema(){
        this.setPreferredSize(new Dimension(1000, 800));
        initElements();
        add(pnlSchema, BorderLayout.WEST);
        add(pnlShowSchema, BorderLayout.EAST);
	}

	
	private void initElements() {
		pnlSchema.setPreferredSize(new Dimension(490, 690));
		pnlSchema.setLayout(new FlowLayout());
		pnlSchema.add(lblSelectDay);
		pnlSchema.add(cbDay);
		pnlSchema.add(lblSelectStage);
		pnlSchema.add(cbStage);
		pnlSchema.add(btnShow);
		lblSelectDay.setPreferredSize(new Dimension(150, 20));
		lblSelectStage.setPreferredSize(new Dimension(150, 20));
		cbDay.setPreferredSize(new Dimension(300, 25));
		cbStage.setPreferredSize(new Dimension(300, 25));
		pnlShowSchema.setPreferredSize(new Dimension(490, 690));
		cbDay.addItem("Fredag");
		cbDay.addItem("Lördag");
		cbDay.addItem("Söndag");
		
		pnlShowSchema.setPreferredSize(new Dimension(490, 690));
		pnlShowSchema.setLayout(new FlowLayout());
		pnlShowSchema.add(lblTime);
		pnlShowSchema.add(lblName);
		pnlShowSchema.add(lblGenre);
		lblTime.setPreferredSize(new Dimension(450, 20));
		lblName.setPreferredSize(new Dimension(450, 20));
		lblGenre.setPreferredSize(new Dimension(450, 20));
		
		pnlSchema.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlShowSchema.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		populateCBStage();
		btnShow.addActionListener(actionListener);
		
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
    
    public static void test(List<String>myList){
    	for(int i=0; i<myList.size(); i++){
	    	JLabel _lbl = new JLabel(myList.get(i));
	    	pnlShowSchema.add(_lbl);
	    	_lbl.setPreferredSize(new Dimension(450, 20));
	    	pnlShowSchema.revalidate();
	    	pnlShowSchema.repaint();
    	}
    }
    
    
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) { 
        	if(e.getSource() == btnShow){
        		try {
        			pnlShowSchema.removeAll();
        			pnlShowSchema.updateUI();
        			controller.getSchema(cbDay.getSelectedItem().toString().toLowerCase(), ((Stage) (cbStage.getSelectedItem())).getId());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	
        }
    };
}
