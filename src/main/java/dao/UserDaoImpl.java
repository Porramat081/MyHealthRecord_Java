package dao;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;

import model.User;
import utils.PasswordHasher;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(16) NOT NULL,"
					+ "password VARCHAR(16) NOT NULL,"
                    + "firstName VARCHAR(16) NOT NULL,"
                    + "lastName VARCHAR(16) NOT NULL,"
                    + "createdAt TIMESTAMP NOT NULL,"
                    + "editedAt TIMESTAMP NOT NULL,"
                    + "salt VARCHAR(16) NOT NULL,"
                    + "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		} 
	}

	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					return user;
				}
				return null;
			} 
		}
	}

	@Override
	public User createUser(String username, String password , String firstName , String lastName) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ? ,?,?,?,?,?)";
        Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());
        String genSalt = PasswordHasher.generateSalt();
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
            String hashPassword = PasswordHasher.hashPassword(password,genSalt);

			stmt.setString(1, username);
			stmt.setString(2, hashPassword);
            stmt.setString(3,firstName);
            stmt.setString(4,lastName);
            stmt.setTimestamp(5,timestampNow);
            stmt.setTimestamp(6,timestampNow);
            stmt.setString(7,genSalt);

			stmt.executeUpdate();
			return new User(username, password , firstName , lastName , timestampNow,timestampNow);
		} catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
