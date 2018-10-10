package assignment3;

public class Consumer implements Runnable{
	private Buffer<FoodItem> buffer;
	private boolean isRunning = true;
	private String consumerName;
	private Controller controller;
	private int items;
	private double weight, volume;
	private FoodItem item = new FoodItem();
	
	/**
	 * Konstruktor för Consumer
	 * @param consumerName
	 * @param buffer
	 * @param controller
	 * @param items
	 * @param weight
	 * @param volume
	 */
	public Consumer(String consumerName, Buffer buffer, Controller controller, int items, double weight, double volume){
		this.consumerName = consumerName;
		this.buffer = buffer;
		this.controller = controller;
		this.items = items;
		this.weight = weight;
		this.volume = volume;
	}
	
	/**
	 * Metod som stoppar tråd
	 */
	public void stopThread(){
		this.isRunning = false;
	}
	
	/**
	 * Metod som tar bort foodItem från buffern
	 */
	@Override
	public void run() {
		isRunning = true;
		Thread.currentThread().setName(consumerName);
		while(isRunning){
			try {
				Thread.sleep((long)(Math.random() * 2000));
				item =  buffer.get();
				controller.updateTextArea(item, consumerName);
				controller.updateProgressBar(false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(consumerName + " has stopped");
	}
}
