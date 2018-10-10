package file;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
/***
 * En klass som sköter all logik mellan de olika klasserna 
 * @author Oscar
 *
 */

public class Controller extends JFrame {	
    private GUIMonitor gui;
    private Thread readerThread, writerThread, modifierThread;
    private LinkedList<String> list = new LinkedList<>();
    private BoundedBuffer buffer;
    private Reader reader;
    private Modifier modifier;
    private Writer writer;
    private Scanner scanner;
    private String textFile;

    public Controller(GUIMonitor gui) {
        this.gui = gui;
    }

    /**
     * Metod som gör så att man kan välja vilken fil man ska öppna
     */
    public void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            textFile = file.toString();
            readFile();
        }
    }

    /**
     * Metod som läser in en text fil
     * 
     */
    public void readFile() {
        try {
        	scanner = new Scanner(new File(textFile));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                list.add(word + "\n");
                gui.setSourceText(word + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
        	scanner.close();
        }
    }

    /**
     * Metod som startar alla trådar som behövs
     * @param number
     */
    public void startThreads(int number) {
    	buffer = new BoundedBuffer(this, gui.getTxtFind(), gui.getReplaceText());
 
    	if(number == 2){
    		list = reader.getList();
    	}
    	
        writer = new Writer(buffer, list);
        reader = new Reader(buffer, list.size());
        modifier = new Modifier(buffer, list.size());
    	
        writerThread = new Thread(writer);
    	writerThread.start();
    	modifierThread = new Thread(modifier);
    	modifierThread.start();
    	readerThread = new Thread(reader);
    	readerThread.start();
    	
    }


    public List<String> getReaderList() {
        return reader.getList();
    }

    
    
   
    public void result() {
        gui.appeandDestination();
    }
}
