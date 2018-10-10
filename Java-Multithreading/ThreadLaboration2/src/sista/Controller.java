package sista;

public class Controller {
	private Buffer buffer;
	private WriterThread writer;
	private ReaderThread reader;
	private String readerText;
	private String writerText;
	private GUIMutex gui;
	private char[] arr;
	private boolean isRunning = true;
	private boolean match;
	
	public Controller(GUIMutex gui){
		this.gui = gui;
	}
	
	/**
	 * Metod som startar trådarna 
	 * @param inputText
	 * @param isSyncSelected
	 */
	public void startThreads(String inputText, Boolean isSyncSelected){
		buffer = new Buffer(); 
		final Object lock = new Object();
		
		
		arr = new char[inputText.length()];
		for(int i=0; i<inputText.length(); i++){
			arr[i] = inputText.charAt(i);
		}
		
		
		Thread t1 = new Thread(new WriterThread(arr,buffer,this, isSyncSelected, lock));
		Thread t2 = new Thread(new ReaderThread(inputText.length(), buffer, this, isSyncSelected, lock));
		t1.start();
		t2.start();
		
	}

	/**
	 * Metod som uppdaterar GUI med char ifrån Writer tråden
	 * @param r
	 */
	public void updateWriterTextArea(char r) {
		String writerText = String.valueOf(r);
		gui.updateWriterArea(writerText);
	}

	/**
	 * Metod som uppdaterar GUI med char ifrån reader tråden
	 * @param answer
	 */
	public void updateReaderTextArea(char answer) {
		String readerText = String.valueOf(answer);
		gui.updateReaderArea(readerText);
		
	}

	public void setIsRunning(boolean b){
		this.isRunning = b;
	}
	
	public boolean getIsRunning() {
		return isRunning;
	}

	/**
	 * Metod som uppdaterar label i GUI med den slutgilltiga texten ifrån writer tråden 
	 * @param resultText
	 */
	public void setLabelTextWriter(String resultText) {
		writerText = resultText;
		gui.setWriterText(writerText);
	}

	/**
	 * Metod som uppdaterar label i GUI med den slutgilltiga texten ifrån reader tråden
	 * @param text
	 */
	public void setLabelTextReader(String text) {
		//text = text.replaceAll("\\s", "");
		readerText = text;
		gui.setReaderText(readerText);
		compareTexts();
	}

	/**
	 * Metod som jämför om texterna ifrån trådarna stämmer överrens eller inte
	 */
	public void compareTexts() {
//		System.out.println(readerText + "!" + writerText);
		if(readerText.equals(writerText)){
			match = true;
			gui.setColor(match);
		}else{
			match = false;
			gui.setColor(match);
		}
	}

}
