package controller;

import database.Database;
import entities.Band;
import entities.BandMedlem;
import entities.Genre;
import entities.Personal;
import entities.Stage;
import gui.Schema;
import gui.SchemaGUI;
import gui.KansliInmatningGUI;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
//	private Schema schema = new Schema();

	private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

	private void setLogger() {
		ConsoleHandler handler = new ConsoleHandler();
		LOGGER.setUseParentHandlers(false);
		LOGGER.setLevel(Level.INFO);
		LOGGER.addHandler(handler);
	}

	public Controller() {
		setLogger();
		Database.getInstance();
	}

	/**
	 * Lägger till ett band i databasen
	 * 
	 * @param bandName
	 *            En String med namnet på bandet
	 * @param bio
	 *            En String med bio på bandent. Denna får maximalt vara 1000
	 *            tecken lång
	 * @param genre
	 *            En int med id till ett genre objekt
	 * @param country
	 *            En String med namnet av ett land
	 * @param contactId
	 *            En String med personnummret av en personal som är en kontakt
	 *            person
	 * @throws SQLException
	 *             Kastas om något går fel när man sätter in bandet
	 */
	public void insertBand(String bandName, String bio, int genre, String country, String contactId)
			throws SQLException {
		String sql = "INSERT INTO band (namn, genre, country, bio, kontaktperson) values (?,?,?,?,?)";
		LOGGER.info(sql);
		Database.getInstance();
		PreparedStatement stmt = Database.getStmt(sql);
		stmt.setString(1, bandName);
		stmt.setInt(2, genre);
		stmt.setString(3, country);
		stmt.setString(4, bio);
		stmt.setString(5, contactId);
		stmt.executeUpdate();
		stmt.close();
		LOGGER.info(sql + " Lyckades");
	}
    
    /**
     * Metod som lägger till en kontaktperson
     * @param staffName
     * @param staffPhoneNbr
     * @param staffPnr
     * @throws SQLException
     */
	public void insertContactPerson(String staffName, String staffPhoneNbr, String staffPnr) throws SQLException{
		Personal.createPersonal(staffName, staffPhoneNbr, staffPnr);
	}
	

	public void insertBandMedlem(String name, int bandId, String roll) throws SQLException {
		// Skapar bandmedlemmen
		String sql = "INSERT INTO bandmedlem(namn, spelar_i, roll) VALUES(?, ?, ?) ";
		LOGGER.info(sql);
		PreparedStatement stmt = Database.getStmt(sql);
		stmt.setString(1, name);
		stmt.setInt(2, bandId);
		stmt.setString(3, roll);
		stmt.executeUpdate();
		stmt.close();
		LOGGER.info(sql + " Lyckades");
	}
	

	public List<Stage> getStages() throws SQLException {
		List<Stage> stages = new ArrayList<Stage>();
		String sql = "SELECT * FROM scen";
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getString(2);
			Stage stage = new Stage(id, name);
			stages.add(stage);
		}
		rs.close();
		stmt.close();
		return stages;
	}
	
	public List<Genre> getGenres() throws SQLException {
		List<Genre> genreList = new ArrayList<Genre>();
		String sql = "SELECT * FROM genre";
		PreparedStatement stmt = Database.getStmt(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getString(2);
			Genre genre = new Genre(name, id);
			genreList.add(genre);
		}
		rs.close();
		stmt.closeOnCompletion();
		return genreList;
	}

	public List<String> getCountries() throws SQLException {
		List<String> coutries = new ArrayList<String>();
		String sql = "SELECT * FROM countries";

		PreparedStatement stmt = Database.getStmt(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			String country = rs.getString(1);
			coutries.add(country);
		}
		rs.close();
		stmt.close();
		return coutries;
	}
	
	public void addGig(String day, String startTime, String endTime, int bandId, int scenId) throws SQLException {
		String sql = "INSERT INTO spelning(start_time, end_time, band_id, day, scen) VALUES(?, ?, ?, ?,?) ";
		LOGGER.info(sql);
		PreparedStatement stmt = Database.getStmt(sql);
		stmt.setString(1, startTime);
		stmt.setString(2, endTime);
		stmt.setInt(3, bandId);
		stmt.setString(4, day);
		stmt.setInt(5,scenId);
		stmt.executeUpdate();
		stmt.close();
		LOGGER.info(sql + " Lyckades");
	}
	
    /**
     * Metod som ska genera schemat
     * @throws SQLException
     */
	public void getSchema(String day, int stageId) throws SQLException {
		String sql = "SELECT spelning.start_time,spelning.end_time, band.namn, genre.name FROM spelning INNER JOIN band ON spelning.band_id = band.id INNER JOIN genre ON band.genre = genre.id WHERE (spelning.scen = ? AND spelning.day = ?)ORDER BY spelning.start_time ASC ;";
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		
		stmt.setInt(1,stageId);
		stmt.setString(2,day);
		ResultSet res = stmt.executeQuery();
		List<String> myList = new ArrayList<String>();
		while(res.next()) {
			System.out.println(res.getString(1));
			myList.add("Tid: " +res.getString(1) + "-" + res.getString(2));
			myList.add("Band: " + res.getString(3));
			myList.add("Genre: " + res.getString(4));
		}
		Schema.test(myList);
		res.close();
		stmt.close();	
	}

	public List<String> getPersons() throws SQLException {
		List<String> persons = new ArrayList<String>();
		String sql = "SELECT * FROM personal";
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			String person = rs.getString(1);
			persons.add(person);
		}
		rs.close();
		stmt.close();
		return persons;
	}


	public List<Band> getBands() throws SQLException {
		LOGGER.info("Getting bands from database");
		List<Band> bandList = new ArrayList<Band>();
		String sql = "SELECT * FROM band";
		PreparedStatement stmt = Database.getStmt(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String bio = rs.getString(2);
			String name = rs.getString(3);
			String country = rs.getString(4);
			String contact = rs.getString(5);
			int genre = rs.getInt(6);
			Band band = new Band(name, genre, bio, country, contact, id);
			bandList.add(band);
		}
		rs.close();
		stmt.closeOnCompletion();
		LOGGER.info("Getting bands from database complete");
		return bandList;
	}

	public List<BandMedlem> getBandMembersFromBand(Band band) throws SQLException {
		LOGGER.info("Getting band members for: " + band.getName());
		List<BandMedlem> bandMemberList = new ArrayList<BandMedlem>();
		String sql = "SELECT * FROM bandmedlem WHERE spelar_i = ?";
		PreparedStatement stmt = Database.getStmt(sql);
		stmt.setInt(1, band.getId());
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			BandMedlem bm = new BandMedlem(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getString(4));
			bandMemberList.add(bm);
		}
		LOGGER.info("Gettind band members for: " + band.getName() + " Completed");
		rs.close();
		stmt.closeOnCompletion();
		return bandMemberList;
	}
	


	public void removeBandMemberFromBand(BandMedlem bandMedlem) throws SQLException {
		String sql = "DELETE FROM bandmedlem "
				+ "WHERE id=?";
		PreparedStatement stmt = Database.getStmt(sql);
		stmt.setInt(1, bandMedlem.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	public static void main(String[] args) {
		Database db = Database.getInstance();
		Controller controller = new Controller();
		KansliInmatningGUI gui = new KansliInmatningGUI(controller);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Rock Festival | KansliApplikation");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(gui);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
