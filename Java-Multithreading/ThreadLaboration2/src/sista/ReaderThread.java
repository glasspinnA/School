package sista;

import java.util.Random;

public class ReaderThread extends Thread {
	private Buffer buffer;
	private char answer;
	private int inputTextLenght;
	private String text = "";
	private Controller controller;
	private Object lock;
	private Boolean isSyncSelected;
	private boolean isRunning;
	
	public ReaderThread(int inputTextLenght, Buffer buffer, Controller controller, Boolean isSyncSelected, Object lock){
		this.inputTextLenght = inputTextLenght;
		this.buffer = buffer;
		this.controller = controller;
		this.isSyncSelected = isSyncSelected;
		this.lock = lock;
	}
	
	public void stopRunning(){
    	this.isRunning = false;
    }
	
	/**
	 * Metod som tar emot ett tecken i taget ifrån buffern
	 */
	@Override
	public void run() {
		System.out.println("Reader Thread");
		for(int i=0; i<inputTextLenght; i++){
			try {
				Thread.sleep((long)(Math.random() * 1000));
				if(isSyncSelected == true){
					answer = buffer.syncGet();
					text += answer;
					controller.updateReaderTextArea(answer);
				}else if(isSyncSelected == false){
					answer = buffer.UnSyncGet();
					text += answer;
					controller.updateReaderTextArea(answer);
				}
		
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		controller.setLabelTextReader(text);
	}
}
