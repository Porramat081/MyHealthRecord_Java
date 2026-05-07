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
                    + "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		} 
	}

	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? and password = ?";

		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {

            String hashPass = PasswordHasher.hashPassword(password);

			stmt.setString(1, username);
            stmt.setString(2,hashPass);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(password);
                    user.setFirstname(rs.getString("firstName"));
                    user.setLastname(rs.getString("lastName"));
                    user.setCreatedAt(rs.getTimestamp("createdAt"));
                    user.setEditedAt(rs.getTimestamp("editedAt"));
					return user;
				}
				return null;
			}
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public User createUser(String username, String password , String firstName , String lastName) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?,?,?,?,?,?)";
        Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());

		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
            String hashPassword = PasswordHasher.hashPassword(password);

			stmt.setString(1, username);
			stmt.setString(2, hashPassword);
            stmt.setString(3,firstName);
            stmt.setString(4,lastName);
            stmt.setTimestamp(5,timestampNow);
            stmt.setTimestamp(6,timestampNow);

			stmt.executeUpdate();
			return new User(username, password , firstName , lastName , timestampNow,timestampNow);
		} catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
