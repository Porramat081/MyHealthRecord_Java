package model;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.HealthRecordDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;

public class Model {
	private UserDao userDao;
	private User currentUser;
    private HealthRecordDao healthRecordDao;
	private ArrayList<HealthRecord> currentHealthRecords;

	public Model() {
		userDao = new UserDaoImpl();
        healthRecordDao = new HealthRecordDaoImpl();
	}
	
	public void setup() throws SQLException {
		userDao.setup();
        healthRecordDao.setup();
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
