package assignment3;

import java.util.Random;

public class Producer implements Runnable{
	private String producerName;
	private boolean isRunning = true;
	private FoodItem[] foodBuffer = new FoodItem[10];
	private Buffer<FoodItem> buffer;
	private Controller controller;
	private Random rand = new Random();
	/**
	 * Konstruktor för Producer
	 * @param producerName
	 * @param buffer
	 * @param controller
	 */
	public Producer(String producerName, Buffer buffer, Controller controller){
		this.producerName = producerName;
		this.buffer = buffer;
		this.controller = controller;
	}
	
	/**
	 * Metod som stoppar tråd
	 */
	public void stopThread(){
		isRunning = false;
	}
	
	/**
	 * Metod som initiera matprodukterna
	 */
	public void initFoodItems(){
		foodBuffer[0] = new FoodItem("Milk", 1.1, 0.5);
		foodBuffer[1] = new FoodItem("Cream", 0.6, 0.1);
		foodBuffer[2] = new FoodItem("Youghurt", 1.1, 0.5);
		foodBuffer[3] = new FoodItem("Butter", 2.34, 0.66);
		foodBuffer[4] = new FoodItem("Flower", 2.4, 1.2);
		foodBuffer[5] = new FoodItem("Salt", 1.7, 0.2);
		foodBuffer[6] = new FoodItem("Dead person", 3.4, 5.5);
		foodBuffer[7] = new FoodItem("Chicken", 2.2, 1.9);
		foodBuffer[8] = new FoodItem("Beer", 1.0, 0.7);
		foodBuffer[9] = new FoodItem("Bread", 1.2, 2.0);
	}

	/**
	 * Metod som lägger till foodItem till buffern
	 */
	@Override
	public void run() {
		isRunning = true;
		Thread.currentThread().setName(producerName);
		initFoodItems();
		while(isRunning){
			try {
				buffer.put(foodBuffer[rand.nextInt(10)]);
//				System.out.println(producerName + " Buffer put");
				controller.updateProgressBar(true);	
				Thread.sleep((long)(Math.random() * 3000));	
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(producerName + " has stopped");
	}
	
	

}
