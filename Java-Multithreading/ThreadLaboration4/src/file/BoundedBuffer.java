package file;

/***
 * 
 * @author Oscar
 *
 */
public class BoundedBuffer {
    private int bufferSize = 20;		//Storlek p� buffern
    private String[] array = new String[bufferSize];	//Array med en storlek lika stor som buffern
    private BufferStatus[] arrayStatus = new BufferStatus[bufferSize]; // En array av bufferStatus objekt
    private int writePos;	//Positions pekare f�r varje tr�d
    private String wordToFind;			//String som man ska s�ka efter
    private String wordToReplace;		//Stringen som man ska ers�tta med
    private int readPos;				//L�sa position
    private int findPos;				//Hitta position

    private Controller controller;

    /**
     * Konstruktor
     * @param controller
     * @param find
     * @param strReplace
     */
    public BoundedBuffer(Controller controller, String wordToFind, String wordToReplace) {
        this.controller = controller;
        this.wordToFind = wordToFind;
        this.wordToReplace = wordToReplace;
        clearBufferStatus();
    }
    

    /**
     * Metod som t�mmer buffern och s�tter bufferstatusen till EMPTY p� varje position i buffern
     */
    private void clearBufferStatus() {
		// TODO Auto-generated method stub
        array = new String[bufferSize];
        arrayStatus = new BufferStatus[bufferSize];
        
        for(int i = 0; i < arrayStatus.length; i++) {
            arrayStatus[i] = BufferStatus.EMPTY;
        }
	}

	/**
	 * Metod som skriver till buffern
	 * @param text
	 * @throws InterruptedException
	 */

    public synchronized void writeString(String text) throws InterruptedException{
        while(arrayStatus[writePos] != BufferStatus.EMPTY) {
            wait();
        }
        arrayStatus[writePos] = BufferStatus.NEW;
        array[writePos] = text;
        writePos = ((writePos + 1) % bufferSize);
        notifyAll();
    }
    
    

    /**
     *	Metod som kollar varje string och ers�tter det nya ordet som ska bytas till
     * @throws InterruptedException
     */
    public synchronized void modify() throws InterruptedException {
        while(arrayStatus[findPos] != BufferStatus.NEW) {
            wait();
        }
        
        if(array[findPos].contains(wordToFind)) {
            String newWord = array[findPos].replaceAll(wordToFind, wordToReplace);
            array[findPos] = newWord;
        }
        
        
        
        arrayStatus[findPos] = BufferStatus.CHECKED;
        findPos = ((findPos + 1) % bufferSize);
        notifyAll();
        
        

    }


    /**
     *Metod som l�ser ifr�n buffern om bufferstatusen har statusen CHECKED
     *retunerar texten 
     * @return
     * @throws InterruptedException
     */
    public synchronized String read() throws InterruptedException {
        while(arrayStatus[readPos] != BufferStatus.CHECKED) {
            wait();
        }
        
        
        String text = array[readPos];
        arrayStatus[readPos] = BufferStatus.EMPTY;
        readPos = ((readPos + 1) % bufferSize);
        notifyAll();
        return text;
    }


    public void show() {
        controller.result();
    }
}


