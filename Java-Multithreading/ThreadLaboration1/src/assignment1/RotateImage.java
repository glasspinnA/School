package assignment1;

import javax.swing.JLabel;

public class RotateImage extends Thread{
	private boolean isThreadRunning = true;
	private JLabel image;
	private static int x = 0;
	
	/**
	 * Konstruktor som tar emot JLabel
	 * @param image
	 */
    public RotateImage(JLabel image) {
		this.image = image;
	}

    /**
     * Metod som anropas n�r man klickar p� stopp knappen i GUI
     * S�tter isThreadRunning to false och stoppar tr�den
     */
	public void stopRunning(){
    	isThreadRunning = false;
    }
	
	
	/**
	 * Metod som flyttar p� bilden
	 */
	@Override
	public void run() {
		while(isThreadRunning){
			image.setLocation(x+=2, 0);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
