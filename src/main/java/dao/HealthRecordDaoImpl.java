package dao;

import model.HealthRecord;
import model.HealthRecordDao;
import utils.FormatterString;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class HealthRecordDaoImpl implements HealthRecordDao {
    private final String TABLE_NAME = "health_records";

    public HealthRecordDaoImpl(){}

    @Override
    public void setup() throws SQLException {
        try(Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    "username VARCHAR(16) NOT NULL ," +
                    "bloodPressure VARCHAR(16) NOT NULL ," +
                    "weight REAL NOT NULL ," +
                    "temperature REAL NOT NULL ," +
                    "note TEXT ," +
                    "createdAt TIMESTAMP NOT NULL," +
                    "editedAt TIMESTAMP NOT NULL," +
                    "FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE)";
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public ArrayList<HealthRecord> getHealthRecords(String username) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        ArrayList<HealthRecord> healthRecords = new ArrayList<>();
        try(Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,username);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    int getId = rs.getInt("id");
                    String getBP = rs.getString("bloodPressure");
                    float weight = rs.getFloat("weight");
                    float temperature = rs.getFloat("temperature");
                    String note = rs.getString("note");
                    Timestamp createdAt = rs.getTimestamp("createdAt");
                    Timestamp editedAt = rs.getTimestamp("editedAt");
                    healthRecords.add(new HealthRecord(getId , username , getBP, weight , temperature,note , createdAt , editedAt));
                }
            }
            return healthRecords;
        }
    }

    @Override
    public boolean createHealthRecord(String username, float weight, float temperature, int upperBP, int lowerBP, String note) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (username , bloodPressure , weight , temperature , note , createdAt , editedAt)" + " VALUES (?,?,?,?,?,?,?)";
        Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());
        try(Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.setString(1,username);

            String finalBP = FormatterString.combineBP(upperBP,lowerBP);

            stmt.setString(2,finalBP);
            stmt.setFloat(3 , weight);
            stmt.setFloat(4 , temperature);
            stmt.setString(5,note);
            stmt.setTimestamp(6 , timestampNow);
            stmt.setTimestamp(7 , timestampNow);
            int result = stmt.executeUpdate();
            if(result < 0){
                return false;
            }
            return true;
        }
    }
}
