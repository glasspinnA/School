package sista;


public class WriterThread implements Runnable {
	private Buffer buffer;
	private String result = "";
	private char[] arr;
	private Controller controller;
	private Object lock;
	private Boolean isSyncSelected;
	
	public WriterThread(char[] array, Buffer buffer, Controller controller, Boolean isSyncSelected, Object lock){
		this.controller = controller;
		this.arr = array;
		this.buffer = buffer;
		this.isSyncSelected = isSyncSelected;
		this.lock = lock;
	}
	
	/**
	 * Metod som lägger till ett tecken till buffern
	 */
	@Override
	public void run() {
		System.out.println("Writer Thread");
		controller.setIsRunning(true);
		try {
			for(int i=0; i<arr.length; i++){
				Thread.sleep((long)(Math.random() * 1000));
				if(isSyncSelected == true){
					buffer.syncPut(arr[i]);
					result+=arr[i];
					
				}else if(isSyncSelected == false){
					buffer.unSyncPut(arr[i]);
					result+=arr[i];
				}
				controller.updateWriterTextArea(arr[i]);
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		controller.setLabelTextWriter(result);
		controller.setIsRunning(false);
	}
}
