package sista;

public class Buffer {
	private char buffer = '-';
	private boolean empty = true;
	
	/**
	 * Metod som lägger till ett tecken till buffern när man kör i synkat läge
	 * @param a
	 * @throws InterruptedException
	 */
	public synchronized void syncPut(char a) throws InterruptedException {
		if(this.empty == false){
			this.wait();
		}
		this.buffer = a;
		this.empty = false;
		this.notify();
	}
	/**
	 * Metod som lägger till ett tecken till buffern när man kör i synkat läge
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized char syncGet() throws InterruptedException {
		char a = buffer;
		if(this.empty == true) {
			wait();
		}
		this.empty = true;
		if(a == buffer){
			this.notify();	
		}
		return buffer;
	}
	
	/**
	 * Metod som lägger till ett tecken till buffern när man kör i osynkat läge
	 * @param a
	 */
	public void unSyncPut(char a){
		this.buffer = a;
	}
	
	/**
	 * Metod som retunerar ett tecken ifrån buffer när man kör osynkat läge
	 * @return
	 */
	public char UnSyncGet(){
		return buffer;
	}
	
	
}