package entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.Database;

public class Band {
	private int id; // PK
	private String name;
	private int genre;
	private String bio;
	private String country;
	private String contactPerson; // FK To a person

    // A Array List to store band members
    private ArrayList<BandMedlem> bandMembers = new ArrayList<BandMedlem>();
    
    private final static Logger LOGGER = Logger.getLogger(Band.class.getName());

    private void setLogger() {
    		ConsoleHandler handler = new ConsoleHandler();
    		LOGGER.setUseParentHandlers(false);
    		LOGGER.setLevel(Level.INFO);
    		LOGGER.addHandler(handler);
    }
	
	/**
	 * The constructor querys the database to ask if a band exits. If it exists the a band object is
	 * created
	 * @param id
	 * 		The id that the band has.
	 * @throws SQLException 
	 */
	public Band(int id) throws SQLException {
		setLogger();
		String sql = "SELECT * FROM band where id="+id;
		LOGGER.info(sql);
		
		PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement(sql);
		ResultSet res = stmt.executeQuery();
		while(res.next()) {
			this.id = res.getInt(1);
			this.bio = res.getString(2);
			this.name = res.getString(3);
			this.country = res.getString(4);
			this.contactPerson = res.getString(5);
			this.genre = res.getInt(6);
		}
		res.close();
		stmt.closeOnCompletion();
		LOGGER.info(sql + "Lyckades");
	}

    /**
     * The constructor creates a Band object.
     * @param name
     *      The name of the bnad
     * @param genre
     *      The main genre of the band
     * @param bio
     *      A Biography of the band maximum of 1000 chars
     * @param country
     *      The country witch the band comes from
     * @param contactPerson
     *      A Reference to the bands contact person
     * @param id 
     */
    public Band(String name, int genre, String bio, String country, String contactPerson, int id) {
        this.name = name;
        this.genre = genre;
        this.bio = bio;
        this.country = country;
        this.contactPerson = contactPerson;
        this.id = id;
    }

	public static Band getBandFromDatabase(int id) throws SQLException {
		Band band = new Band(id);
		return band;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public int getGenre() {
		return genre;
	}

	public void setGenre(int genre) {
		this.genre = genre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return this.getName();
	}
	
	public String getGenreAsText() throws SQLException {
		String sql = "SELECT name FROM genre WHERE id = ?";
		PreparedStatement stmt = Database.getStmt(sql);
		stmt.setInt(1, this.genre);
		ResultSet res = stmt.executeQuery();
		String genre = null;

		if (res.next()) {

			genre = res.getString(1);
		}
		res.close();
		stmt.close();
		return genre;
	}
}
