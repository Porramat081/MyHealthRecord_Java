package dao;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;

import model.User;
import utils.PasswordHasher;

public class UserDaoImpl implements UserDao {
	/* SQLite implementation of UserDao. Handles all database operations for the users table, including password hashing. */
    private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
        /* Creates the users table if it does not already exist, with username as the primary key. */
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
		/* Looks up a user by username and hashed password. Returns the User object on match, or null if credentials are wrong. */
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
    public boolean editUser(User editedUser) throws SQLException {
        /* Updates the user's first name, last name, and hashed password in the database. */
        String sql = "UPDATE "+TABLE_NAME+" SET username = ? , password = ? , firstname = ? , lastname = ? , editedAt = ? WHERE username = ?";

        try(Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            String hashPassword = PasswordHasher.hashPassword(editedUser.getPassword());

            stmt.setString(1,editedUser.getUsername());
            stmt.setString(2,hashPassword);
            stmt.setString(3, editedUser.getFirstname());
            stmt.setString(4, editedUser.getLastname());
            stmt.setTimestamp(5 , editedUser.getEditedAt());
            stmt.setString(6 , editedUser.getUsername());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public User createUser(String username, String password , String firstName , String lastName) throws SQLException {
		/* Inserts a new user row with hashed password and current timestamps, then returns the created User object. */
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

    @Override
    public String getTestHashPasswordFromDb(){
        /* Retrieves the stored hashed password for "testuser01", used for JUnit password hash verification tests. */
        String sql = "SELECT password from users WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
             stmt.setString(1, "testuser01");
            ResultSet rs = stmt.executeQuery();

            return rs.getString("password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
