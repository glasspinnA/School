package assignment3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Buffer<T> {
	private static Semaphore semPro = new Semaphore(20,true); 	// 20 = buffer size 
	private static Semaphore semCon = new Semaphore(0,true);
	private static Semaphore mutex = new Semaphore(1);
	private LinkedList<FoodItem> buffer = new LinkedList<FoodItem>();
	private FoodItem item;
	
	/**
	 * Metod som tar bort foodItem från buffern och retunerar det till Consumer
	 * @return
	 * @throws InterruptedException
	 */
	public FoodItem get() throws InterruptedException{
		semCon.acquire();
		mutex.acquire();
		item = buffer.removeFirst();
		mutex.release();
		semPro.release();
		return item;
	}
	
	/**
	 * Metod som lägger till foodItem till buffern 
	 * @param foodBuffer
	 * @throws InterruptedException
	 */
	public void put(FoodItem foodBuffer) throws InterruptedException{
		semPro.acquire();
		mutex.acquire();
		buffer.addLast(foodBuffer);
		mutex.release();
		semCon.release();
	}
}
