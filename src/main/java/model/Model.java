package model;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.HealthRecordDao;
import dao.HealthRecordDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;

public class Model {
    /* Central application model holding DAO references and the current session state (logged-in user and their health records). */
	private UserDao userDao;
	private User currentUser;
    private HealthRecordDao healthRecordDao;
	private ArrayList<HealthRecord> currentHealthRecords;

	public Model() {
		userDao = new UserDaoImpl();
        healthRecordDao = new HealthRecordDaoImpl();
	}
	
	public void setup() throws SQLException {
		/* Initialises the UserDao and HealthRecordDao, creating SQLite tables if they do not already exist. */
        userDao.setup();
        healthRecordDao.setup();
	}

    public void resetState(){
        /* Clears the current user and health records, effectively logging the user out. */
        this.setCurrentUser(null);
        this.setCurrentHealthRecords(null);
    }

	public UserDao getUserDao() {
		return userDao;
	}

    public HealthRecordDao getHealthRecordDao(){return healthRecordDao;}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

    public ArrayList<HealthRecord> getCurrentHealthRecords(){
        return this.currentHealthRecords;
    }

    public void setCurrentHealthRecords(ArrayList<HealthRecord> hrs){
        this.currentHealthRecords = hrs;
    }

}
