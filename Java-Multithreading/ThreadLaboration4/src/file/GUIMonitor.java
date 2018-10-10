package file;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * The GUI for assignment 4
 */
public class GUIMonitor extends Component {
    /**
     * These are the components you need to handle.
     * You have to add listeners and/or code
     */
    private JFrame frame;                // The Main window
    private JMenu fileMenu;                // The menu
    private JMenuItem openItem;            // File - open
    private JMenuItem saveItem;            // File - save as
    private JMenuItem exitItem;            // File - exit
    private JTextField txtFind;            // Input string to find
    private JTextField txtReplace;        // Input string to replace
    private JCheckBox chkNotify;        // User notification choise
    private JLabel lblInfo;                // Hidden after file selected
    private JButton btnCreate;            // Start copying
    private JButton btnClear;            // Removes dest. file and removes marks
    private JLabel lblChanges;            // Label telling number of replacements
    
    private Controller controller;
    private int nbrReplacements = 0;
    private int counter = 0;
    private JTextArea txtSourec;
    private JTextArea txtDestition;
    private Highlighter highlighter;
    
    /**
     * Constructor
     */
    public GUIMonitor() {
    }

    private Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter();

    /**
     * Starts the application
     *
     * @param controller
     */
    public void Start(Controller controller) {
        this.controller = controller;
        frame = new JFrame();
        frame.setBounds(0, 0, 754, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Text File Copier - with Find and Replace");
        InitializeGUI();                    // Fill in components
        frame.setVisible(true);
        frame.setResizable(false);            // Prevent user from change size
        frame.setLocationRelativeTo(null);    // Start middle screen
        buttonListener();
    }


	/**
     * Sets up the GUI with components
     */
    private void InitializeGUI() {
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open Source File");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openItem.addActionListener(e -> controller.chooseFile());
        saveItem = new JMenuItem("Save Destination File As");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveItem.setEnabled(false);
        exitItem = new JMenuItem("Exit");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));        
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        JMenuBar bar = new JMenuBar();
        frame.setJMenuBar(bar);
        bar.add(fileMenu);
        
        JPanel pnlFind = new JPanel();
        pnlFind.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Find and Replace"));
        pnlFind.setBounds(12, 32, 436, 122);
        pnlFind.setLayout(null);
        frame.add(pnlFind);
        JLabel lab1 = new JLabel("Find:");
        lab1.setBounds(7, 30, 80, 13);
        pnlFind.add(lab1);
        JLabel lab2 = new JLabel("Replace with:");
        lab2.setBounds(7, 63, 80, 13);
        pnlFind.add(lab2);

        txtFind = new JTextField();
        txtFind.setBounds(88, 23, 327, 20);
        pnlFind.add(txtFind);
        txtFind.setText("");
        txtReplace = new JTextField();
        txtReplace.setBounds(88, 60, 327, 20);
        txtReplace.setText("");
        pnlFind.add(txtReplace);
        chkNotify = new JCheckBox("Notify user on every match");
        chkNotify.setBounds(88, 87, 180, 17);
        pnlFind.add(chkNotify);

        lblInfo = new JLabel("Select Source File..");
        lblInfo.setBounds(485, 42, 120, 13);
        frame.add(lblInfo);

        btnCreate = new JButton("Copy to Destination");
        btnCreate.setBounds(465, 119, 230, 23);
        frame.add(btnCreate);
        btnClear = new JButton("Clear dest. and remove marks");
        btnClear.setBounds(465, 151, 230, 23);
        frame.add(btnClear);

        lblChanges = new JLabel("No. of Replacements:");
        lblChanges.setBounds(279, 161, 200, 13);
        frame.add(lblChanges);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 170, 763, 399);
        frame.add(tabbedPane);
        txtSourec = new JTextArea();
        txtSourec.setLineWrap(true);
        txtSourec.setEditable(false);
        JScrollPane scrollSource = new JScrollPane(txtSourec);
        tabbedPane.addTab("Source", scrollSource);
        txtDestition = new JTextArea();
        txtDestition.setLineWrap(true);
        txtDestition.setEditable(false);
        JScrollPane scrollDest = new JScrollPane(txtDestition);
        tabbedPane.addTab("Destination", scrollDest);
        
        
        saveItem.addActionListener(e -> controller.chooseFile());
        exitItem.addActionListener(e -> System.exit(0));

    }
    
    private void buttonListener() {
    	btnClear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtFind.setText("");
	            txtReplace.setText("");
	            lblChanges.setText("No. of Replacements:");
	            txtDestition.setText("");
	            nbrReplacements = 0;
	            txtSourec.setText("");
	            controller.readFile();
	            counter = 0;
			}
    		
    	});
    	
    	btnCreate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	            if (txtFind.getText().isEmpty() || txtReplace.getText().isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Please fill in both fields");

	            } else if (chkNotify.isSelected()) {
	                int reply = JOptionPane.showConfirmDialog(null, "Replace " + txtFind.getText() + " with " + txtReplace.getText() + ".", "Confirm", JOptionPane.YES_NO_OPTION);
	                if (reply == JOptionPane.YES_OPTION) {
	                    controllHighliht();
	                }
	            } else {
	                controllHighliht();
	            }
			}
    		
    	});
		
	}



    private void controllHighliht() {
        if (counter == 0) {
            controller.startThreads(1);
        }
        else if (counter > 0) {
            controller.startThreads(2);
        }
        counter++;
        highlight(txtSourec, txtFind.getText());
    }

    public String getTxtFind() {
        return txtFind.getText();
    }

    public void setSourceText(String text) {
        this.txtSourec.append(text);
    }

    public String getReplaceText() {
        return txtReplace.getText();
    }


    public void appeandDestination() {
        txtDestition.setText("");
        for (String s : controller.getReaderList()) {
            txtDestition.append(s);
        }
    }


    private void highlight(JTextArea txtPaneSource, String pattern) {
        try {
            highlighter = txtPaneSource.getHighlighter();

            Document doc = txtPaneSource.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0) {

                highlighter.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                nbrReplacements++;
                lblChanges.setText("No. of Replacements: " + nbrReplacements);
                pos += pattern.length();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

        public MyHighlightPainter() {
            super(Color.YELLOW);

        }
    }
}


