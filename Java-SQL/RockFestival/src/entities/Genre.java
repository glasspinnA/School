package entities;

public class Genre {
	private int id; // PK
	private String name;
	
	public Genre(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
}
