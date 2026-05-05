package dao;

import model.HealthRecord;
import model.HealthRecordDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HealthRecordDaoImpl implements HealthRecordDao {
    private final String TABLE_NAME = "health_records";

    public HealthRecordDaoImpl(){}

    @Override
    public void setup() throws SQLException {
        try(Connection connection = Database.getConnection();
            Statement stmt = connection.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    "username VARCHAR(16) NOT NULL ," +
                    "upperBp INTEGER NOT NULL ," +
                    "lowerBp INTEGER NOT NULL ," +
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
                    int upperBp = rs.getInt("upperBp");
                    int lowerBp = rs.getInt("lowerBp");
                    float weight = rs.getFloat("weight");
                    float temperature = rs.getFloat("temperature");
                    String note = rs.getString("note");
                    Timestamp createdAt = rs.getTimestamp("createdAt");
                    Timestamp editedAt = rs.getTimestamp("editedAt");
                    healthRecords.add(new HealthRecord(getId , username , upperBp, lowerBp , weight , temperature,note , createdAt , editedAt));
                }
            }
            return healthRecords;
        }
    }

    @Override
    public boolean createHealthRecord(String username, float weight, float temperature, int upperBP, int lowerBP, String note) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (username , upperBp, lowerBp , weight , temperature , note , createdAt , editedAt)" + " VALUES (?,?,?,? ,?,?,?,?)";
        Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());
        try(Connection connection = Database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.setString(1,username);
            stmt.setInt(2,upperBP);
            stmt.setInt(3,lowerBP);
            stmt.setFloat(4 , weight);
            stmt.setFloat(5 , temperature);
            stmt.setString(6,note);
            stmt.setTimestamp(7 , timestampNow);
            stmt.setTimestamp(8 , timestampNow);
            int result = stmt.executeUpdate();
            if(result < 0){
                return false;
            }
            return true;
        }
    }
}
