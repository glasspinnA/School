package assignment3;

import javax.swing.JOptionPane;

public class Controller {
	private GUIFoodSupply gui;
	private Thread scanProducer, arlaProducer, axFoodProducer, icaConsumer, coopConsumer, cityGrossConsumer;
	private int icaItems = 15;
	private double icaWeight = 27.00;
	private double icaVolume = 12.50;
	private int cgItems = 19;
	private double cgWeight = 23.30;
	private double cgVolume = 17.00;
	private int coopItems = 10;
	private double coopWeight = 13.20;
	private double coopVolume = 9.70;
	
	private Buffer buffer = new Buffer<>();
	private Producer scan = new Producer("Scan",buffer,this);
	private Producer arla = new Producer("Arla",buffer,this);
	private Producer axFood = new Producer("AxFood",buffer,this);
	private Consumer ica = new Consumer("ICA", buffer, this, icaItems, icaWeight, icaVolume);
	private Consumer coop = new Consumer("COOP", buffer, this, coopItems, coopWeight, coopVolume);
	private Consumer cityGross = new Consumer("City Gross",buffer, this, cgItems, cgWeight, cgVolume);

	/**
	 * Konstruktor för controller klassen
	 */
	public Controller(GUIFoodSupply gui) {
		this.gui = gui;
	}

	/**
	 * Metod som startar vald tråd
	 * @param number
	 */
	public void starThread(int number) {
		switch(number){
			case 1:
				scanProducer = new Thread(scan);
				scanProducer.start();
				break;
			case 2:
				arlaProducer = new Thread(arla);
				arlaProducer.start();
				break;
			case 3:
				axFoodProducer = new Thread(axFood);
				axFoodProducer.start();
				break;
			case 4:
				icaConsumer = new Thread(ica);
				icaConsumer.start();
				break;
			case 5:
				coopConsumer = new Thread(coop);
				coopConsumer.start();
				break;
			case 6:
				cityGrossConsumer = new Thread(cityGross);
				cityGrossConsumer.start();
				break;
		}
	}

	/**
	 * Metod som stoppar vald tråd
	 * @param number
	 */
	public void stopThred(int number) {
		switch(number){
			case 1:
				scan.stopThread();
				break;
			case 2:
				arla.stopThread();
				break;
			case 3:
				axFood.stopThread();
				break;
			case 4:
				ica.stopThread();
				break;
			case 5:
				coop.stopThread();
				break;
			case 6:
				cityGross.stopThread();
				break;
		}
	}

	/**
	 * Metod som updatera JTextArean i GUI och håller koll på maxvikt och maxvolym har passerat
	 * @param item
	 * @param consumerName
	 * @throws InterruptedException
	 */
	public void updateTextArea(FoodItem item, String consumerName) throws InterruptedException {
		String productName = item.getName();
		double volume = item.getVolume();
		double weight = item.getWeight();
		if(consumerName == "ICA"){
			if((volume < icaVolume) && (weight < icaWeight) && (icaItems > 0)){
				icaItems--;
				icaVolume -= volume;
				icaWeight -= weight;
				gui.updateICA(productName);			
			}else{
				gui.setLblTruckFull(1);
				ica.stopThread();
				if(gui.getIcaCheckBox() == true){
					setValues();
					gui.clearTextArea("ICA");
					Thread.sleep(2000);
					starThread(4);
				}
			}
		}else if(consumerName == "COOP"){
			if((volume < coopVolume) && (weight < coopWeight) && (coopItems > 0)){
				coopItems--;
				coopVolume -= volume;
				coopWeight -= weight;
				gui.updateCOOP(productName);			
			}else{
				gui.setLblTruckFull(2);
				coop.stopThread();
				if(gui.getCoopCheckBox() == true){
					setValues();
					gui.clearTextArea("COOP");
					Thread.sleep(2000);
					starThread(5);
				}
			}
		}else if(consumerName == "City Gross"){
			if((volume < cgVolume) && (weight < cgWeight) && (cgItems > 0)){
				cgItems--;
				cgVolume -= volume;
				cgWeight -= weight;
				gui.updateCityGross(productName);			
			}else{
				gui.setLblTruckFull(3);
				cityGross.stopThread();
				if(gui.getCgCheckBox() == true){
					setValues();
					gui.clearTextArea("CG");
					Thread.sleep(2000);
					starThread(6);
				}
			}
		}
		
	}

	/**
	 * Metod som uppdaterar progressbaren
	 * @param b
	 */
	public void updateProgressBar(boolean b) {
		gui.updateProgressBar(b);
	}
	
	/**
	 * Metod som sätter alla värden för maxvikt, maxvolym för dom olika lastbilarna
	 */
	public void setValues(){
		icaItems = 15;
		icaWeight = 27.00;
		icaVolume = 12.50;
		cgItems = 19;
		cgWeight = 23.30;
		cgVolume = 17.00;
		coopItems = 10;
		coopWeight = 13.20;
		coopVolume = 9.70;
		gui.setValues(15,27.00,12.50, 19, 23.30,17.00, 10, 13.20, 9.70);
	}


}
