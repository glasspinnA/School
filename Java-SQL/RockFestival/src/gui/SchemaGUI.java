package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.MouseListener;

import javax.swing.*;

public class SchemaGUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Dimension lblSize = new Dimension(290,30);
	
	private JTabbedPane tab = new JTabbedPane();
	private JPanel pnlThurs = new JPanel();
	private JPanel pnlFri = new JPanel();
	private JPanel pnlSat = new JPanel();
	private JPanel pnlStage1Thurs = createPanel("Mallorcascenen");
	private JScrollPane scrollStage1Thurs = createScrollPane(pnlStage1Thurs);
	private JPanel pnlStage2Thurs = createPanel("Forumscenen");
	private JScrollPane scrollStage2Thurs = createScrollPane(pnlStage2Thurs);
	private JPanel pnlStage3Thurs = createPanel("Dieseltältet");
	private JScrollPane scrollStage3Thurs = createScrollPane(pnlStage3Thurs);
	private JPanel pnlStage1Fri = createPanel("Mallorcascenen");
	private JScrollPane scrollStage1Fri = createScrollPane(pnlStage1Fri);
	private JPanel pnlStage2Fri = createPanel("Forumscenen");
	private JScrollPane scrollStage2Fri = createScrollPane(pnlStage2Fri);
	private JPanel pnlStage3Fri = createPanel("Dieseltältet");
	private JScrollPane scrollStage3Fri = createScrollPane(pnlStage3Fri);
	private JPanel pnlStage1Sat = createPanel("Mallorcascenen");
	private JScrollPane scrollStage1Sat = createScrollPane(pnlStage1Sat);
	private JPanel pnlStage2Sat = createPanel("Forumscenen");
	private JScrollPane scrollStage2Sat = createScrollPane(pnlStage2Sat);
	private JPanel pnlStage3Sat = createPanel("Dieseltältet");
	private JScrollPane scrollStage3Sat = createScrollPane(pnlStage3Sat);

	
	public SchemaGUI(){
		setPreferredSize(new Dimension(1000,800));
		setBackground(Color.BLACK);
		setLayout(new FlowLayout());
		initElems();
		
		
	}
	
	private JPanel createPanel(String stageName) {
        JPanel pnl = new JPanel();
        pnl.setLayout(new FlowLayout());
        pnl.setPreferredSize(new Dimension(310, 2400));
        pnl.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        pnl.setBackground(Color.BLACK);
        JLabel lbl = createDayLabel(lblSize, stageName);
        pnl.add(lbl);
        return pnl;
    }



    private JScrollPane createScrollPane(JPanel panel) {
        JScrollPane pnl = new JScrollPane(panel);
        pnl.setPreferredSize(new Dimension(310, 760));
        pnl.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        pnl.setOpaque(false);
        pnl.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        return pnl;
    }
	
	public void initElems(){
		

		
		pnlThurs.setPreferredSize(new Dimension(1000,800));
		pnlThurs.setBackground(Color.BLACK);
		pnlThurs.setLayout(new FlowLayout());
		pnlThurs.add(scrollStage1Thurs);
		pnlThurs.add(scrollStage2Thurs);
		pnlThurs.add(scrollStage3Thurs);
		tab.addTab("Thursday", pnlThurs );
		
		pnlFri.setPreferredSize(new Dimension(1000,800));
		pnlFri.setBackground(Color.BLACK);
		pnlFri.setLayout(new FlowLayout());
		pnlFri.add(scrollStage1Fri);
		pnlFri.add(scrollStage2Fri);
		pnlFri.add(scrollStage3Fri);
		tab.addTab("Friday", pnlFri );
		
		pnlStage2Fri.add(testGig());
		pnlStage2Fri.add(testGig());
		pnlStage2Fri.add(testGig());
		pnlStage2Fri.add(testGig());
		pnlStage2Fri.add(testGig());
		pnlStage2Fri.add(testGig());
		pnlStage2Fri.add(testGig());
		pnlStage2Fri.add(testGig());
		pnlStage2Fri.add(testGig());
		pnlStage2Fri.add(testGig());
		
		pnlSat.setPreferredSize(new Dimension(1000,800));
		pnlSat.setBackground(Color.BLACK);
		pnlSat.setLayout(new FlowLayout());
		pnlSat.add(scrollStage1Sat);
		pnlSat.add(scrollStage2Sat);
		pnlSat.add(scrollStage3Sat);
		tab.addTab("Saturday", pnlSat );
		
		
		add(tab);
		
		
	}
	

	public JLabel createDayLabel(Dimension size, String name){
		JLabel lbl = new JLabel();
		lbl.setPreferredSize(size);
		lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY,2));
		lbl.setForeground(Color.WHITE);
		lbl.setText(name);
		return lbl;
	}
	
	
	// TODO: endast för testsyfte, ska tas bort
	public JLabel createTestLabel(Dimension size, String name){
		JLabel lbl = new JLabel();
		lbl.setPreferredSize(size);
		lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY,2));
		lbl.setForeground(Color.WHITE);
		lbl.setText(name);
		return lbl;
	}
	
	// TODO: endast för testsyfte, ska tas bort
	public JPanel testGig(){
		Dimension lblTestSise = new Dimension(220,18);
		JPanel test = new JPanel();
		test.setPreferredSize(new Dimension(250,100));;
		test.setBorder(BorderFactory.createLineBorder(Color.CYAN,2));
		test.setBackground(Color.BLACK);
		
		JLabel name = createTestLabel(lblTestSise,"Band: Jontes pågar");
		JLabel time = createTestLabel(lblTestSise,"18:00-19:00");
		JLabel stage = createTestLabel(lblTestSise,"Scen: Mallorcascenen");
		JLabel genre = createTestLabel(lblTestSise,"Genre: Reggae");
		test.add(time);
		test.add(stage);
		test.add(name);
		test.add(genre);
		return test;
	}
	
	public void createInfoFrame(){
		JFrame bandInfo = new JFrame();
		bandInfo.setLocationRelativeTo(this);
		Dimension lblTestSise = new Dimension(300,18);
		JLabel lblName = createTestLabel(lblTestSise,"Band: Jontes pågar");
		JLabel lblGenre = createTestLabel(lblTestSise,"Genre: Reggae");
		JLabel lblBio = createTestLabel(lblTestSise,"Biography:");
		JTextArea taBio = new JTextArea();
		taBio.setPreferredSize(new Dimension(350,200));
		taBio.setEditable(false);
		bandInfo.add(lblName);
		bandInfo.add(lblGenre);
		bandInfo.add(lblBio);
		bandInfo.add(taBio);
		bandInfo.pack();
		bandInfo.setVisible(true);

		




		
	}
	
}
