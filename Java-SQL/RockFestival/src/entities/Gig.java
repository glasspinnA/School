package entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

public class Gig {
	private static int id; //PK
	private int day, month, year;
	private String name;
	private String startTime, endTime;
	private int bandId;
	private int stage;
	
	public static void createGig(int day, int month, int year, String startTime, String endTime, int bandId, int stage, int id) throws SQLException{
		
		System.out.println("Skapar spelning");
		String sql = "INSERT INTO spelning(day, month, year, start_time, end_time, band_id, scen, id) values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		stmt = Database.getInstance().getConnection().prepareStatement(sql);
		stmt.setInt(1, day);
		stmt.setInt(2, month);
		stmt.setInt(3, year);
		stmt.setString(4, startTime);
		stmt.setString(5, endTime);
		stmt.setInt(6, bandId);
		stmt.setInt(7, stage);
		stmt.setInt(8, id);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public static void removeGig(int id) throws SQLException {
		System.out.println("Tar bort spelning med id nummer:" + id);
		String sql = "DELETE FROM spelning " + "WHERE id = "+ id;
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		stmt.executeUpdate();
	}
	
	public static void printGig() throws SQLException {
		String sql = "SELECT * FROM spelning;";
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		ResultSet res = stmt.executeQuery();
		while(res.next()) {
			System.out.println("Day: " + res.getInt(1) + "\nMonth: " + res.getInt(2) + "\n"
					+ "Year " + res.getInt(3) + "\nStart time " + res.getString(4) + "\nEnd time " + res.getString(5) + "\n"
					+ "Band id " + res.getInt(6) + "\nStage" + res.getString(7)+ "\nid " + res.getString(8));
		}
	}
	
	public static void main(String[] args) {
		try {
			createGig(10,2,2016,"09:00","10:30",16,2,17);
			
			printGig();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
