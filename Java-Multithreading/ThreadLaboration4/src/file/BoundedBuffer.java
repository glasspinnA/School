package file;

/***
 * 
 * @author Oscar
 *
 */
public class BoundedBuffer {
    private int bufferSize = 20;		//Storlek på buffern
    private String[] array = new String[bufferSize];	//Array med en storlek lika stor som buffern
    private BufferStatus[] arrayStatus = new BufferStatus[bufferSize]; // En array av bufferStatus objekt
    private int writePos;	//Positions pekare för varje tråd
    private String wordToFind;			//String som man ska söka efter
    private String wordToReplace;		//Stringen som man ska ersätta med
    private int readPos;				//Läsa position
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
     * Metod som tömmer buffern och sätter bufferstatusen till EMPTY på varje position i buffern
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
     *	Metod som kollar varje string och ersätter det nya ordet som ska bytas till
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
     *Metod som läser ifrån buffern om bufferstatusen har statusen CHECKED
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


