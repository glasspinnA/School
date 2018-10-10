
package assignment3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The GUI for assignment 3
 */
public class GUIFoodSupply 
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 * Static controls are defined inline
	 */
	private JFrame frame;				// The Main window
	private JProgressBar bufferStatus;	// The progressbar, showing content in buffer
	
	// Data for Producer Scan
	private JButton btnStartS;			// Button start Scan
	private JButton btnStopS;			// Button stop Scan
	private JLabel lblStatusS;			// Status Scan
	// DAta for producer Arla
	private JButton btnStartA;			// Button start Arla
	private JButton btnStopA;			// Button stop Arla
	private JLabel lblStatusA;			// Status Arla
	//Data for producer AxFood
	private JButton btnStartX;			// Button start AxFood
	private JButton btnStopX;			// Button stop AxFood
	private JLabel lblStatusX;			// Status AxFood
	
	// Data for consumer ICA
	private JLabel lblIcaItems;			// Ica limits
	private JLabel lblIcaWeight;
	private JLabel lblIcaVolume;
	private JLabel lblIcaStatus;		// load status
	private JTextArea lstIca;			// The cargo list
	private JButton btnIcaStart;		// The buttons
	private JButton btnIcaStop;
	private JCheckBox chkIcaCont;		// Continue checkbox
	//Data for consumer COOP
	private JLabel lblCoopItems;
	private JLabel lblCoopWeight;
	private JLabel lblCoopVolume;
	private JLabel lblCoopStatus;		// load status
	private JTextArea lstCoop;			// The cargo list
	private JButton btnCoopStart;		// The buttons
	private JButton btnCoopStop;
	private JCheckBox chkCoopCont;		// Continue checkbox
	// Data for consumer CITY GROSS
	private JLabel lblCGItems;
	private JLabel lblCGWeight;
	private JLabel lblCGVolume;
	private JLabel lblCGStatus;			// load status
	private JTextArea lstCG;			// The cargo list
	private JButton btnCGStart;			// The buttons
	private JButton btnCGStop;
	private JCheckBox chkCGCont;		// Continue checkbox
	
	private Controller controller;
	private int value = 0;
	/**
	 * Constructor, creates the window
	 */
	public GUIFoodSupply()
	{
		
	}
	
	/**
	 * Starts the application
	 */
	public void Start(Controller controller)
	{
		frame = new JFrame();
		frame.setBounds(0, 0, 730, 526);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Food Supply System");
		InitializeGUI();					// Fill in components
		buttonListener();
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen
		this.controller = controller;
		controller.setValues();
	}
	
	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{
		// First create the three main panels
		JPanel pnlBuffer = new JPanel();
		pnlBuffer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Storage"));
		pnlBuffer.setBounds(13, 403, 693, 82);
		pnlBuffer.setLayout(null);
		// Then create the progressbar, only component in buffer panel
		bufferStatus = new JProgressBar();
		bufferStatus.setBounds(155, 37, 500, 23);
		bufferStatus.setBorder(BorderFactory.createLineBorder(Color.black));
		bufferStatus.setForeground(Color.GREEN);
		bufferStatus.setMaximum(20);
		pnlBuffer.add(bufferStatus);
		JLabel lblmax = new JLabel("Max capacity (items):");
		lblmax.setBounds(10, 42, 126,13);
		pnlBuffer.add(lblmax);
		frame.add(pnlBuffer);
		
		JPanel pnlProd = new JPanel();
		pnlProd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Producers"));
		pnlProd.setBounds(13, 13, 229, 379);
		pnlProd.setLayout(null);
		
		JPanel pnlCons = new JPanel();
		pnlCons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Consumers"));
		pnlCons.setBounds(266, 13, 440, 379);
		pnlCons.setLayout(null);
		
		// Now add the three panels to producer panel
		JPanel pnlScan = new JPanel();
		pnlScan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Producer: Scan"));
		pnlScan.setBounds(6, 19, 217, 100);
		pnlScan.setLayout(null);
		
		// Content Scan panel
		btnStartS = new JButton("Start Producing");
		btnStartS.setBounds(10, 59, 125, 23);
		pnlScan.add(btnStartS);
		btnStopS = new JButton("Stop");
		btnStopS.setBounds(140, 59, 65, 23);
		pnlScan.add(btnStopS);
		lblStatusS = new JLabel(" ");
		lblStatusS.setBounds(10, 31, 200, 13);
		pnlScan.add(lblStatusS);
		// Add Scan panel to producers		
		pnlProd.add(pnlScan);
		
		// The Arla panel
		JPanel pnlArla = new JPanel();
		pnlArla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Producer: Arla"));
		pnlArla.setBounds(6, 139, 217, 100);
		pnlArla.setLayout(null);
		
		// Content Arla panel
		btnStartA = new JButton("Start Producing");
		btnStartA.setBounds(10, 59, 125, 23);
		pnlArla.add(btnStartA);
		btnStopA = new JButton("Stop");
		btnStopA.setBounds(140, 59, 65, 23);
		pnlArla.add(btnStopA);
		lblStatusA = new JLabel(" ");
		lblStatusA.setBounds(10, 31, 200, 13);
		pnlArla.add(lblStatusA);
		// Add Arla panel to producers		
		pnlProd.add(pnlArla);
		
		// The AxFood Panel
		JPanel pnlAxfood = new JPanel();
		pnlAxfood.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Producer: AxFood"));
		pnlAxfood.setBounds(6, 262, 217, 100);
		pnlAxfood.setLayout(null);
		
		// Content AxFood Panel
		btnStartX = new JButton("Start Producing");
		btnStartX.setBounds(10, 59, 125, 23);
		pnlAxfood.add(btnStartX);
		btnStopX = new JButton("Stop");
		btnStopX.setBounds(140, 59, 65, 23);
		pnlAxfood.add(btnStopX);
		lblStatusX = new JLabel(" ");
		lblStatusX.setBounds(10, 31, 200, 13);
		pnlAxfood.add(lblStatusX);
		// Add Axfood panel to producers		
		pnlProd.add(pnlAxfood);
		// Producer panel done, add to frame		
		frame.add(pnlProd);
		
		// Next, add the three panels to Consumer panel
		JPanel pnlICA = new JPanel();
		pnlICA.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Consumer: ICA"));
		pnlICA.setBounds(19, 19,415, 100);
		pnlICA.setLayout(null);
		
		// Content ICA panel
		// First the limits panel
		JPanel pnlLim = new JPanel();
		pnlLim.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Package Limits"));
		pnlLim.setBounds(6, 19, 107, 75);
		pnlLim.setLayout(null);
		JLabel lblItems = new JLabel("Items:");
		lblItems.setBounds(7, 20, 50, 13);
		pnlLim.add(lblItems);
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(7, 35, 50, 13);
		pnlLim.add(lblWeight);
		JLabel lblVolume = new JLabel("Volume:");
		lblVolume.setBounds(7, 50, 50, 13);
		pnlLim.add(lblVolume);
		lblIcaItems = new JLabel("Data");
		lblIcaItems.setBounds(60, 20, 47, 13);
		pnlLim.add(lblIcaItems);
		lblIcaWeight = new JLabel("Data");
		lblIcaWeight.setBounds(60, 35, 47, 13);
		pnlLim.add(lblIcaWeight);
		lblIcaVolume = new JLabel("Data");
		lblIcaVolume.setBounds(60, 50, 47, 13);
		pnlLim.add(lblIcaVolume);
		pnlICA.add(pnlLim);
		// Then rest of controls
		lstIca = new JTextArea();
		lstIca.setEditable(false);
		JScrollPane spane = new JScrollPane(lstIca);		
		spane.setBounds(307, 16, 102, 69);
		spane.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlICA.add(spane);
		btnIcaStart = new JButton("Start Loading");
		btnIcaStart.setBounds(118, 64, 120, 23);
		pnlICA.add(btnIcaStart);
		btnIcaStop = new JButton("Stop");
		btnIcaStop.setBounds(240, 64, 60, 23);
		pnlICA.add(btnIcaStop);
		lblIcaStatus = new JLabel("");
		lblIcaStatus.setBounds(118, 16, 150, 23);
		pnlICA.add(lblIcaStatus);
		chkIcaCont = new JCheckBox("Continue load");
		chkIcaCont.setBounds(118, 39, 130, 17);
		pnlICA.add(chkIcaCont);
		// All done, add to consumers panel
		pnlCons.add(pnlICA);
		
		JPanel pnlCOOP = new JPanel();
		pnlCOOP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Consumer: COOP"));
		pnlCOOP.setBounds(19, 139, 415, 100);
		pnlCOOP.setLayout(null);
		pnlCons.add(pnlCOOP);
		
		// Content COOP panel
		// First the limits panel
		JPanel pnlLimC = new JPanel();
		pnlLimC.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Package Limits"));
		pnlLimC.setBounds(6, 19, 107, 75);
		pnlLimC.setLayout(null);
		JLabel lblItemsC = new JLabel("Items:");
		lblItemsC.setBounds(7, 20, 50, 13);
		pnlLimC.add(lblItemsC);
		JLabel lblWeightC = new JLabel("Weight:");
		lblWeightC.setBounds(7, 35, 50, 13);
		pnlLimC.add(lblWeightC);
		JLabel lblVolumeC = new JLabel("Volume:");
		lblVolumeC.setBounds(7, 50, 50, 13);
		pnlLimC.add(lblVolumeC);
		lblCoopItems = new JLabel("Data");
		lblCoopItems.setBounds(60, 20, 47, 13);
		pnlLimC.add(lblCoopItems);
		lblCoopWeight = new JLabel("Data");
		lblCoopWeight.setBounds(60, 35, 47, 13);
		pnlLimC.add(lblCoopWeight);
		lblCoopVolume = new JLabel("Data");
		lblCoopVolume.setBounds(60, 50, 47, 13);
		lblCoopVolume.setBackground(Color.RED);
		pnlLimC.add(lblCoopVolume);
		pnlCOOP.add(pnlLimC);
		// Then rest of controls
		lstCoop = new JTextArea();
		lstCoop.setEditable(false);
		JScrollPane spaneC = new JScrollPane(lstCoop);		
		spaneC.setBounds(307, 16, 102, 69);
		spaneC.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlCOOP.add(spaneC);
		btnCoopStart = new JButton("Start Loading");
		btnCoopStart.setBounds(118, 64, 120, 23);
		pnlCOOP.add(btnCoopStart);
		btnCoopStop = new JButton("Stop");
		btnCoopStop.setBounds(240, 64, 60, 23);
		pnlCOOP.add(btnCoopStop);
		lblCoopStatus = new JLabel(" ");
		lblCoopStatus.setBounds(118, 16, 150, 23);
		pnlCOOP.add(lblCoopStatus);
		chkCoopCont = new JCheckBox("Continue load");
		chkCoopCont.setBounds(118, 39, 130, 17);
		pnlCOOP.add(chkCoopCont);
		// All done, add to consumers panel
		pnlCons.add(pnlCOOP);
		
		JPanel pnlCG = new JPanel();
		pnlCG.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Consumer: CITY GROSS"));
		pnlCG.setBounds(19, 262, 415, 100);
		pnlCG.setLayout(null);
		pnlCons.add(pnlCG);
		
		// Content CITY GROSS panel
		// First the limits panel
		JPanel pnlLimG = new JPanel();
		pnlLimG.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Package Limits"));
		pnlLimG.setBounds(6, 19, 107, 75);
		pnlLimG.setLayout(null);
		JLabel lblItemsG = new JLabel("Items:");
		lblItemsG.setBounds(7, 20, 50, 13);
		pnlLimG.add(lblItemsG);
		JLabel lblWeightG = new JLabel("Weight:");
		lblWeightG.setBounds(7, 35, 50, 13);
		pnlLimG.add(lblWeightG);
		JLabel lblVolumeG = new JLabel("Volume:");
		lblVolumeG.setBounds(7, 50, 50, 13);
		pnlLimG.add(lblVolumeG);
		lblCGItems = new JLabel("Data");
		lblCGItems.setBounds(60, 20, 47, 13);
		pnlLimG.add(lblCGItems);
		lblCGWeight = new JLabel("Data");
		lblCGWeight.setBounds(60, 35, 47, 13);
		pnlLimG.add(lblCGWeight);
		lblCGVolume = new JLabel("Data");
		lblCGVolume.setBounds(60, 50, 47, 13);
		pnlLimG.add(lblCGVolume);
		pnlCG.add(pnlLimG);
		// Then rest of controls
		lstCG = new JTextArea();
		lstCG.setEditable(false);
		JScrollPane spaneG = new JScrollPane(lstCG);		
		spaneG.setBounds(307, 16, 102, 69);
		spaneG.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlCG.add(spaneG);
		btnCGStart = new JButton("Start Loading");
		btnCGStart.setBounds(118, 64, 120, 23);
		pnlCG.add(btnCGStart);
		btnCGStop = new JButton("Stop");
		btnCGStop.setBounds(240, 64, 60, 23);
		pnlCG.add(btnCGStop);
		lblCGStatus = new JLabel("");
		lblCGStatus.setBounds(118, 16, 150, 23);
		pnlCG.add(lblCGStatus);
		chkCGCont = new JCheckBox("Continue load");
		chkCGCont.setBounds(118, 39, 130, 17);
		pnlCG.add(chkCGCont);
		// All done, add to consumers panel
		pnlCons.add(pnlCOOP);
		
		// Add consumer panel to frame
		frame.add(pnlCons);
	}
	
	/**
	 * Buttonlistener för alla knapparna
	 */
	public void buttonListener(){
		/** ---------------------Producer---------------------------- **/
		btnStartS.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.starThread(1);
				lblStatusS.setText("Starting producing...");
			}
		});
		
		btnStartA.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.starThread(2);
				lblStatusA.setText("Starting producing....");
			}
		});
		
		btnStartX.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.starThread(3);
				lblStatusX.setText("Starting producing.....");
			}
		});
		
		btnStopS.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.stopThred(1);
			}
		});
		
		btnStopA.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.stopThred(2);
			}
		});
		
		btnStopX.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.stopThred(3);
			}
		});
		/** ---------------- Consumer -------------------- **/
		btnIcaStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.starThread(4);
			}
		});
		
		btnCoopStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.starThread(5);
			}
		});
		
		btnCGStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.starThread(6);
			}
		});
		
		btnIcaStop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.stopThred(4);
			}
		});	
		
		
		btnCoopStop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.stopThred(5);
			}
		});
		
		btnCGStop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.stopThred(6);
			}
		});
	}

	/**
	 * Metod som updaterar JtextARea för ica
	 * @param productName
	 */
	public void updateICA(String productName) {
		lstIca.append(productName + " \n");
//		lblIcaWeight.setText(String.valueOf(weight));
//		lblIcaVolume.setText(String.valueOf(volume));
//		lblIcaItems.setText(String.valueOf(icaCounter));		
	}

	/**
	 * Metod som updaterar JtextARea för ica
	 * @param productName
	 */
	public void updateCOOP(String productName) {
		lstCoop.append(productName + " \n");
	}
	
	/**
	 * Metod som updaterar JtextARea för ica
	 * @param productName
	 */
	public void updateCityGross(String productName) {
		lstCG.append(productName + " \n");
	}

	public void updateProgressBar(boolean b) {
		if(b == true){
			value++;
		}else if(b == false){
			value--;
		}
		bufferStatus.setValue(value);
		
	}
	
	/**
	 * Retunerar om checkbox för ica är checkad
	 * @return
	 */
	public boolean getIcaCheckBox(){
		return chkIcaCont.isSelected();
	}
	

	/**
	 * Retunerar om checkbox för ica är checkad
	 * @return
	 */
	public boolean getCoopCheckBox(){
		return chkCoopCont.isSelected();
	}
	

	/**
	 * Retunerar om checkbox för ica är checkad
	 * @return
	 */
	public boolean getCgCheckBox(){
		return chkCGCont.isSelected();
	}

	/**
	 * Metod som sätter startvärden för de olika lastbilarna
	 * @param icaItems
	 * @param icaWeight
	 * @param icaVolume
	 * @param cgItems
	 * @param cgWeight
	 * @param cgVolume
	 * @param coopItems
	 * @param coopWeight
	 * @param coopVolume
	 */
	public void setValues(int icaItems, double icaWeight, double icaVolume, int cgItems, double cgWeight, double cgVolume, int coopItems, double coopWeight, double coopVolume) {
		lblIcaWeight.setText(String.valueOf(icaWeight));
		lblIcaVolume.setText(String.valueOf(icaVolume));
		lblIcaItems.setText(String.valueOf(icaItems));
		
		lblCoopWeight.setText(String.valueOf(coopWeight));
		lblCoopVolume.setText(String.valueOf(coopVolume));
		lblCoopItems.setText(String.valueOf(coopItems));		
		
		
		lblCGWeight.setText(String.valueOf(cgWeight));
		lblCGVolume.setText(String.valueOf(cgVolume));
		lblCGItems.setText(String.valueOf(cgItems));	
	}

	/**
	 * Metod som rensar JTtextArean från text
	 * @param consumerName
	 */
	public void clearTextArea(String consumerName) {
		switch(consumerName){
			case "ICA":
				lstIca.setText("");
			break;
			case "COOP":
				lstCoop.setText("");
				break;
			case "CG":
				lstCG.setText("");
				break;
		}
	}

	/**
	 * Metod som sätter labels för GUI när lastbilarna är fulla
	 * @param i
	 */
	public void setLblTruckFull(int i) {
		switch(i){
		case 1:
			lblIcaStatus.setText("TRUCK IS FULL");
			break;
		case 2:
			lblCoopStatus.setText("TRUCK IS FULL");
		case 3:
			lblCGStatus.setText("TRUCK IS FULL");
			break;
		}
	}
}
