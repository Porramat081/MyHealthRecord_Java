package model;

public class User {
	private String username;
	private String password;

	public User() {
	}
	
	public User(String username, String password) {
		this.username = username;
//        hashing password
        String hashedPassword = "hashed" + password;
        this.password = hashedPassword;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
//		hashing password
        String hashedPassword = "hashed" + password;
        this.password = hashedPassword;
	}
}
