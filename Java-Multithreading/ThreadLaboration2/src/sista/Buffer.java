package sista;

public class Buffer {
	private char buffer = '-';
	private boolean empty = true;
	
	/**
	 * Metod som l�gger till ett tecken till buffern n�r man k�r i synkat l�ge
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
	 * Metod som l�gger till ett tecken till buffern n�r man k�r i synkat l�ge
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
	 * Metod som l�gger till ett tecken till buffern n�r man k�r i osynkat l�ge
	 * @param a
	 */
	public void unSyncPut(char a){
		this.buffer = a;
	}
	
	/**
	 * Metod som retunerar ett tecken ifr�n buffer n�r man k�r osynkat l�ge
	 * @return
	 */
	public char UnSyncGet(){
		return buffer;
	}
	
	
}