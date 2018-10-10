package file;
import java.util.LinkedList;

/***
 * Reader tr�d som l�ser ifr�n buffern
 * @author Oscar
 *
 */

public class Reader implements Runnable {
    private int listSize;
    private BoundedBuffer buffer;
    private LinkedList<String> list = new LinkedList<>();


    /**
     * Konstruktor
     * @param buffer - Objekt av buffern
     * @param listSize - Storlek p� listan list
     */
	public Reader(BoundedBuffer buffer, int listSize) {
        this.buffer = buffer;
        this.listSize = listSize;
    }
	
    public LinkedList<String> getList() {
		return list;
	}

	public void setList(LinkedList<String> list) {
		this.list = list;
	}
    
	/**
	 *  Metod som l�ser ifr�n buffern och lagrar elementent i listan
	 */
    public void run() {  
	    while (list.size() < listSize) {
	        try {
				list.add(buffer.read());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    buffer.show();
    }


}

