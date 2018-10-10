package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * En singelton implementation av en connection till en MYSQL Databas
 * 
 * @author patriklarsson
 *
 */
public class Database {
	private final static Logger LOGGER = Logger.getLogger(Database.class.getName());
	private Connection conn = null;
	private Statement stmt = null;
	private static Database instance;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	// Användaruppgifter
	public static final String USERNAME = "AD1107";
	public static final String PASSWORD = "qwerty123";
	
	private void setLogger() {
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatter());
		LOGGER.setLevel(Level.INFO);
		LOGGER.addHandler(new ConsoleHandler());
		LOGGER.setUseParentHandlers(false);
		
	}
	
	private Database() {
		try {
			setLogger();
			Class.forName(JDBC_DRIVER);
			LOGGER.info("Ansluter till Databas: 195.178.232.16 ");
			conn = DriverManager.getConnection(
					"jdbc:mysql://195.178.232.16/" + USERNAME + "?user=" + USERNAME + "&password=" + PASSWORD);
			LOGGER.info("Ansluten till Databas");
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PreparedStatement getStmt(String sql) throws SQLException {
		return getInstance().conn.prepareStatement(sql);
	}
	
	public static void disconnect() throws SQLException {
		LOGGER.info("Stänger ner ansluting till databas");
		getInstance().conn.close();
	}
		
	public Connection getConnection() {
		return this.conn;
	}
	
	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
			return instance;
	}
}
