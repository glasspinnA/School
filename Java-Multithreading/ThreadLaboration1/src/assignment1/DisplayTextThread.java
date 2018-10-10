package assignment1;

import java.util.Random;

import javax.swing.JLabel;

public class DisplayTextThread extends Thread{
	private boolean isThreadRunning1 = true;
	private GUIDualThreads gui = new GUIDualThreads();
	private int x,y;
	private JLabel lbl;
	private Random rand = new Random();
	
	/**
	 * Konstruktor som tar emot JLabel
	 * @param lblText
	 */
	public DisplayTextThread(JLabel lblText){
		this.lbl = lblText; 
	}
	
	/**
	 * Metod som anropas när man klickar på stopp knappen i GUI
	 * Sätter isThreadRunning to false och stoppar tråden
	 */
    public void stopRunning(){
    	isThreadRunning1 = false;
    }
	
	/**
	 * Metod som flyttar på bilden
	 */
	@Override
	public void run() {
		while(isThreadRunning1){
			x = rand.nextInt(150);
			y = rand.nextInt(150);
			lbl.setLocation(x, y);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
