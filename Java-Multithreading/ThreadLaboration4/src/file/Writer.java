package file;

import java.util.LinkedList;
/***
 * Writer tråd klass som skriver till buffern
 * @author Oscar
 *
 */

public class Writer implements Runnable {
    private BoundedBuffer buffer;
    private LinkedList<String> list = new LinkedList<>();

    /**
     * Konstruktor 
     * @param buffer - Objekt av Buffer
     */
    public Writer(BoundedBuffer buffer, LinkedList<String> list) {
        this.buffer = buffer;
        this.list = list;

    }

    /**
     * Metod som skriver tar ett element ifrån listan och skriver till buffern
     */
    @Override
    public void run() {
        try {
        	for(int i=0; i<list.size(); i++){
        		String word = list.get(i);
        		buffer.writeString(word);
        	}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}



