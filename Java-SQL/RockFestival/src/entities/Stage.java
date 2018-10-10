package entities;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.Database;

public class Stage {
	private int id; //PK
	private String name;
	private int capacity;
	
	public Stage(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	public int getId(){
		return id;
	}
	
	@Override
	public String toString() {
		return name;
	}




	public static void createStage(int id, String name, int capacity) throws SQLException{
		System.out.println("Skapar Scen");
		String sql = "INSERT INTO scen(id, namn, kapacitet) values (?, ?, ?)";
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		stmt = Database.getInstance().getConnection().prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.setString(2, name);
		stmt.setInt(3, capacity);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public static void removeStage(int id) throws SQLException{
		System.out.println("Tar bort scen med id: " + id);
		String sql = "DELETE FROM scen WHERE id = "+ id;
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		stmt.executeUpdate();
	}
	
	public static void printStage() throws SQLException {
		String sql = "SELECT * FROM scen;";
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		ResultSet res = stmt.executeQuery();
		while(res.next()) {
			System.out.println("Id: " + res.getInt(1) + "\nName: " + res.getString(2) + "\n"
					+ "Capacity: " + res.getInt(3));
		}
	}

	
	public static void main(String[] args) {
		try {
			printStage();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	
}
