package entities;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import database.Database;

public class Personal {
	
	// Variabler att lagra information i.
	private String personnr;
	private String name;
	private String phoneNumber;
	
	public static void createPersonal(String name, String phoneNbr, String pnr) throws SQLException {
		boolean pnrExists = false;
		
		// Checks if the pnr allready exits in the database
		String sql = "SELECT * FROM personal where personnr = '" + pnr +"';" ;
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		ResultSet res = stmt.executeQuery();
		if (res.first()) {
			pnrExists = true;
		}
		
		if (!pnrExists) {
			System.out.println("Skapar personal");
			sql = "INSERT INTO personal(personnr, phonenbr, namn) values (?, ?, ?)";
			stmt = Database.getInstance().getConnection().prepareStatement(sql);
			stmt.setString(1, pnr);
			stmt.setString(2, phoneNbr);
			stmt.setString(3, name);
			stmt.executeUpdate();
			stmt.close();
		} else {
			System.out.println("Personal finns redan");
		}
	}
	
	public static void removePersonal(String pnr) throws SQLException {
		System.out.println("Tar bort personal med personnr: " + pnr);
		String sql = "DELETE FROM personal " +
				"WHERE personnr = "+ pnr;
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		stmt.executeUpdate();
		stmt.close();;
	}
	
	public static void printPersonal() throws SQLException {
		String sql = "SELECT * FROM personal;";
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		ResultSet res = stmt.executeQuery();
		while(res.next()) {
			System.out.println("Pnr: " + res.getString(1) + "\nTelefonnummer: " + res.getString(2) + "\n"
					+ "Namn: " + res.getString(3));
		}
		res.close();
		stmt.close();
	}
	
	public static void main(String[] args) {
		try {
			//createPersonal("Kung", "496719", "9812121212");
			//removePersonal("9812121212");
//			createPersonal("Kung", "496719", "9812121212");
//			removePersonal("9812121212");
//			createPersonal("Kunglig man", "749121", "9595959595");
//			removePersonal("9595959595");			
			printPersonal();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
