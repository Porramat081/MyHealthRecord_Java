package dao;

import model.HealthRecord;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HealthRecordDao {
    void setup() throws SQLException;
    ArrayList<HealthRecord> getHealthRecords(String username) throws SQLException;
    boolean createHealthRecord(String username, float weight, float temperature, int upperBP, int lowerBP, String note) throws SQLException;
    boolean editHealthRecord(HealthRecord editRecord , String username) throws SQLException;
    boolean deleteHealthRecord(int healthRecordId , String username) throws SQLException;
}
