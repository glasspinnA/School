package assignment3;

public class FoodItem {
	private String name;
	private double weight, volume;
	
	public FoodItem(String name, double weight, double volume){
		this.name = name;
		this.weight = weight;
		this.volume = volume;
	}
	
	public FoodItem() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public double getWeight() {
		return weight;
	}

	public double getVolume() {
		return volume;
	}
}
