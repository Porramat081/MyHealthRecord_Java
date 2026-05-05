package model;

import java.sql.Timestamp;

public class User {
	private String username;
	private String password;
    private String firstname;
    private String lastname;

    private Timestamp createdAt;
    private Timestamp editedAt;

	public User() {
	}
	
	public User(String username, String password , String firstname , String lastname , Timestamp createdAt , Timestamp editedAt) {
		this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
	}

	public String getUsername() {
		return username;
	}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Timestamp editedAt) {
        this.editedAt = editedAt;
    }

    public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
        this.password = password;
	}
}
