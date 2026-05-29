package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    /*
      Provides a shared SQLite connection using JDBC. All DAOs call getConnection() to access the database.
      URL pattern for database : jdbc:sqlite:application.db
    */
	private static final String DB_URL = "jdbc:sqlite:application.db";

	public static Connection getConnection() throws SQLException {
		/*
		  DriverManager is the basic service for managing a set of JDBC drivers.
		  Opens and returns a new JDBC connection to the SQLite database file (application.db).
		*/
		return DriverManager.getConnection(DB_URL);
	}
}
