package entities;

import database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BandMedlem {


    private String name;
    private int id;
    private String role;
    private int bandId;

    public BandMedlem(int id, String name, int bandId, String role) throws SQLException {
    	this.name = name;
    	this.role = role;
    	this.id = id;
    	this.setBandId(bandId);
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
    	return this.name + ", " + this.role;
    }

	public int getBandId() {
		return bandId;
	}

	public void setBandId(int bandId) {
		this.bandId = bandId;
	}

}
