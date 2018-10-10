package file;

import java.util.List;

/**
 * Given klass
 * @author Oscar
 *
 */

public class Modifier implements Runnable {
    private BoundedBuffer buffer;
    private int counter;

	/**
	 * Konstruktur 
	 * @param buffer - delade datan
	 * @param counter - antal strings som ska testas
	 */
    public Modifier(BoundedBuffer buffer, int counter) {
        this.buffer = buffer;
        this.counter = counter;

    }


    /**
     * ThreadLoop - kallar på modify i buffern för varje string som testas
     */
    @Override
    public void run() {
        for (int i = 0; i < counter; i++) {
            try {
                buffer.modify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
